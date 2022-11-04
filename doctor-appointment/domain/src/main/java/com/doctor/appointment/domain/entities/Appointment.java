package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.AppointmentDto;
import com.doctor.appointment.utils.mappers.MapperUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.util.Date;

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
@Table(name= "APPOINTMENT",
        indexes = {
                @Index(name = "INDEX_ON_APPOINTMENT", columnList = "id")
        })/*,
        uniqueConstraints = {
                @UniqueConstraint(name = "APPOINTMENT_UK1", columnNames = {"OFFICE_ID", "PATIENT_ID", "APPOINTMENT_ACTUAL_START_TIME"})
        })*/
@org.hibernate.annotations.Table(appliesTo = "appointment", comment = "APPOINTMENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Appointment extends BaseEntity {
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
        @JoinColumn(name="OFFICE_ID", nullable=false)
        @Comment("OFFICE PK")
        @JsonBackReference
        private Office office;
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
        @JoinColumn(name="PATIENT_ID", nullable=false)
        @Comment("PATIENT PK")
        private Patient patient;
        @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
        @JoinColumn(name="APPOINTMENT_STATUS_ID", nullable=false)
        @Comment("APPOINTMENT STATUS PK")
        private AppointmentStatus appointmentStatus;
        @Column(name = "APPOINTMENT_BOOKED_DATE", columnDefinition = "DATE")
        @Comment("APPOINTMENT BOOKED DATE")
        @Temporal(TemporalType.DATE)
        private Date bookedDate;
        @Column(name = "APPOINTMENT_ACTUAL_START_TIME", columnDefinition = "TIMESTAMP")
        @Comment("APPOINTMENT ACTUAL START TIME")
        @Temporal(TemporalType.TIMESTAMP)
        private Date actualStartTime;
        @Column(name = "APPOINTMENT_ACTUAL_END_TIME", columnDefinition = "TIMESTAMP")
        @Comment("APPOINTMENT ACTUAL END TIME")
        @Temporal(TemporalType.TIMESTAMP)
        private Date actualEndTime;

        public Appointment fromDto(AppointmentDto appointmentDto){
                if(appointmentDto.getId() != null){
                        this.id=appointmentDto.getId();
                }
                if(appointmentDto.getOffice() != null){
                        this.office= MapperUtil.mapOne(appointmentDto.getOffice(), Office.class);
                }
                if(appointmentDto.getPatient() != null){
                        this.patient = MapperUtil.mapOne(appointmentDto.getPatient(), Patient.class);
                }
                if(appointmentDto.getAppointmentStatus() != null){
                        this.appointmentStatus = MapperUtil.mapOne(appointmentDto.getAppointmentStatus(), AppointmentStatus.class);
                }
                if(appointmentDto.getBookedDate() != null){
                        this.bookedDate = appointmentDto.getBookedDate();
                }
                if(appointmentDto.getActualStartTime() != null){
                        this.actualStartTime = appointmentDto.getActualStartTime();
                }
                if(appointmentDto.getActualEndTime() != null){
                        this.actualEndTime = appointmentDto.getActualEndTime();
                }
           return this;
        }
}
