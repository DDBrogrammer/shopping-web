package edu.daidp.shoppingwebapp.service;


import com.fasterxml.jackson.databind.ObjectMapper;

import edu.daidp.shoppingwebapp.common.constant.TokenType;
import edu.daidp.shoppingwebapp.config.security.JwtService;
import edu.daidp.shoppingwebapp.dto.AuthenticationRequestDto;
import edu.daidp.shoppingwebapp.dto.AuthenticationResponseDto;
import edu.daidp.shoppingwebapp.dto.RegisterRequestDto;
import edu.daidp.shoppingwebapp.common.constant.Role;
import edu.daidp.shoppingwebapp.entity.User;
import edu.daidp.shoppingwebapp.repository.TokenRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.daidp.shoppingwebapp.entity.Token;
import java.io.IOException;

import static edu.daidp.shoppingwebapp.common.constant.UserStatus.ENABLE;

@Service

public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationService(UserRepository repository, TokenRepository tokenRepository,
                               PasswordEncoder passwordEncoder, JwtService jwtService,
                               AuthenticationManager authenticationManager) {
    this.repository = repository;
    this.tokenRepository = tokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  public AuthenticationResponseDto register(RegisterRequestDto request) {
    var user = User.builder()
            .phoneNo(request.getPhoneNo())
            .userStatus(ENABLE)
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.valueOf(request.getRole()))
        .build();

    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
    try{
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
    } catch (Exception e){
      System.out.println(e);
    }

    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponseDto.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .email(user.getEmail())
            .role(user.getRole().name())
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId().intValue());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
