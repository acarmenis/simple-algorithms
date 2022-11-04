package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IDoctorSpecializationService {
    List<DoctorSpecializationDto> doctorSpecialization(String doctorSpecialization);
}
