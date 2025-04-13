package com.baedal.rider.application.port.in;

public interface RiderSignupUsecase {

  public void signup(String email, String nickname, String rawPassword);
}
