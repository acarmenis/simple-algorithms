package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.SpecializationDto;
import com.doctor.appointment.domain.entities.Specialization;
import com.doctor.appointment.persistence.ISpecializationRepository;
import com.doctor.appointment.service.signatures.IGenericService;
import com.doctor.appointment.utils.AppUtil;
import com.doctor.appointment.utils.errors.ResourceNotFoundException;
import com.doctor.appointment.utils.mappers.MapperUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@NoArgsConstructor
@Transactional
@Service
public class SpecializationService implements IGenericService<SpecializationDto> {

    private ISpecializationRepository specializationRepo;

    @Autowired
    public SpecializationService(ISpecializationRepository specializationRepo) {
        this.specializationRepo = specializationRepo;
    }

    @Override
    public SpecializationDto getOne(Long id) {
        log.info("Trying to get one Specialization entity with id: {}", id);
        Specialization specialization = specializationRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Specialization" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(specialization, SpecializationDto.class);
    }

    @Override
    public List<SpecializationDto> getAll() {
        List<Specialization> specializations = new ArrayList<>();
        log.info("Trying to get all Doctor entities.");
        try {
            specializations = specializationRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Specialization", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(specializations, SpecializationDto.class);
    }

    @Override
    public Page<SpecializationDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Specialization Pageable entities.");
        Page<Specialization> pagedResult = specializationRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<SpecializationDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), SpecializationDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Specialization Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public SpecializationDto createOne(SpecializationDto dto) {
        log.info("Into createOne Specialization entity ..");
        Specialization specialization = specializationRepo.save(MapperUtil.mapOne(dto, Specialization.class));
        return MapperUtil.mapOne(specialization, SpecializationDto.class);
    }

    @Override
    public Boolean createAll(List<SpecializationDto> dtos) {
        log.info("Into createAll Specialization entities ..");
        return specializationRepo.saveAll(MapperUtil.mapList(dtos, Specialization.class)).size() > 0;
    }

    @Override
    public SpecializationDto updateOne(SpecializationDto dto) {
        log.info("Into updateOne Specialization entity ..");
        SpecializationDto retrievedSpecializationDto = getOne(dto.getId());
        Specialization specialization = MapperUtil.mapOne(retrievedSpecializationDto, Specialization.class);
        specialization = specialization.fromDto(dto);
        specialization = specializationRepo.save(specialization);
        return MapperUtil.mapOne(specialization, SpecializationDto.class);
    }

    @Override
    public Boolean updateAll(List<SpecializationDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            SpecializationDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one Specialization entity with id: {}", id);
        specializationRepo.deleteById(id);
        return getOne(id) == null;
    }

    @Override
    public void removeAll() {
        specializationRepo.deleteAll();
    }
}
