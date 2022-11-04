package com.doctor.appointment.service.impls;

import com.doctor.appointment.domain.dtos.*;
import com.doctor.appointment.domain.entities.Appointment;
import com.doctor.appointment.domain.state.AppointmentState;
import com.doctor.appointment.persistence.IAppointmentRepository;
import com.doctor.appointment.service.signatures.IAppointmentService;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.doctor.appointment.utils.IApConstant.ALREADY_BOOKED;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Slf4j
@NoArgsConstructor
@Transactional
@Service
public class AppointmentService implements IGenericService<AppointmentDto>, IAppointmentService {

    private IAppointmentRepository appointmentRepo;
    private PatientService patientService;
    private OfficeService officeService;
    private OfficeDoctorAvailabilityService officeDoctorAvailabilityService;
    private AppointmentStatusService appointmentStatusService;

    @Autowired
    public AppointmentService(IAppointmentRepository appointmentRepo,
                              PatientService patientService,
                              OfficeService officeService,
                              OfficeDoctorAvailabilityService officeDoctorAvailabilityService,
                              AppointmentStatusService appointmentStatusService) {
        this.appointmentRepo = appointmentRepo;
        this.patientService = patientService;
        this.officeService = officeService;
        this.officeDoctorAvailabilityService = officeDoctorAvailabilityService;
        this.appointmentStatusService = appointmentStatusService;
    }

    @Override
    public AppointmentDto getOne(Long id) {
        log.info("Trying to get one Appointment entity with id: {}", id);
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() -> {
                    String message = AppUtil.buildMessage("Appointment", id);
                    log.error(message);
                    return new ResourceNotFoundException(message);
                });
        return MapperUtil.mapOne(appointment, AppointmentDto.class);
    }

    @Override
    public List<AppointmentDto> getAll() {
        List<Appointment> appointments = new ArrayList<>();
        log.info("Trying to get all Appointment entities.");
        try {
            appointments = appointmentRepo.findAll();
        } catch(Exception e){
            String message = AppUtil.buildMessage("Appointment", e);
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
        return MapperUtil.mapList(appointments, AppointmentDto.class);
    }

    @Override
    public Page<AppointmentDto> getAllPaged(Pageable paging) {
        log.info("Trying to get all Patient Pageable entities.");
        Page<Appointment> pagedResult = appointmentRepo.findAll(paging);
        if(pagedResult.hasContent()) {
            List<AppointmentDto> cDtoList = MapperUtil.mapList(pagedResult.getContent(), AppointmentDto.class);
            return new PageImpl<>(cDtoList, paging, pagedResult.getTotalElements());
        } else {
            String message = AppUtil.buildMessage("Appointment Pageable");
            log.error(message);
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public AppointmentDto createOne(AppointmentDto dto) {
        log.info("Into createOne Appointment entity ..");
        Appointment appointment = appointmentRepo.save(MapperUtil.mapOne(dto, Appointment.class));
        return MapperUtil.mapOne(appointment, AppointmentDto.class);
    }

    @Override
    public Boolean createAll(List<AppointmentDto> dtos) {
        log.info("Into createAll Appointment entities ..");
        return appointmentRepo.saveAll(MapperUtil.mapList(dtos, Appointment.class)).size() > 0;
    }

    @Override
    public AppointmentDto updateOne(AppointmentDto dto) {
        log.info("Into updateOne Appointment entity ..");
        AppointmentDto retrievedAppointmentDto = getOne(dto.getId());
        Appointment appointment = MapperUtil.mapOne(retrievedAppointmentDto, Appointment.class);
        appointment = appointment.fromDto(dto);
        appointment = appointmentRepo.save(appointment);
        return MapperUtil.mapOne(appointment, AppointmentDto.class);
    }

    @Override
    public Boolean updateAll(List<AppointmentDto> dtos) {
        int dtoSize = dtos.size();
        AtomicInteger updated = new AtomicInteger();
        dtos.forEach(dto -> {
            AppointmentDto updatedDto = updateOne(dto);
            if(updatedDto != null){
                updated.getAndIncrement();
            }
        });
        return (dtoSize == updated.get());
    }

    @Override
    public boolean deleteOne(Long id) {
        log.info("Trying to delete one Appointment entity with id: {}", id);
        appointmentRepo.deleteById(id);
        return (getOne(id) == null);
    }

    @Override
    public void removeAll() {
        appointmentRepo.deleteAll();
    }

    /**
     * for some reasons, it recreates the appointment and that's the reason the constrain at entity was commented
     * @param addAppointmentRequest
     * @return
     */
    @Override
    public AppointmentDto bookAppointmentFor(AddAppointmentRequest addAppointmentRequest) {
            PatientDto patientDto = patientService.getOne(addAppointmentRequest.getPatientId());
            OfficeDto officeDto = officeService.getOne(addAppointmentRequest.getAvailability().getOfficeId());

            OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto = new OfficeDoctorAvailabilityDto();
            officeDoctorAvailabilityDto.setAvailability(Boolean.FALSE);
            officeDoctorAvailabilityDto.setOffice(officeDto);
            officeDoctorAvailabilityDto.setAvailabilityDate(addAppointmentRequest.getAvailability().getAvailabilityDate());
            officeDoctorAvailabilityDto.setUnAvailabilityReason(ALREADY_BOOKED);
            officeDoctorAvailabilityDto.setStartTime(addAppointmentRequest.getAvailability().getStartTime());
            officeDoctorAvailabilityDto.setEndTime(addAppointmentRequest.getAvailability().getEndTime());
            officeDoctorAvailabilityService.createOne(officeDoctorAvailabilityDto);

            AppointmentDto appointment = new AppointmentDto();
            appointment.setAppointmentStatus(appointmentStatusService.findByState(AppointmentState.BOOKED));
            appointment.setOffice(officeDto);
            // LocalDateTime ls = DateUtils.dateToLocalDateTime(addAppointmentRequest.getAvailability().getAvailabilityDate());

            appointment.setActualStartTime(addAppointmentRequest.getAvailability().getStartTime());
            appointment.setActualEndTime(addAppointmentRequest.getAvailability().getEndTime() );
            appointment.setPatient(patientDto);
            appointment.setBookedDate(new Date());
            Appointment a = MapperUtil.mapOne(appointment, Appointment.class);

            a = appointmentRepo.save(a) ;

         return MapperUtil.mapOne(a, AppointmentDto.class);
    }
}
