package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.HospitalDto;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IHospitalService {

    HospitalDto findHospitalByName(String hospitalName);
}
