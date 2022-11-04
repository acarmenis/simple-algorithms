package com.doctor.appointment.domain.dtos;

import lombok.*;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddAppointmentRequest {
    private Long patientId;
    private Availability availability;
}
