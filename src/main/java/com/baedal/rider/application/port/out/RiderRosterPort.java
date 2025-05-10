package com.baedal.rider.application.port.out;

import java.util.Set;

public interface RiderRosterPort {

  void makeOnDuty(Long riderId);

  void makeOffDuty(Long riderId);

  Set<Long> findAllOnDuty();
}
