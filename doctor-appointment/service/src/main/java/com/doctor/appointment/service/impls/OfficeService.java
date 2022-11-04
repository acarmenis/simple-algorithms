package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.OfficeDto;
import com.doctor.appointment.domain.entities.Office;
import com.doctor.appointment.persistence.IOfficeRepository;
import com.doctor.appointment.service.signatures.IGenericService;
import com.doctor.appointment.utils.AppUtil;
import com.doctor.appointment.utils.errors.ResourceNotFoundException;
import com.doctor.appointment.utils.mappers.MapperUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

@Slf4j
@NoArgsConstructor
@Transactional
@Service
public class OfficeService implements IGenericService<OfficeDto> {

    private IOfficeRepository officeRepo;

    @Autowired
    public OfficeService(IOfficeRepository officeRepo) {
        this.officeRepo = officeRepo;
    }

    @Override
    public OfficeDto getOne(Long id) {
        Office office = officeRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Office", id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(office, OfficeDto.class);
    }

    @Override
    public List<OfficeDto> getAll() {
        List<Office> offices = new ArrayList<>();
        log.info("Trying to get all Office entities.");
        try {
            offices = officeRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Office", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(offices, OfficeDto.class);
    }

    @Override
    public Page<OfficeDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Office Pageable entities.");
        Page<Office> pagedResult = officeRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<OfficeDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), OfficeDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Office Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public OfficeDto createOne(OfficeDto dto) {
        log.info("Into createOne Office entity ..");
        Office office = officeRepo.save(MapperUtil.mapOne(dto, Office.class));
        return MapperUtil.mapOne(office, OfficeDto.class);
    }

    @Override
    public Boolean createAll(List<OfficeDto> dtos) {
        log.info("Into createAll Office entities ..");
        return officeRepo.saveAll(MapperUtil.mapList(dtos, Office.class)).size() > 0;
    }

    @Override
    public OfficeDto updateOne(OfficeDto dto) {
        log.info("Into updateOne Office entity ..");
        OfficeDto retrievedOfficeDto = getOne(dto.getId());
        Office office = MapperUtil.mapOne(retrievedOfficeDto, Office.class);
        office = office.fromDto(dto);
        office = officeRepo.save(office);
        return MapperUtil.mapOne(office, OfficeDto.class);
    }

    @Override
    public Boolean updateAll(List<OfficeDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            OfficeDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        officeRepo.deleteById(id);
        return getOne(id) == null;
    }

    @Override
    public void removeAll() {
        officeRepo.deleteAll();
    }
}
