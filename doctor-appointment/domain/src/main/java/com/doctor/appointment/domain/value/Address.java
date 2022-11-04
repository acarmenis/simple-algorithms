package com.doctor.appointment.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

/**
 * @Embeddable annotation declares that a class will be embedded by other entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    @Column(name = "STREET", length = 100)
    @Comment("STREET")
    private String street;
    @Column(name = "STREET_NUMBER")
    @Comment("STREET NUMBER")
    private String streetNumber;
    @Column(name = "CITY", length = 100)
    @Comment("CITY")
    private String city;
    @Column(name = "COUNTRY", length = 100)
    @Comment("COUNTRY")
    private String country;
    @Column(name = "ZIP_CODE", length = 5)
    @Comment("ZIP_CODE")
    private String zipCode;
}
