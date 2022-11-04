package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;
import com.doctor.appointment.utils.mappers.MapperUtil;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

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
@Table(name= "DOCTOR_SPECIALIZATION",
        indexes = {
                @Index(name = "INDEX_ON_DOCTOR_SPECIALIZATION", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "DOCTOR_SPECIALIZATION_UK1", columnNames = {"DOCTOR_ID", "SPECIALIZATION_ID"})
        })
@org.hibernate.annotations.Table(appliesTo = "doctor_specialization", comment = "DOCTOR - SPECIALIZATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DoctorSpecialization extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "DOCTOR_ID", nullable = false, foreignKey=@ForeignKey(name = "DOCTOR_FK"))
    @Comment("DOCTOR")
    private Doctor doctor;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "SPECIALIZATION_ID", nullable = false, foreignKey=@ForeignKey(name = "SPECIALIZATION_FK"))
    @Comment("SPECIALIZATION")
    private Specialization specialization;
    @Comment("RATING")
    private Integer rating;

    public DoctorSpecialization fromDto(DoctorSpecializationDto doctorSpecializationDto){
        if(doctorSpecializationDto.getId() != null){
            this.id=doctorSpecializationDto.getId();
        }
        if(doctorSpecializationDto.getDoctor() != null){
            this.doctor=MapperUtil.mapOne(doctorSpecializationDto.getDoctor(), Doctor.class);
        }
        if(doctorSpecializationDto.getSpecialization() != null){
            this.specialization=MapperUtil.mapOne(doctorSpecializationDto.getSpecialization(), Specialization.class);
        }
        if(doctorSpecializationDto.getRating() != null){
            this.rating=doctorSpecializationDto.getRating();
        }
        return this;
    }
}
