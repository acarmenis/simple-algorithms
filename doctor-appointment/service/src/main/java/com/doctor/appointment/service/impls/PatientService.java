package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.PatientDto;
import com.doctor.appointment.domain.entities.Patient;
import com.doctor.appointment.persistence.IPatientRepository;
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
public class PatientService implements IGenericService<PatientDto> {

    private IPatientRepository patientRepo;

    @Autowired
    public PatientService(IPatientRepository patientRepo) {
        this.patientRepo = patientRepo;
    }

    @Override
    public PatientDto getOne(Long id) {
        log.info("Trying to get one Patient entity with id: {}", id);
        Patient patient = patientRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Patient" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(patient, PatientDto.class);
    }

    @Override
    public List<PatientDto> getAll() {
        List<Patient> patients = new ArrayList<>();
        log.info("Trying to get all Patient entities.");
        try {
            patients = patientRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Patient", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(patients, PatientDto.class);
    }

    @Override
    public Page<PatientDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Patient Pageable entities.");
        Page<Patient> pagedResult = patientRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<PatientDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), PatientDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Patient Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public PatientDto createOne(PatientDto dto) {
        log.info("Into createOne Patient entity ..");
        Patient patient = patientRepo.save(MapperUtil.mapOne(dto, Patient.class));
        return MapperUtil.mapOne(patient, PatientDto.class);
    }

    @Override
    public Boolean createAll(List<PatientDto> dtos) {
        log.info("Into createAll Patient entities ..");
        return patientRepo.saveAll(MapperUtil.mapList(dtos, Patient.class)).size() > 0;
    }

    @Override
    public PatientDto updateOne(PatientDto dto) {
        log.info("Into updateOne Doctor entity ..");
        PatientDto retrievedPatientDto = getOne(dto.getId());
        Patient patient = MapperUtil.mapOne(retrievedPatientDto, Patient.class);
        patient = patient.fromDto(dto);
        patient = patientRepo.save(patient);
        return MapperUtil.mapOne(patient, PatientDto.class);
    }

    @Override
    public Boolean updateAll(List<PatientDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            PatientDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one Patient entity with id: {}", id);
        patientRepo.deleteById(id);
        return (getOne(id) == null);
    }

    @Override
    public void removeAll() {
        patientRepo.deleteAll();
    }
}
