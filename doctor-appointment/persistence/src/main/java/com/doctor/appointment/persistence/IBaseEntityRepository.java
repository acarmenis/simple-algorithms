package com.doctor.appointment.persistence;

import com.doctor.appointment.domain.audit.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */
@Repository
public interface IBaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {}
