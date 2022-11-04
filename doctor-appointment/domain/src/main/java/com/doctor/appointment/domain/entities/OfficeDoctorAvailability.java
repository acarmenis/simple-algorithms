package com.doctor.appointment.domain.entities;

import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.OfficeDoctorAvailabilityDto;
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
@Table(name= "DOCTOR_OFFICE_AVAILABILITY",
        indexes = {
                @Index(name = "INDEX_ON_DOCTOR_DOCTOR_OFFICE_AVAILABILITY", columnList = "id")
        })
@org.hibernate.annotations.Table(appliesTo = "doctor_office_availability", comment = "DOCTOR OFFICE AVAILABILITY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OfficeDoctorAvailability extends BaseEntity {
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "OFFICE_ID", nullable = false, foreignKey=@ForeignKey(name = "OFFICE_FK"))
    @Comment("OFFICE PK")
    @JsonBackReference
    private Office office;
    @Column(name = "IS_AVAILABLE", columnDefinition = "boolean default true")
    @Comment("IS AVAILABLE")
    private boolean availability;
    @Column(name = "AVAILABILITY_DATE", columnDefinition = "DATE")
    @Comment("AVAILABILITY DATE")
    @Temporal(TemporalType.DATE)
    private Date availabilityDate;
    @Column(name = "START_TIME", columnDefinition = "TIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "END_TIME", columnDefinition = "TIME")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Column(name = "UN_AVAILABILITY_REASON", length = 500)
    @Comment("UN AVAILABILITY REASON - IF SO")
    private String unAvailabilityReason;

    public OfficeDoctorAvailability fromDto(OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto){
        if(officeDoctorAvailabilityDto.getId() != null){
            this.id=officeDoctorAvailabilityDto.getId();
        }
        if(officeDoctorAvailabilityDto.getOffice() != null){
            this.office=MapperUtil.mapOne(officeDoctorAvailabilityDto.getOffice(), Office.class);
        }
        this.availability = officeDoctorAvailabilityDto.isAvailability();
        if(officeDoctorAvailabilityDto.getAvailabilityDate() != null){
            this.availabilityDate = officeDoctorAvailabilityDto.getAvailabilityDate();
        }
        if(officeDoctorAvailabilityDto.getStartTime() != null){
            this.startTime = officeDoctorAvailabilityDto.getStartTime();
        }
        if(officeDoctorAvailabilityDto.getEndTime() != null){
            this.endTime = officeDoctorAvailabilityDto.getEndTime();
        }
        if(officeDoctorAvailabilityDto.getUnAvailabilityReason() != null){
            this.unAvailabilityReason = officeDoctorAvailabilityDto.getUnAvailabilityReason();
        }
        return this;
    }

}
