package com.doctor.appointment.domain.dtos;

import com.doctor.appointment.domain.audit.BaseDto;
import com.doctor.appointment.domain.value.Address;
import com.doctor.appointment.domain.value.Contact;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Set;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PatientDto extends BaseDto {
    private Contact contact;
    private Address address;
    private Set<AppointmentDto> appointments;
}
