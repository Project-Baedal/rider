package com.baedal.rider.adapter.presentation.controller;

import com.baedal.rider.adapter.presentation.request.LoginRequest;
import com.baedal.rider.adapter.presentation.request.SignupRequest;
import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.port.in.RiderAuthenticationUsecase;
import com.baedal.rider.application.port.in.RiderSignupUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RiderController {

  private final RiderAuthenticationUsecase authenticationUsecase;
  private final RiderSignupUsecase signupUsecase;

  @PostMapping("/v0/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = authenticationUsecase.authenticate(request.email(),
        request.password());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/v0/signup")
  public ResponseEntity<Void> singup(@RequestBody SignupRequest request) {
    signupUsecase.signup(request.email(), request.nickname(), request.password());
    return ResponseEntity.noContent().build();
  }
}
