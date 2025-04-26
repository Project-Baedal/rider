package com.baedal.rider.application.port.in;

import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.application.command.ApproveDeliveryCommand;

public interface RiderUsecase {

  public void signup(String email, String nickname, String rawPassword);

  public LoginResponse authenticate(String email, String password);

  void approveDelivery(ApproveDeliveryCommand.Request req);
}
