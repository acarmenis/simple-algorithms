package com.doctor.appointment.persistence;

import com.doctor.appointment.domain.dtos.Availability;
import com.doctor.appointment.domain.entities.OfficeDoctorAvailability;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Repository
public interface IOfficeDoctorAvailabilityRepository extends IBaseEntityRepository<OfficeDoctorAvailability> {

    @Transactional
    @Query( "SELECT new com.doctor.appointment.domain.dtos.Availability( oda.id, oda.availability, oda.availabilityDate, oda.startTime, oda.endTime, oda.office.id, o.timeSlotPerClientInMinutes, d.id, o.hospital.id, d.contact.firstName, d.contact.lastName, sp.specialization.id, s.specializationName ) " +
            "FROM OfficeDoctorAvailability oda " +
            "JOIN Office o " +
            "ON o.id = oda.office.id " +
            "JOIN Doctor d " +
            "ON d.hospital.id = o.hospital.id " +
            "JOIN DoctorSpecialization sp " +
            "ON sp.doctor.id = o.doctor.id " +
            "JOIN Specialization s " +
            "ON s.id = sp.specialization.id " +
            "JOIN Hospital h " +
            "ON h.id = d.hospital.id " +
            "WHERE oda.availability = :enabled " +
            "AND s.specializationName = :specializationName " +
            "AND h.name = :hospitalNameName " +
            "ORDER BY o.doctor.id " +
            "ASC")
    List<Availability> getDoctorAvailabilities(@Param("enabled") boolean enabled, @Param("specializationName") String specializationName, @Param("hospitalNameName") String hospitalNameName);

}
