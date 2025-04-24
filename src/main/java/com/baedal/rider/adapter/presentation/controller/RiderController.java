package com.baedal.rider.adapter.presentation.controller;

import com.baedal.rider.adapter.presentation.request.LoginRequest;
import com.baedal.rider.adapter.presentation.request.SignupRequest;
import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.service.RiderService;
import jakarta.annotation.security.PermitAll;
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

  private final RiderService service;

  @PostMapping("/v0/login")
  @PermitAll
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    LoginResponse response = service.authenticate(request.email(), request.password());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/v0/signup")
  @PermitAll
  public ResponseEntity<Void> singup(@RequestBody SignupRequest request) {
    service.signup(request.email(), request.name(), request.password());
    return ResponseEntity.noContent().build();
  }
}
