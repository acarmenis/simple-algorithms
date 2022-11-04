package com.doctor.appointment.domain.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */

/**
 *
 * @CreatedDate: represents the date when the field was created.
 * @CreatedBy: represents the principal(user) that created the entity.
 * @LastModifiedDate: represents the date the entity was recently modified.
 * @LastModifiedBy: represents the principal(user) that recently modified the entity.
 * @MappedSuperclass: represents that this class is parent class and will be extended by other audited entities.
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> implements Serializable {
    @CreatedBy
    @JsonIgnore
    @Column(name = "created_by")
    @Comment("CREATED BY")
    protected U createdBy;
    @CreatedDate
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @Comment("CREATED DATE")
    protected Date createdOn;
    @LastModifiedBy
    @JsonIgnore
    @Column(name = "modified_by")
    @Comment("MODIFIED BY")
    protected U modifiedBy;
    @LastModifiedDate
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on")
    @Comment("MODIFIED DATE")
    protected Date modifiedOn;
}

