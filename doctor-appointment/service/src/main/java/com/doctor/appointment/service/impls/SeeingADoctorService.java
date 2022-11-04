package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.SeeingADoctorDto;
import com.doctor.appointment.domain.entities.SeeingADoctor;
import com.doctor.appointment.persistence.ISeeingADoctorRepository;
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
public class SeeingADoctorService implements IGenericService<SeeingADoctorDto> {

    private ISeeingADoctorRepository seeingADoctorRepo;

    @Autowired
    public SeeingADoctorService(ISeeingADoctorRepository seeingADoctorRepo) {
        this.seeingADoctorRepo = seeingADoctorRepo;
    }

    @Override
    public SeeingADoctorDto getOne(Long id) {
        log.info("Trying to get one SeeingADoctor entity with id: {}", id);
        SeeingADoctor seeingADoctor = seeingADoctorRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("SeeingADoctor" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(seeingADoctor, SeeingADoctorDto.class);
    }

    @Override
    public List<SeeingADoctorDto> getAll() {
        List<SeeingADoctor> seeingADoctors = new ArrayList<>();
        log.info("Trying to get all SeeingADoctor entities.");
        try {
            seeingADoctors = seeingADoctorRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("SeeingADoctor", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(seeingADoctors, SeeingADoctorDto.class);
    }

    @Override
    public Page<SeeingADoctorDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all SeeingADoctor Pageable entities.");
        Page<SeeingADoctor> pagedResult = seeingADoctorRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<SeeingADoctorDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), SeeingADoctorDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("SeeingADoctor Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public SeeingADoctorDto createOne(SeeingADoctorDto dto) {
        log.info("Into createOne SeeingADoctor entity ..");
        SeeingADoctor seeingADoctor = seeingADoctorRepo.save(MapperUtil.mapOne(dto, SeeingADoctor.class));
        return MapperUtil.mapOne(seeingADoctor, SeeingADoctorDto.class);
    }

    @Override
    public Boolean createAll(List<SeeingADoctorDto> dtos) {
        log.info("Into createAll SeeingADoctor entities ..");
        return seeingADoctorRepo.saveAll(MapperUtil.mapList(dtos, SeeingADoctor.class)).size() > 0;
    }

    @Override
    public SeeingADoctorDto updateOne(SeeingADoctorDto dto) {
        log.info("Into updateOne SeeingADoctor entity ..");
        SeeingADoctorDto retrievedPatientDto = getOne(dto.getId());
        SeeingADoctor seeingADoctor = MapperUtil.mapOne(retrievedPatientDto, SeeingADoctor.class);
        seeingADoctor = seeingADoctor.fromDto(dto);
        seeingADoctor = seeingADoctorRepo.save(seeingADoctor);
        return MapperUtil.mapOne(seeingADoctor, SeeingADoctorDto.class);
    }

    @Override
    public Boolean updateAll(List<SeeingADoctorDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            SeeingADoctorDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one SeeingADoctor entity with id: {}", id);
        seeingADoctorRepo.deleteById(id);
        return (getOne(id) == null);
    }

    @Override
    public void removeAll() {
        seeingADoctorRepo.deleteAll();
    }
}
