package edu.daidp.shoppingwebapp.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.daidp.shoppingwebapp.common.constant.Role;
import edu.daidp.shoppingwebapp.common.constant.TokenType;
import edu.daidp.shoppingwebapp.common.exception.AuthenticationException;
import edu.daidp.shoppingwebapp.common.exception.DuplicateDataException;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.config.security.JwtService;
import edu.daidp.shoppingwebapp.dto.AuthenticationRequestDto;
import edu.daidp.shoppingwebapp.dto.AuthenticationResponseDto;
import edu.daidp.shoppingwebapp.dto.RegisterRequestDto;
import edu.daidp.shoppingwebapp.entity.Cart;
import edu.daidp.shoppingwebapp.entity.Token;
import edu.daidp.shoppingwebapp.entity.User;
import edu.daidp.shoppingwebapp.repository.CartRepository;
import edu.daidp.shoppingwebapp.repository.TokenRepository;
import edu.daidp.shoppingwebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static edu.daidp.shoppingwebapp.common.constant.UserStatus.DISABLE;
import static edu.daidp.shoppingwebapp.common.constant.UserStatus.ENABLE;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthenticationService(UserRepository repository, CartRepository cartRepository,
                                 TokenRepository tokenRepository,
                                 PasswordEncoder passwordEncoder, JwtService jwtService,
                                 AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = repository;
        this.cartRepository = cartRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public String register(RegisterRequestDto request) throws DuplicateDataException {
        User user = User.builder().firstName(request.getFirstName()).middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .phoneNo(request.getPhoneNo())
                .userStatus(ENABLE)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .build();
        try {  // Generate a random confirmation token and save it to the user
            String confirmationToken = generateConfirmationToken();
            // Build the confirmation link
            String confirmationLink = buildConfirmationLink(request.getEmail(), confirmationToken);
            // Send the confirmation email with the link
            emailService.sendEmail(request.getEmail(), "ĐĂNG KÝ TÀI KHOẢN SHOPPING",
                                   "Click vào đường dẫn để xác nhận tài khỏan: " + confirmationLink);
            user.setConfirmationToken(confirmationToken);
        } catch (Exception e) {
            System.out.println(e);
        }
        user.setUserStatus(DISABLE);
        try {
            User savedUser = userRepository.save(user);
            cartRepository.save(Cart.builder().subTotal(BigDecimal.ZERO).createAt(Timestamp.valueOf(
                    LocalDateTime.now())).user(savedUser).build());
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException(e.getMessage());
        } catch (Exception e) {
            throw e;
        }

        return "CHECK EMAIL FOR REGISTER CONFIRMATION";
    }

    private String generateConfirmationToken() {
        int length = 20;
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return passwordEncoder.encode(sb.toString());
    }

    private String buildConfirmationLink(String email, String confirmationToken) {
        StringBuilder stringBuilder = new StringBuilder(
                "http://localhost:8080/api/v1/auth/confirmation-endpoint?email=");
        stringBuilder.append(email);
        stringBuilder.append("&token=");
        stringBuilder.append(confirmationToken);
        return stringBuilder.toString();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            System.out.println(authentication);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        if (!user.isEnabled()) {
            throw new AuthenticationException("Account Not Active");
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .active(user.isEnabled())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public boolean emailConfirm(String email, String token) throws NoContentFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new NoContentFoundException("Can't find user");
        }
        User user = userOptional.get();

        if (user.getConfirmationToken().equals(token)) {
            user.setUserStatus(ENABLE);
            userRepository.save(user);
            return true;
        } else return false;
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
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
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
