package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.AppointmentStatusDto;
import com.doctor.appointment.domain.state.AppointmentState;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

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
@Table(name= "APPOINTMENT_STATUS",
        indexes = {
                @Index(name = "INDEX_ON_APPOINTMENT_STATUS", columnList = "id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "APPOINTMENT_STATUS_UK1", columnNames = {"STATUS"})
        })
@org.hibernate.annotations.Table(appliesTo = "appointment_status", comment = "APPOINTMENTS STATUS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AppointmentStatus extends BaseEntity {
    @Column(name = "STATUS")
    @Comment("APPOINTMENT STATUS")
    @Enumerated(EnumType.STRING)
    private AppointmentState state;
    public AppointmentStatus fromDto(AppointmentStatusDto appointmentStatusDto){
        if(appointmentStatusDto.getId() != null){
            this.id=appointmentStatusDto.getId();
        }
        if(appointmentStatusDto.getState() != null){
            this.state=appointmentStatusDto.getState();
        }
        return this;
    }
}
