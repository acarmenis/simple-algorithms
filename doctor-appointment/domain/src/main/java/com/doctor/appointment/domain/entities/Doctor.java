package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.DoctorDto;
import com.doctor.appointment.domain.value.Contact;
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
 * @author Andreas Karmenis on 11/1/2022
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
@Table(name= "DOCTOR",
        indexes = {
                @Index(name = "INDEX_ON_DOCTOR", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "DOCTOR_UK1", columnNames = {"FIRST_NAME", "LAST_NAME"})
        })
@org.hibernate.annotations.Table(appliesTo = "doctor", comment = "DOCTORS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Doctor extends BaseEntity {
    @Embedded
    private Contact contact;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="HOSPITAL_ID", nullable=false)
    @Comment("HOSPITAL TO WHICH DOCTOR BELONGS TO")
    @JsonBackReference
    private Hospital hospital;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(
            mappedBy = "doctor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Office> offices = new HashSet<>();
    public void addOffice(Office office) {
        offices.add(office);
        office.setDoctor(this);
    }
    public void removeOffice(Office office) {
        if(offices.size() > 0) {
            offices.remove(office);
            office.setDoctor(null);
        }
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor )) return false;
        return id != null && id.equals(((Doctor) o).getId());
    }

    public Doctor fromDto(DoctorDto doctorDto){
        if(doctorDto.getId() != null){
            this.id=doctorDto.getId();
        }
        if(doctorDto.getContact() != null){
            this.contact=doctorDto.getContact();
        }
        if(doctorDto.getHospital() != null){
            this.hospital=MapperUtil.mapOne(doctorDto.getHospital(), Hospital.class);
        }
        if(doctorDto.getOffices() != null){
            this.offices=MapperUtil.mapSet(doctorDto.getOffices(), Office.class);
        }
        return this;
    }
}
