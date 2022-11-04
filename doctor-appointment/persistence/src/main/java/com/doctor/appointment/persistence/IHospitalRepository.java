package com.doctor.appointment.persistence;


import com.doctor.appointment.domain.entities.Hospital;
import org.springframework.stereotype.Repository;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Repository
public interface IHospitalRepository extends IBaseEntityRepository<Hospital> {}
