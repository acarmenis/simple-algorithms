package com.doctor.appointment.service.signatures;

import com.doctor.appointment.domain.dtos.PageAbleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
public interface IGenericService <T> extends IPagingService<T> {
    T getOne(Long id);
    List<T> getAll();
    Page<T> getAllPaged(Pageable paging);
    T createOne(T dto);
    Boolean createAll(List<T> dtos);
    T updateOne(T dto);
    Boolean updateAll(List<T> dtos);
    boolean deleteOne(Long id);
    void removeAll();

    /**
     * Default implementation for a pageable response for get all
     * @param pageNo the page number that is requested
     * @param pageSize the page size
     * @param sortBy the sort by clause
     * @param ascending the direction
     * @return a PageAbleDto holding the response
     */
    default PageAbleDto<T> getAllPageable(Integer pageNo, Integer pageSize, String sortBy, Boolean ascending) {
        Pageable paging = PageRequest.of(pageNo, pageSize, ( (ascending) ? Sort.by(sortBy).ascending() : Sort.by(sortBy) ) );
        Page<T> page = getAllPaged(paging);
        return getPageable(page);
    }
}
