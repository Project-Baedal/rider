package com.baedal.rider.adapter.presentation.controller;

import com.baedal.rider.adapter.presentation.request.LoginRequest;
import com.baedal.rider.adapter.presentation.request.SignupRequest;
import com.baedal.rider.adapter.presentation.request.ToggleDutyRequest;
import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.service.RiderService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
  public ResponseEntity<Void> singUp(@RequestBody SignupRequest request) {
    service.signUp(request.email(), request.name(), request.password());
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasRole('RIDER')")
  @PatchMapping("/v0/me/duty")
  public ResponseEntity<Void> makeOnDuty(
      @AuthenticationPrincipal Long riderId,
      @RequestBody ToggleDutyRequest request) {
    service.toggleDuty(riderId, request.onDuty());
    return ResponseEntity.noContent().build();
  }
}
