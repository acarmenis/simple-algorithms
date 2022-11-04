package com.doctor.appointment.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Availability {
    boolean availability;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date availabilityDate;
    @JsonFormat(pattern="HH:mm:ss")
    private Date startTime;
    @JsonFormat(pattern="HH:mm:ss")
    private Date endTime;
    private Long officeId;
    private Integer timeSlotPerClientInMinutes;
    private Long doctorId;
    private Long hospitalId;
    private String firstName;
    private String lastName;
    private Long doctorSpecializationId;
    private String doctorSpecializationName;
}
