package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.DoctorDto;
import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IDoctorServiceService {

    List<DoctorDto> getAll(Integer pageNo, Integer pageSize, String sortBy, Boolean ascending);
    List<DoctorSpecializationDto> doctorSpecializationAndHospital(String doctorSpecialization, String hospitalName);
}
