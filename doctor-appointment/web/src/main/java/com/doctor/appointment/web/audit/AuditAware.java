package com.doctor.appointment.web.audit;



import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-booking-appointment
 */

/**
 * JPA needs information about currently logged in user so for that we need to implement the AuditorAware interface
 * and override its getCurrentAuditor() method to fetch current logged in user.
 * JPA can audit the created date and modified date by using the system’s current time but for auditor information.
 */
public class AuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
      return Optional.of("Andreas Karmenis");
    }
}

