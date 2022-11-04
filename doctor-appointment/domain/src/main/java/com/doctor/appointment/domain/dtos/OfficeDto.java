package com.doctor.appointment.domain.dtos;

import com.doctor.appointment.domain.audit.BaseDto;
import com.doctor.appointment.domain.value.Address;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OfficeDto extends BaseDto {
    private DoctorDto doctor;
    private HospitalDto hospital;
    private Integer timeSlotPerClientInMinutes;
    private Address address;
    private Set<OfficeDoctorAvailabilityDto> officeDoctorAvailabilities;
    private Set<AppointmentDto> appointments;
}
