package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.HospitalDto;
import com.doctor.appointment.domain.entities.Hospital;
import com.doctor.appointment.persistence.IHospitalRepository;
import com.doctor.appointment.service.signatures.IGenericService;
import com.doctor.appointment.service.signatures.IHospitalService;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

@Slf4j
@NoArgsConstructor
@Transactional
@Service
public class HospitalService implements IGenericService<HospitalDto>, IHospitalService {

    private IHospitalRepository hospitalRepo;

    @Autowired
    public HospitalService(IHospitalRepository hospitalRepo) {
        this.hospitalRepo = hospitalRepo;
    }

    @Override
    public HospitalDto getOne(Long id) {
        Hospital hospital = hospitalRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Hospital", id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(hospital, HospitalDto.class);
    }

    @Override
    public List<HospitalDto> getAll() {
        List<Hospital> hospitals = new ArrayList<>();
        log.info("Trying to get all Hospital entities.");
        try {
            hospitals = hospitalRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Hospital", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(hospitals, HospitalDto.class);
    }

    @Override
    public Page<HospitalDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Doctor Pageable entities.");
        Page<Hospital> pagedResult = hospitalRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<HospitalDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), HospitalDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Hospital Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public HospitalDto createOne(HospitalDto dto) {
        log.info("Into createOne Hospital entity ..");
        Hospital hospital = hospitalRepo.save(MapperUtil.mapOne(dto, Hospital.class));
        return MapperUtil.mapOne(hospital, HospitalDto.class);
    }

    @Override
    public Boolean createAll(List<HospitalDto> dtos) {
        log.info("Into createAll Hospital entities ..");
        return hospitalRepo.saveAll(MapperUtil.mapList(dtos, Hospital.class)).size() > 0;
    }

    @Override
    public HospitalDto updateOne(HospitalDto dto) {
        log.info("Into updateOne Doctor entity ..");
        HospitalDto retrievedHospitalDto = getOne(dto.getId());
        Hospital hospital = MapperUtil.mapOne(retrievedHospitalDto, Hospital.class);
        hospital = hospital.fromDto(dto);
        hospital = hospitalRepo.save(hospital);
        return MapperUtil.mapOne(hospital, HospitalDto.class);
    }

    @Override
    public Boolean updateAll(List<HospitalDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            HospitalDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        hospitalRepo.deleteById(id);
        return getOne(id) == null;
    }

    @Override
    public void removeAll() {
        hospitalRepo.deleteAll();
    }

    @Override
    public HospitalDto findHospitalByName(String hospitalName) {
        Optional<Hospital> optionalHospital =
                hospitalRepo.findOne((root, criteriaQuery, cb) ->  cb.equal(root.get("name"), hospitalName) );
        if (optionalHospital.isEmpty()) {
            String message = AppUtil.buildMessage("Hospital with name: "+hospitalName);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapOne(optionalHospital.get(), HospitalDto.class);
    }
}
