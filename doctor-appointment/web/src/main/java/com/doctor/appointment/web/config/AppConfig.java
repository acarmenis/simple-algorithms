package com.doctor.appointment.web.config;

import com.doctor.appointment.web.swagger.SwaggerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-appointment
 */
@Configuration
@ComponentScan(basePackages = {"com.doctor.appointment"})
@Import({  JpaConfig.class, SwaggerConfig.class  })
public class AppConfig {}
