package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;
import com.doctor.appointment.domain.entities.DoctorSpecialization;
import com.doctor.appointment.persistence.IDoctorSpecializationRepository;
import com.doctor.appointment.service.signatures.IDoctorSpecializationService;
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
public class DoctorSpecializationService implements IGenericService<DoctorSpecializationDto>, IDoctorSpecializationService {

    private IDoctorSpecializationRepository doctorSpecializationRepo;

    @Autowired
    public DoctorSpecializationService(IDoctorSpecializationRepository doctorSpecializationRepo) {
        this.doctorSpecializationRepo = doctorSpecializationRepo;
    }

    @Override
    public DoctorSpecializationDto getOne(Long id) {
        log.info("Trying to get one DoctorSpecialization entity with id: {}", id);
        DoctorSpecialization doctorSpecialization = doctorSpecializationRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("DoctorSpecialization" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(doctorSpecialization, DoctorSpecializationDto.class);
    }

    @Override
    public List<DoctorSpecializationDto> getAll() {
        List<DoctorSpecialization> doctorSpecializations = new ArrayList<>();
        log.info("Trying to get all DoctorSpecialization entities.");
        try {
            doctorSpecializations = doctorSpecializationRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("DoctorSpecialization", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(doctorSpecializations, DoctorSpecializationDto.class);
    }

    @Override
    public Page<DoctorSpecializationDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all DoctorSpecialization Pageable entities.");
        Page<DoctorSpecialization> pagedResult = doctorSpecializationRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<DoctorSpecializationDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), DoctorSpecializationDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("doctorSpecializationRepo Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public DoctorSpecializationDto createOne(DoctorSpecializationDto dto) {
        log.info("Into createOne DoctorSpecialization entity ..");
        DoctorSpecialization doctorSpecialization = doctorSpecializationRepo.save(MapperUtil.mapOne(dto, DoctorSpecialization.class));
        return MapperUtil.mapOne(doctorSpecialization, DoctorSpecializationDto.class);
    }

    @Override
    public Boolean createAll(List<DoctorSpecializationDto> dtos) {
        log.info("Into createAll DoctorSpecialization entities ..");
        return doctorSpecializationRepo.saveAll(MapperUtil.mapList(dtos, DoctorSpecialization.class)).size() > 0;
    }

    @Override
    public DoctorSpecializationDto updateOne(DoctorSpecializationDto dto) {
        log.info("Into updateOne DoctorSpecialization entity ..");
        DoctorSpecializationDto retrievedDoctorSpecializationDto = getOne(dto.getId());
        DoctorSpecialization doctorSpecialization = MapperUtil.mapOne(retrievedDoctorSpecializationDto, DoctorSpecialization.class);
        doctorSpecialization = doctorSpecialization.fromDto(dto);
        doctorSpecialization = doctorSpecializationRepo.save(doctorSpecialization);
        return MapperUtil.mapOne(doctorSpecialization, DoctorSpecializationDto.class);
    }

    @Override
    public Boolean updateAll(List<DoctorSpecializationDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            DoctorSpecializationDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one DoctorSpecialization entity with id: {}", id);
        doctorSpecializationRepo.deleteById(id);
        return (getOne(id) == null);
    }

    @Override
    public void removeAll() {
        doctorSpecializationRepo.deleteAll();
    }

    @Override
    public List<DoctorSpecializationDto> doctorSpecialization(String doctorSpecialization) {
        List<DoctorSpecialization> doctorSpecializations = doctorSpecializationRepo.findAll((root, criteriaQuery, cb) ->
                cb.equal(root.get("specialization").get("specializationName"), doctorSpecialization));
        if (doctorSpecializations.isEmpty()) {
            String message = AppUtil.buildMessage("DoctorSpecialization for specialization: "+doctorSpecialization);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(doctorSpecializations, DoctorSpecializationDto.class);
    }
}
