package com.baedal.rider.application.port.in;

import com.baedal.rider.adapter.presentation.response.LoginResponse;

public interface RiderAuthenticationUsecase {

  public LoginResponse authenticate(String email, String password);
}
