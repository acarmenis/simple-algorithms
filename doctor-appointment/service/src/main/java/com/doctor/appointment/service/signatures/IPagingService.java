package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.PageAbleDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
public interface IPagingService <P> {
    /**
     * Default implementation for pageable response for every search action
     * @param page the page with the data
     * @return a PageAbleDto holding data and page information
     */
    default PageAbleDto<P> getPageable(Page<P> page) {
        List<P> data = page.getContent();
        return new PageAbleDto<>(data,  page.getNumber(), page.getTotalElements(),  page.getTotalPages());
    }

    /**
     * Default Generic implementation for pageable response for every search action
     * @param page the page with the data
     * @return a map holding data and page information
     */
    default PageAbleDto<?> getGenericDataPageable(Page<?> page) {
        List<?> data = page.getContent();
        return  new PageAbleDto<>(data, page.getNumber(), page.getTotalElements(), page.getTotalPages());
    }
}
