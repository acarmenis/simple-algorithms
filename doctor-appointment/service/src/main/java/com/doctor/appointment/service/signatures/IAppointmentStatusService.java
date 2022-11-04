package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.AppointmentStatusDto;
import com.doctor.appointment.domain.state.AppointmentState;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IAppointmentStatusService {
    AppointmentStatusDto findByState(AppointmentState appointmentState);
}
