package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.Availability;
import com.doctor.appointment.domain.dtos.OfficeDoctorAvailabilityDto;

import java.util.Date;
import java.util.List;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
public interface IOfficeDoctorAvailabilityService {
    List<OfficeDoctorAvailabilityDto> availabilitiesOfADoctor(long doctorId);
    List<OfficeDoctorAvailabilityDto> availabilitiesOfADoctor(long doctorId, Date date);
    List<Availability> getAvailabilitiesOfADoctor(boolean enabled, String specializationName, String hospitalNameName);
}
