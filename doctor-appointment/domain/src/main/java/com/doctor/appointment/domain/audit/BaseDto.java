package com.doctor.appointment.domain.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseDto extends Auditable<String> {
    private Long id;
    @JsonIgnore
    private Long version;
}
