package com.baedal.rider.application.service;

import com.baedal.rider.adapter.presentation.response.LoginResponse;
import com.baedal.rider.adapter.presentation.security.UserDetailsImpl;
import com.baedal.rider.application.port.in.RiderUsecase;
import com.baedal.rider.domain.entity.Rider;
import com.baedal.rider.domain.repository.RiderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RiderService implements RiderUsecase {

  private final RiderRepository repository;

  private final PasswordEncoder passwordEncoder;

  private final UserDetailsService userDetailsService;

  @Transactional
  public void signup(String email, String nickname, String rawPassword) {
    Rider rider = new Rider(email, nickname, passwordEncoder.encode(rawPassword));
    repository.save(rider);
  }

  @Transactional(readOnly = true)
  public LoginResponse authenticate(String email, String password) {
    UserDetailsImpl user = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      // FIXME: Define Exception Class
      throw new IllegalArgumentException("Invalid email or password");
    }

    return new LoginResponse(
        user.rider().getId()
    );
  }
}
