package com.doctor.appointment.domain.value;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @Embeddable annotation declares that a class will be embedded by other entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Contact {
    @Column(name = "FIRST_NAME")
    @Comment("FIRST NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    @Comment("LAST NAME")
    private String lastName;
    @Column(name = "PHONE")
    @Comment("PHONE")
    private String phone;
    @Comment("EMAIL")
    private String email;
}
