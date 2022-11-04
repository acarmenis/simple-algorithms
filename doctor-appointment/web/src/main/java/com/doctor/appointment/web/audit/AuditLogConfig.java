package com.doctor.appointment.web.audit;

/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-booking-appointment
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorRef")
public class AuditLogConfig {
    @Bean
    public AuditAware auditorRef() {
        return new AuditAware();
    }
}
