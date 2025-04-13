package com.baedal.rider.domain.repository;

import com.baedal.rider.domain.entity.Rider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<Rider, Long> {

  Optional<Rider> findByEmail(String email);
}
