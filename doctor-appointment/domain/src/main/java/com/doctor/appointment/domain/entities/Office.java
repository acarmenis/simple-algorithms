package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.OfficeDto;
import com.doctor.appointment.domain.value.Address;
import com.doctor.appointment.utils.mappers.MapperUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

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
@Table(name= "OFFICE",
        indexes = {
                @Index(name = "INDEX_ON_OFFICE", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "OFFICE_UK1", columnNames = {"DOCTOR_ID", "HOSPITAL_ID"})
        })
@org.hibernate.annotations.Table(appliesTo = "office", comment = "OFFICES")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Office extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name="DOCTOR_ID", nullable=false)
    @Comment("DOCTOR PK")
    @JsonBackReference
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name="HOSPITAL_ID", nullable=false)
    @Comment("HOSPITAL TO WHICH DOCTOR BELONGS TO")
    private Hospital hospital;
    @Column(name = "TIME_SLOT_PER_CLIENT_IN_MINUTES")
    @Comment("TIME SLOT PER CLIENT IN MINUTES")
    private Integer timeSlotPerClientInMinutes;
    @Embedded
    private Address address;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(
            mappedBy = "office",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<OfficeDoctorAvailability> officeDoctorAvailabilities = new HashSet<>();
    public void addOfficeDoctorAvailability(OfficeDoctorAvailability officeDoctorAvailability) {
        officeDoctorAvailabilities.add(officeDoctorAvailability);
        officeDoctorAvailability.setOffice(this);
    }
    public void removeOfficeDoctorAvailability(OfficeDoctorAvailability officeDoctorAvailability) {
        if(officeDoctorAvailabilities.size() > 0){
            officeDoctorAvailabilities.remove(officeDoctorAvailability);
            officeDoctorAvailability.setOffice(null);
        }
    }
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(
            mappedBy = "office",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Appointment> appointments = new HashSet<>();
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setOffice(this);
    }
    public void removeAppointment(Appointment appointment) {
        if(appointments.size() > 0){
            appointments.remove(appointment);
            appointment.setOffice(null);
        }
    }
    public Office fromDto(OfficeDto officeDto){
        if(officeDto.getId() != null){
            this.id=officeDto.getId();
        }
        if(officeDto.getDoctor() != null){
            this.doctor= MapperUtil.mapOne(officeDto.getDoctor(), Doctor.class);
        }
        if(officeDto.getAddress() != null){
            this.address=officeDto.getAddress();
        }
        if(officeDto.getHospital() != null){
            this.hospital= MapperUtil.mapOne(officeDto.getHospital(), Hospital.class);
        }
        if(officeDto.getTimeSlotPerClientInMinutes() != null){
            this.timeSlotPerClientInMinutes=officeDto.getTimeSlotPerClientInMinutes();
        }
        if(officeDto.getOfficeDoctorAvailabilities() != null){
            this.officeDoctorAvailabilities=MapperUtil.mapSet(officeDto.getOfficeDoctorAvailabilities(), OfficeDoctorAvailability.class);
        }
        if(officeDto.getAppointments() != null){
            this.appointments=MapperUtil.mapSet(officeDto.getAppointments(), Appointment.class);
        }
        return this;
    }
}
