package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.AppointmentStatusDto;
import com.doctor.appointment.domain.dtos.PageAbleDto;
import com.doctor.appointment.domain.entities.AppointmentStatus;
import com.doctor.appointment.domain.state.AppointmentState;
import com.doctor.appointment.persistence.IAppointmentStatusRepository;
import com.doctor.appointment.service.signatures.IAppointmentStatusService;
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
import java.util.Optional;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Slf4j
@NoArgsConstructor
@Transactional
@Service
public class AppointmentStatusService implements IGenericService<AppointmentStatusDto>, IAppointmentStatusService {

    private IAppointmentStatusRepository appointmentStatusRepo;

    @Autowired
    public AppointmentStatusService(IAppointmentStatusRepository appointmentStatusRepo) {
        this.appointmentStatusRepo = appointmentStatusRepo;
    }

    @Override
    public AppointmentStatusDto getOne(Long id) {
        log.info("Trying to get one AppointmentStatus entity with id: {}", id);
        AppointmentStatus appointmentStatus = appointmentStatusRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("AppointmentState", id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(appointmentStatus, AppointmentStatusDto.class);
    }

    @Override
    public List<AppointmentStatusDto> getAll() {
        List<AppointmentStatus> appointmentStatuses = new ArrayList<>();
        log.info("Trying to get all AppointmentStatus entities.");
        try {
            appointmentStatuses = appointmentStatusRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("AppointmentStatus", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(appointmentStatuses, AppointmentStatusDto.class);
    }

    @Override
    public Page<AppointmentStatusDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all AppointmentStatus Pageable entities.");
        Page<AppointmentStatus> pagedResult = appointmentStatusRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<AppointmentStatusDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), AppointmentStatusDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("AppointmentStatus Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public AppointmentStatusDto createOne(AppointmentStatusDto dto) {
        log.info("Into createOne AppointmentStatus entity ..");
        AppointmentStatus appointmentStatus = appointmentStatusRepo.save(MapperUtil.mapOne(dto, AppointmentStatus.class));
        return MapperUtil.mapOne(appointmentStatus, AppointmentStatusDto.class);
    }

    @Override
    public Boolean createAll(List<AppointmentStatusDto> dtos) {
        log.info("Into createAll AppointmentStatus entities ..");
        return appointmentStatusRepo.saveAll(MapperUtil.mapList(dtos, AppointmentStatus.class)).size() > 0;
    }

    @Override
    public AppointmentStatusDto updateOne(AppointmentStatusDto dto) {
        log.info("Into updateOne AppointmentStatus entity ..");
        AppointmentStatusDto retrievedAppointmentStatusDto = getOne(dto.getId());
        AppointmentStatus appointmentStatus = MapperUtil.mapOne(retrievedAppointmentStatusDto, AppointmentStatus.class);
        appointmentStatus = appointmentStatus.fromDto(dto);
        appointmentStatus = appointmentStatusRepo.save(appointmentStatus);
        return MapperUtil.mapOne(appointmentStatus, AppointmentStatusDto.class);
    }

    @Override
    public Boolean updateAll(List<AppointmentStatusDto> dtos) {
        return null;
    }

    @Override
    public boolean deleteOne(Long id) {
        return false;
    }

    @Override
    public void removeAll() {
        appointmentStatusRepo.deleteAll();
    }

    @Override
    public PageAbleDto<AppointmentStatusDto> getAllPageable(Integer pageNo, Integer pageSize, String sortBy, Boolean ascending) {
        return IGenericService.super.getAllPageable(pageNo, pageSize, sortBy, ascending);
    }

    @Override
    public AppointmentStatusDto findByState(AppointmentState appointmentState) {
         Optional<AppointmentStatus> optionalEntity = appointmentStatusRepo.findOne((root, criteriaQuery, cb) -> cb.equal(root.get("state"), appointmentState));
        if (optionalEntity.isEmpty()) {
            String message = AppUtil.buildMessage("AppointmentStatus for: " + appointmentState.toString());
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapOne(optionalEntity.get(), AppointmentStatusDto.class);
    }
}
