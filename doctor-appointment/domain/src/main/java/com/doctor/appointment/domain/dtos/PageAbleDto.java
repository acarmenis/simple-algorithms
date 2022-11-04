package com.doctor.appointment.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PageAbleDto<I> {
    private List<I> data;
    private Integer pageNumber;
    private Long totalElements;
    private Integer totalPages;
}
