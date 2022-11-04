package com.doctor.appointment.domain.dtos;

import com.doctor.appointment.domain.audit.BaseDto;
import com.doctor.appointment.domain.value.Address;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Date;
import java.util.Set;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class HospitalDto extends BaseDto {
    private String name;
    private Address address;
    private Date startDate;
    private Date endDate;
    private Set<DoctorDto> doctors;
}
