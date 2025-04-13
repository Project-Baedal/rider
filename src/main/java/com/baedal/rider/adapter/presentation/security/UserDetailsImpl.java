package com.baedal.rider.adapter.presentation.security;

import com.baedal.rider.domain.entity.Rider;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserDetailsImpl(Rider rider) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return rider.getPassword();
  }

  @Override
  public String getUsername() {
    return rider.getEmail();
  }
}
