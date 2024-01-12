package edu.daidp.shoppingwebapp.controller;

import edu.daidp.shoppingwebapp.common.constant.COMMON_CONSTANT;
import edu.daidp.shoppingwebapp.common.exception.ApplicationResponse;
import edu.daidp.shoppingwebapp.common.exception.AuthenticationException;
import edu.daidp.shoppingwebapp.common.exception.DuplicateDataException;
import edu.daidp.shoppingwebapp.common.exception.NoContentFoundException;
import edu.daidp.shoppingwebapp.dto.AuthenticationRequestDto;
import edu.daidp.shoppingwebapp.dto.AuthenticationResponseDto;
import edu.daidp.shoppingwebapp.dto.OrderDto;
import edu.daidp.shoppingwebapp.dto.RegisterRequestDto;
import edu.daidp.shoppingwebapp.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<ApplicationResponse<String> > register(
      @RequestBody @Valid RegisterRequestDto request
  ) throws DuplicateDataException {
    return ResponseEntity.ok(new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                               COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                                                service.register(request), List.of()
    ) );
  }
  @PostMapping("/authenticate")
  public ResponseEntity<ApplicationResponse<AuthenticationResponseDto>> authenticate(
      @RequestBody AuthenticationRequestDto request
  ) throws AuthenticationException {
    return ResponseEntity.ok(new ApplicationResponse<AuthenticationResponseDto>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                                                COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                                                service.authenticate(request), List.of()
    ) );
  }

  @GetMapping("/confirmation-endpoint")
  public ResponseEntity<ApplicationResponse<String>> emailConfirm(
         @RequestParam String email,@RequestParam String token
  ) throws NoContentFoundException {
    return ResponseEntity.ok(new ApplicationResponse<String>(COMMON_CONSTANT.APP_STATUS.SUCCESS.CODE,
                                                                                COMMON_CONSTANT.APP_STATUS.SUCCESS.MESSAGE,
                                                                                service.emailConfirm(email,token)? "SUCCESS":"FAILED", List.of()
    ) );
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

}
