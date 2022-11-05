package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.Availability;
import com.doctor.appointment.domain.dtos.OfficeDoctorAvailabilityDto;
import com.doctor.appointment.domain.entities.OfficeDoctorAvailability;
import com.doctor.appointment.persistence.IOfficeDoctorAvailabilityRepository;
import com.doctor.appointment.service.signatures.IGenericService;
import com.doctor.appointment.service.signatures.IOfficeDoctorAvailabilityService;
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
import java.util.Date;
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
public class OfficeDoctorAvailabilityService implements IGenericService<OfficeDoctorAvailabilityDto>, IOfficeDoctorAvailabilityService {

    private IOfficeDoctorAvailabilityRepository officeDoctorAvailabilityRepo;

    @Autowired
    public OfficeDoctorAvailabilityService(IOfficeDoctorAvailabilityRepository officeDoctorAvailabilityRepo) {
        this.officeDoctorAvailabilityRepo = officeDoctorAvailabilityRepo;
    }

    @Override
    public OfficeDoctorAvailabilityDto getOne(Long id) {
        log.info("Trying to get one OfficeDoctorAvailability entity with id: {}", id);
        OfficeDoctorAvailability officeDoctorAvailability = officeDoctorAvailabilityRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("OfficeDoctorAvailability" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(officeDoctorAvailability, OfficeDoctorAvailabilityDto.class);
    }

    @Override
    public List<OfficeDoctorAvailabilityDto> getAll() {
        List<OfficeDoctorAvailability> officeDoctorAvailabilities = new ArrayList<>();
        log.info("Trying to get all OfficeDoctorAvailability entities.");
        try {
            officeDoctorAvailabilities = officeDoctorAvailabilityRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("OfficeDoctorAvailability", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(officeDoctorAvailabilities, OfficeDoctorAvailabilityDto.class);
    }

    @Override
    public Page<OfficeDoctorAvailabilityDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all OfficeDoctorAvailability Pageable entities.");
        Page<OfficeDoctorAvailability> pagedResult = officeDoctorAvailabilityRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<OfficeDoctorAvailabilityDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), OfficeDoctorAvailabilityDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("OfficeDoctorAvailability Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public OfficeDoctorAvailabilityDto createOne(OfficeDoctorAvailabilityDto dto) {
        log.info("Into createOne OfficeDoctorAvailability entity ..");
        OfficeDoctorAvailability officeDoctorAvailability = officeDoctorAvailabilityRepo.save(MapperUtil.mapOne(dto, OfficeDoctorAvailability.class));
        return MapperUtil.mapOne(officeDoctorAvailability, OfficeDoctorAvailabilityDto.class);
    }

    @Override
    public Boolean createAll(List<OfficeDoctorAvailabilityDto> dtos) {
        log.info("Into createAll OfficeDoctorAvailability entities ..");
        return officeDoctorAvailabilityRepo.saveAll(MapperUtil.mapList(dtos, OfficeDoctorAvailability.class)).size() > 0;
    }

    @Override
    public OfficeDoctorAvailabilityDto updateOne(OfficeDoctorAvailabilityDto dto) {
        log.info("Into updateOne Doctor entity ..");
        OfficeDoctorAvailabilityDto retrievedOfficeDoctorAvailabilityDto = getOne(dto.getId());
        OfficeDoctorAvailability officeDoctorAvailability = MapperUtil.mapOne(retrievedOfficeDoctorAvailabilityDto, OfficeDoctorAvailability.class);
        officeDoctorAvailability = officeDoctorAvailability.fromDto(officeDoctorAvailability, dto);
        try {
            officeDoctorAvailability = officeDoctorAvailabilityRepo.save(officeDoctorAvailability);
        } catch(Exception e){
            String message = AppUtil.buildMessage("OfficeDoctorAvailability "+e.getMessage());
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapOne(officeDoctorAvailability, OfficeDoctorAvailabilityDto.class);
    }

    @Override
    public Boolean updateAll(List<OfficeDoctorAvailabilityDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            OfficeDoctorAvailabilityDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one OfficeDoctorAvailability entity with id: {}", id);
        officeDoctorAvailabilityRepo.deleteById(id);
        return (getOne(id) == null);
    }

    @Override
    public void removeAll() {
        officeDoctorAvailabilityRepo.deleteAll();
    }

    @Override
    public List<OfficeDoctorAvailabilityDto> availabilitiesOfADoctor(long doctorId) {
        return null;
    }

    @Override
    public List<OfficeDoctorAvailabilityDto> availabilitiesOfADoctor(long doctorId, Date date) {
        return null;
    }

    @Override
    public List<Availability> getAvailabilitiesOfADoctor(boolean enabled, String specializationName, String hospitalNameName) {
        return officeDoctorAvailabilityRepo.getDoctorAvailabilities(enabled, specializationName, hospitalNameName);
    }
}
