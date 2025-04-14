package com.baedal.rider.adapter.presentation.controller;

import com.baedal.rider.adapter.presentation.request.LoginRequest;
import com.baedal.rider.adapter.presentation.request.SignupRequest;
import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.port.in.RiderUsecase;
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

  private final RiderUsecase riderUsecase;

  @PostMapping("/v0/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = riderUsecase.authenticate(request.email(),
        request.password());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/v0/signup")
  public ResponseEntity<Void> singup(@RequestBody SignupRequest request) {
    riderUsecase.signup(request.email(), request.nickname(), request.password());
    return ResponseEntity.noContent().build();
  }
}
