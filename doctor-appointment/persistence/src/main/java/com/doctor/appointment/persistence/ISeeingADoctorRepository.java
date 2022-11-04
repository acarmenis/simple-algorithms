package com.doctor.appointment.persistence;

import com.doctor.appointment.domain.entities.SeeingADoctor;
import org.springframework.stereotype.Repository;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Repository
public interface ISeeingADoctorRepository extends IBaseEntityRepository<SeeingADoctor> {}
