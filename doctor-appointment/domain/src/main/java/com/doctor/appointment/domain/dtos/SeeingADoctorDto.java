package com.doctor.appointment.domain.dtos;

import com.doctor.appointment.domain.audit.BaseDto;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Date;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SeeingADoctorDto extends BaseDto {
    private DoctorDto doctor;
    private PatientDto patient;
    private Integer waitTimeRate;
    private Integer behaviourMannerRate;
    private String review;
    private boolean recommended;
    private Date reviewDate;
}
