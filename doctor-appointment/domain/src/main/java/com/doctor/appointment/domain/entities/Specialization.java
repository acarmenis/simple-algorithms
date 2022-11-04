package com.doctor.appointment.domain.entities;


import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.SpecializationDto;
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

/**
 * The specialization table holds all existing medical specializations like orthopedic, neurologist, dentist, etc.
 * A doctor can have more than one specialization; in fact, it’s pretty common for a doctor to specialize in related fields.
 * For example, a neurologist can also be a psychiatrist;
 * a gynecologist can be an endocrinologist, and so on.
 * Therefore, the doctor_specialization table allows a many-to-many relationship between the doctor and specialization tables.
 * The attributes on these two tables are self-explanatory.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@javax.persistence.Table(name= "SPECIALIZATION",
        indexes = {
                @Index(name = "INDEX_ON_SPECIALIZATION", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "SPECIALIZATION_UK1", columnNames = {"SPECIALIZATION_NAME"})
        })
@org.hibernate.annotations.Table(appliesTo = "specialization", comment = "DOCTOR SPECIALIZATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Specialization extends BaseEntity {
        @Column(name = "SPECIALIZATION_NAME", length = 250)
        @Comment("SPECIALIZATION NAME")
        private String specializationName;
        public Specialization fromDto(SpecializationDto specializationDto){
                if(specializationDto.getId() != null){
                        this.id=specializationDto.getId();
                }
                if(specializationDto.getSpecializationName() != null){
                        this.specializationName=specializationDto.getSpecializationName();
                }
                return this;
        }
}
