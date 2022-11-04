package com.doctor.appointment.domain.entities;


import com.doctor.appointment.domain.audit.BaseEntity;
import com.doctor.appointment.domain.dtos.SeeingADoctorDto;
import com.doctor.appointment.utils.mappers.MapperUtil;
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
@Table(name= "SEEING_A_DOCTOR",
        indexes = {
                @Index(name = "INDEX_ON_SEEING_A_DOCTOR", columnList = "id")
        })
@org.hibernate.annotations.Table(appliesTo = "seeing_a_doctor", comment = "SEEING A DOCTOR")
public class SeeingADoctor extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DOCTOR_ID", nullable = false, foreignKey=@ForeignKey(name = "SEEING_A_DOCTOR_FK"))
    @Comment("DOCTOR PK")
    private Doctor doctor;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PATIENT_ID", nullable = false, foreignKey=@ForeignKey(name = "PATIENT_FK"))
    @Comment("PATIENT PK")
    private Patient patient;
    @Column(name = "WAIT_TIME_RATING", length = 100)
    @Comment("WAIT TIME RATING")
    private Integer waitTimeRate;
    @Column(name = "BEHAVIOUR_MANNER_RATING", length = 100)
    @Comment("BEHAVIOUR MANNER RATING")
    private Integer behaviourMannerRate;
    @Column(name = "REVIEW", length = 500)
    @Comment("REVIEW")
    private String review;
    @Column(name = "IS_RECOMMENDED")
    @Comment("IS RECOMMENDED")
    private boolean recommended;
    @Column(name = "REVIEW_DATE")
    @Comment("REVIEW DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewDate;

    public SeeingADoctor fromDto(SeeingADoctorDto seeingADoctorDto){
        if(seeingADoctorDto.getId() != null){
            this.id=seeingADoctorDto.getId();
        }
        if(seeingADoctorDto.getDoctor() != null){
            this.doctor= MapperUtil.mapOne(seeingADoctorDto.getDoctor(), Doctor.class);
        }
        if(seeingADoctorDto.getPatient() != null){
            this.patient = MapperUtil.mapOne(seeingADoctorDto.getPatient(), Patient.class);
        }
        if(seeingADoctorDto.getWaitTimeRate() != null){
            this.waitTimeRate = seeingADoctorDto.getWaitTimeRate();
        }
        if(seeingADoctorDto.getBehaviourMannerRate() != null){
            this.behaviourMannerRate = seeingADoctorDto.getBehaviourMannerRate();
        }
        if(seeingADoctorDto.getReview() != null){
            this.review = seeingADoctorDto.getReview();
        }
        this.recommended = seeingADoctorDto.isRecommended();
        if(seeingADoctorDto.getReviewDate() != null){
            this.reviewDate = seeingADoctorDto.getReviewDate();
        }
        return this;
    }
}
