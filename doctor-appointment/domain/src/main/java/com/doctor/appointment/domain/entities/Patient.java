package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.PatientDto;
import com.doctor.appointment.domain.value.Address;
import com.doctor.appointment.domain.value.Contact;
import com.doctor.appointment.utils.mappers.MapperUtil;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name= "PATIENT",
        indexes = {
                @Index(name = "INDEX_ON_PATIENT", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "PATIENT_UK1", columnNames = {"PHONE"})
        })
@org.hibernate.annotations.Table(appliesTo = "patient", comment = "PATIENTS")
public class Patient extends BaseEntity {
    @Embedded
    private Contact contact;
    @Embedded
    private Address address;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(
            mappedBy = "patient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Appointment> appointments = new HashSet<>();
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setPatient(this);
    }
    public void removeAppointment(Appointment appointment) {
        if(appointments.size() > 0){
            appointments.remove(appointment);
            appointment.setPatient(null);
        }
    }

    public Patient fromDto(PatientDto patientDto){
        if(patientDto.getId() != null){
            this.id=patientDto.getId();
        }
        if(patientDto.getContact() != null){
            this.contact=patientDto.getContact();
        }
        if(patientDto.getAddress() != null){
            this.address = patientDto.getAddress();
        }
        if(patientDto.getAppointments() != null){
            this.appointments=MapperUtil.mapSet(patientDto.getAppointments(), Appointment.class);
        }
        return this;
    }
}
