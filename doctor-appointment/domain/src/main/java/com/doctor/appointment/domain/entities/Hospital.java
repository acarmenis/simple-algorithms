package com.doctor.appointment.domain.entities;


import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.HospitalDto;
import com.doctor.appointment.domain.value.Address;
import com.doctor.appointment.utils.mappers.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Date;
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
@Table(name= "HOSPITAL",
        indexes = {
                @Index(name = "INDEX_ON_HOSPITAL", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "HOSPITAL_UK1", columnNames = {"HOSPITAL_NAME"})
        })
@org.hibernate.annotations.Table(appliesTo = "hospital", comment = "HOSPITALS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hospital extends BaseEntity {
    @Column(name = "HOSPITAL_NAME", length = 100)
    @Comment("HOSPITAL NAME")
    private String name;
    @Embedded
    private Address address;
    @Column(name = "START_DATE", nullable = false)
    @Comment("START DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE")
    @Comment("END DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @OneToMany(
            mappedBy = "hospital",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private Set<Doctor> doctors = new HashSet<>();
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        doctor.setHospital(this);
    }
    public void removeDoctor(Doctor doctor) {
        if(doctors.size() > 0) {
            doctors.remove(doctor);
            doctor.setHospital(null);
        }
    }
    public Hospital fromDto(HospitalDto hospitalDto){
        if(hospitalDto.getId() != null){
            this.id=hospitalDto.getId();
        }
        if(hospitalDto.getName() != null){
            this.name=hospitalDto.getName();
        }
        if(hospitalDto.getAddress() != null){
            this.address=hospitalDto.getAddress();
        }
        if(hospitalDto.getStartDate() != null){
            this.startDate=hospitalDto.getStartDate();
        }
        if(hospitalDto.getEndDate() != null){
            this.endDate=hospitalDto.getEndDate();
        }
        if(hospitalDto.getDoctors() != null){
            this.doctors=MapperUtil.mapSet(hospitalDto.getDoctors(), Doctor.class);
        }
        return this;
    }
}
