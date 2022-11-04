package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.DoctorDto;
import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;
import com.doctor.appointment.domain.dtos.HospitalDto;
import com.doctor.appointment.domain.entities.Doctor;
import com.doctor.appointment.persistence.IDoctorRepository;
import com.doctor.appointment.service.signatures.IDoctorServiceService;
import com.doctor.appointment.service.signatures.IGenericService;
import com.doctor.appointment.utils.AppUtil;
import com.doctor.appointment.utils.errors.ResourceNotFoundException;
import com.doctor.appointment.utils.mappers.MapperUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

@Slf4j
@NoArgsConstructor
@Transactional
@Service
public class DoctorService implements IGenericService<DoctorDto>, IDoctorServiceService {

    private IDoctorRepository doctorRepo;
    private HospitalService hospitalService;
    private DoctorSpecializationService doctorSpecializationService;

    @Autowired
    public DoctorService(IDoctorRepository doctorRepo, HospitalService hospitalService,
                         DoctorSpecializationService doctorSpecializationService) {
        this.doctorRepo = doctorRepo;
        this.hospitalService = hospitalService;
        this.doctorSpecializationService = doctorSpecializationService;
    }

    @Override
    public DoctorDto getOne(Long id) {
        log.info("Trying to get one Doctor entity with id: {}", id);
        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Doctor" , id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(doctor, DoctorDto.class);
    }

    @Override
    public List<DoctorDto> getAll() {
        List<Doctor> doctors = new ArrayList<>();
        log.info("Trying to get all Doctor entities.");
        try {
            doctors = doctorRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Doctor", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(doctors, DoctorDto.class);
    }

    @Override
    public Page<DoctorDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Doctor Pageable entities.");
        Page<Doctor> pagedResult = doctorRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<DoctorDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), DoctorDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Doctor Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }


    @Override
    public List<DoctorDto> getAll(Integer pageNo, Integer pageSize, String sortBy, Boolean ascending) {
        log.debug("getAll Doctors by pagination");
        Pageable paging = (sortBy != null && !sortBy.isEmpty())
                ? PageRequest.of(pageNo, pageSize)
                : PageRequest.of(pageNo, pageSize, ((ascending) ? Sort.by(sortBy).ascending() : Sort.by(sortBy)));
        Page<Doctor> pagedResult = doctorRepo.findAll(paging);
        if (pagedResult.hasContent()) {
            return MapperUtil.mapList(pagedResult.getContent(), DoctorDto.class);
        } else {
            String message = AppUtil.buildMessage("Doctor Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }


    @Override
    public DoctorDto createOne(DoctorDto dto) {
        log.info("Into createOne Doctor entity ..");
        Doctor doctor = doctorRepo.save(MapperUtil.mapOne(dto, Doctor.class));
        return MapperUtil.mapOne(doctor, DoctorDto.class);
    }

    @Override
    public Boolean createAll(List<DoctorDto> dtos) {
        log.info("Into createAll Doctor entities ..");
        return doctorRepo.saveAll(MapperUtil.mapList(dtos, Doctor.class)).size() > 0;
    }

    @Override
    public DoctorDto updateOne(DoctorDto dto) {
        log.info("Into updateOne Doctor entity ..");
        DoctorDto retrievedDoctorDto = getOne(dto.getId());
        Doctor doctor = MapperUtil.mapOne(retrievedDoctorDto, Doctor.class);
        doctor = doctor.fromDto(dto);
        doctor = doctorRepo.save(doctor);
        return MapperUtil.mapOne(doctor, DoctorDto.class);
    }

    @Override
    public Boolean updateAll(List<DoctorDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            DoctorDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one Doctor entity with id: {}", id);
        doctorRepo.deleteById(id);
        return getOne(id) == null;
    }

    @Override
    public void removeAll() {
        doctorRepo.deleteAll();
    }

    @Override
    public List<DoctorSpecializationDto> doctorSpecializationAndHospital(String doctorSpecialization, String hospitalName) {
        HospitalDto h = hospitalService.findHospitalByName(hospitalName);
        List<DoctorSpecializationDto> doctorSpecializations = doctorSpecializationService.doctorSpecialization(doctorSpecialization);
        return doctorSpecializations.stream().filter(ds->ds.getDoctor().getHospital().getName().equalsIgnoreCase(h.getName())).collect(Collectors.toList());
    }


}
