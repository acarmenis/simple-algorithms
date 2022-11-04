package com.doctor.appointment.web.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */


@Configuration
@EntityScan(basePackages = {"com.doctor.appointment.domain"})
@EnableJpaRepositories(basePackages = { "com.doctor.appointment.persistence" })
public class JpaConfig {}
