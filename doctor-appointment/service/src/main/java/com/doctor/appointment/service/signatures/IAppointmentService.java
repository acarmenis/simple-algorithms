package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.AddAppointmentRequest;
import com.doctor.appointment.domain.dtos.AppointmentDto;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IAppointmentService {

    AppointmentDto bookAppointmentFor(AddAppointmentRequest addAppointmentRequest);
}
