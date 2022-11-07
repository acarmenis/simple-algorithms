package com.doctor.appointment.web.app;

import com.doctor.appointment.domain.dtos.*;
import com.doctor.appointment.domain.state.AppointmentState;
import com.doctor.appointment.domain.value.Address;
import com.doctor.appointment.domain.value.Contact;
import com.doctor.appointment.service.impls.*;
import com.doctor.appointment.utils.AppUtil;
import com.doctor.appointment.utils.DateUtils;
import com.doctor.appointment.utils.errors.ResourceNotFoundException;
import com.doctor.appointment.web.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.doctor.appointment.utils.IApConstant.ALREADY_BOOKED;

/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-booking-appointment
 */

@Slf4j
@Configuration
@EnableTransactionManagement
@SpringBootApplication
@Import({  AppConfig.class  })
public class DoctorAppointmentApplication implements CommandLineRunner {

    @Autowired
    private SpecializationService specializationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentStatusService appointmentStatusService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeDoctorAvailabilityService officeDoctorAvailabilityService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorSpecializationService doctorSpecializationService;

    @Autowired
    private SeeingADoctorService seeingADoctorService;

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        /** Specialization initialization */
        specializationService.removeAll();
        patientService.removeAll();
        appointmentStatusService.removeAll();
        hospitalService.removeAll();
        doctorService.removeAll();
        officeService.removeAll();
        officeDoctorAvailabilityService.removeAll();
        appointmentService.removeAll();
        doctorSpecializationService.removeAll();
        seeingADoctorService.removeAll();

        SpecializationDto endocrinologist = new SpecializationDto();
        endocrinologist.setSpecializationName("Endocrinologist");

        SpecializationDto allergist = new SpecializationDto();
        allergist.setSpecializationName("Allergist");

        SpecializationDto immunologist = new SpecializationDto();
        immunologist.setSpecializationName("Immunologist");

        SpecializationDto cardiologist = new SpecializationDto();
        cardiologist.setSpecializationName("Cardiologist");

        SpecializationDto dermatologist = new SpecializationDto();
        dermatologist.setSpecializationName("Dermatologist");

        List<SpecializationDto> specializations = List.of(
                endocrinologist,
                allergist,
                immunologist,
                cardiologist,
                dermatologist
        );

        // save specializations
        specializationService.createAll(specializations);
        // select * from specialization;


        /** enroll Patients to application - initialization  */

        // create contact + address
        Contact contactJames = new Contact();
        contactJames.setEmail("a@b.com");
        contactJames.setFirstName("James");
        contactJames.setLastName("Bond");
        contactJames.setPhone("1234567890");

        Address addressJames = new Address();
        addressJames.setCity("Athens");
        addressJames.setCountry("Greece");
        addressJames.setStreet("Pathsion");
        addressJames.setStreetNumber("123");
        addressJames.setZipCode("12345");

         // create Patient james
        PatientDto james = new PatientDto();
        james.setAddress(addressJames);
        james.setContact(contactJames);
        james.setAppointments(null);


        // create contact + address
        Contact contactGeorge = new Contact();
        contactGeorge.setEmail("g@b.com");
        contactGeorge.setFirstName("George");
        contactGeorge.setLastName("Michel");
        contactGeorge.setPhone("4123658974");

        Address addressGeorge = new Address();
        addressGeorge.setCity("New York");
        addressGeorge.setCountry("USA");
        addressGeorge.setStreet("Wall street");
        addressGeorge.setStreetNumber("745");
        addressGeorge.setZipCode("25874");

        // create Patient george
        PatientDto george = new PatientDto();
        george.setAddress(addressGeorge);
        george.setContact(contactGeorge);
        george.setAppointments(null);

        List<PatientDto> patients = List.of(
                james,
                george
        );
        patientService.createAll(patients);
        // select * from patient;


        /** AppointmentStatus initialization  */
        AppointmentStatusDto booked = new AppointmentStatusDto();
        booked.setState(AppointmentState.BOOKED);

        AppointmentStatusDto started = new AppointmentStatusDto();
        started.setState(AppointmentState.STARTED);

        AppointmentStatusDto finished = new AppointmentStatusDto();
        finished.setState(AppointmentState.FINISHED);

        AppointmentStatusDto postponed = new AppointmentStatusDto();
        postponed.setState(AppointmentState.POSTPONED);

        AppointmentStatusDto attended = new AppointmentStatusDto();
        attended.setState(AppointmentState.ATTENDED);

        AppointmentStatusDto remoted = new AppointmentStatusDto();
        remoted.setState(AppointmentState.REMOTED);

        AppointmentStatusDto available = new AppointmentStatusDto();
        available.setState(AppointmentState.AVAILABLE);

        List<AppointmentStatusDto> appointmentStatuses = List.of(
                booked,
                started,
                finished,
                postponed,
                attended,
                remoted,
                available
        );
        // save apointment statuses
        appointmentStatusService.createAll(appointmentStatuses);
        // select * from appointment_status;

        /** Hospital initialization  */
        // 1 phoenixHospitalCenter
        Address phoenixHospitalCenterAddress = new Address();
        phoenixHospitalCenterAddress.setCity("Craiova");
        phoenixHospitalCenterAddress.setCountry("Romania");
        phoenixHospitalCenterAddress.setStreet("Calea Severinului");
        phoenixHospitalCenterAddress.setStreetNumber("2G");
        phoenixHospitalCenterAddress.setZipCode("21122");

        HospitalDto phoenixHospitalCenter = new HospitalDto();
        phoenixHospitalCenter.setName("Phoenix Hospital Center");
        phoenixHospitalCenter.setStartDate(new Date());
        phoenixHospitalCenter.setAddress(phoenixHospitalCenterAddress);
        phoenixHospitalCenter.setEndDate(null);
        phoenixHospitalCenter.setDoctors(null);

        // 2 riversideMethodistHospital
        Address riversideMethodistHospitalAddress = new Address();
        riversideMethodistHospitalAddress.setCity("Ohio");
        riversideMethodistHospitalAddress.setCountry("USA");
        riversideMethodistHospitalAddress.setStreet("Columbus, OH");
        riversideMethodistHospitalAddress.setStreetNumber("56");
        riversideMethodistHospitalAddress.setZipCode("61456");

        HospitalDto riversideMethodistHospital = new HospitalDto();
        riversideMethodistHospital.setName("Health Riverside Methodist Hospital");
        riversideMethodistHospital.setStartDate(new Date());
        riversideMethodistHospital.setAddress(riversideMethodistHospitalAddress);
        riversideMethodistHospital.setEndDate(null);
        riversideMethodistHospital.setDoctors(null);

        List<HospitalDto> hospitals = List.of(
                phoenixHospitalCenter,
                riversideMethodistHospital
        );
        // save hospitals
        hospitalService.createAll(hospitals);
        // select * from hospital;


        //--------------------------------------------------------------------------------------//
        /** Doctor initialization
         * doctorWilliam
         * doctorAbbas
         */
        // create contact
        Contact contactWilliam = new Contact();
        contactWilliam.setEmail("w@a.com");
        contactWilliam.setFirstName("William");
        contactWilliam.setLastName("Abdu");
        contactWilliam.setPhone("6523104578");
        // create contact
        Contact contactAbbas = new Contact();
        contactAbbas.setEmail("f@a.com");
        contactAbbas.setFirstName("Fouad");
        contactAbbas.setLastName("Abbas");
        contactAbbas.setPhone("7410215421");

        DoctorDto doctorWilliam = new DoctorDto();
        doctorWilliam.setContact(contactWilliam);
        doctorWilliam.setOffices(null);

        DoctorDto doctorAbbas = new DoctorDto();
        doctorAbbas.setContact(contactAbbas);
        doctorAbbas.setOffices(null);

        hospitals = hospitalService.getAll();

        // associate doctors with hospitals
        // doctorWilliam -> phoenixHospital
        HospitalDto phoenixHospitalDto = hospitals.stream().filter(h->h.getName().contains("Phoenix")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Hospital");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        doctorWilliam.setHospital(phoenixHospitalDto);
        HospitalDto riversideHospitalDto = hospitals.stream().filter(h->h.getName().contains("Riverside")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Hospital");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        // doctorAbbas -> riversideHospital
        doctorAbbas.setHospital(riversideHospitalDto);

        // save doctors
        List<DoctorDto> doctors = List.of( doctorWilliam, doctorAbbas  );
        doctorService.createAll(doctors);
        // select * from doctor;



        //--------------------------------------------------------------------------------------//
        /** Office initialization  */
         // retrieve from db doctors
        doctors = doctorService.getAll();
        doctorWilliam = doctors.stream().filter(d->d.getContact().getEmail().equalsIgnoreCase("w@a.com")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Hospital");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        doctorAbbas = doctors.stream().filter(d->d.getContact().getEmail().equalsIgnoreCase("f@a.com")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Hospital");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });

        // associate them with hospital offices
        Address officeAddress1 = new Address();
        officeAddress1.setCity("New York");
        officeAddress1.setCountry("USA");
        officeAddress1.setStreet("Broadway");
        officeAddress1.setStreetNumber("14");
        officeAddress1.setZipCode("25874");

        OfficeDto office1 = new OfficeDto();
        office1.setAppointments(null);
        office1.setAddress(officeAddress1);
        office1.setDoctor(doctorWilliam);

        office1.setHospital(phoenixHospitalDto);
        office1.setTimeSlotPerClientInMinutes(30);
        office1.setOfficeDoctorAvailabilities(null);

        Address officeAddress2 = new Address();
        officeAddress2.setCity("Craiova");
        officeAddress2.setCountry("Romania");
        officeAddress2.setStreet("Chiristigiilor");
        officeAddress2.setStreetNumber("254");
        officeAddress2.setZipCode("85471");

        OfficeDto office2 = new OfficeDto();
        office2.setAppointments(null);
        office2.setAddress(officeAddress2);
        office2.setDoctor(doctorAbbas);

        office2.setHospital(riversideHospitalDto);
        office2.setTimeSlotPerClientInMinutes(40);
        office2.setOfficeDoctorAvailabilities(null);

        List<OfficeDto> offices = List.of( office1, office2  );
        officeService.createAll(offices);
        // select * from office;


        //--------------------------------------------------------------------------------------//
        /** Office Doctor Availability initialization  */
        offices = officeService.getAll();
        LocalDateTime dt1 = LocalDateTime.now().plusDays(4);
        OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto1 = new OfficeDoctorAvailabilityDto();
        officeDoctorAvailabilityDto1.setAvailability(Boolean.FALSE);
        officeDoctorAvailabilityDto1.setOffice(offices.get(0));
        officeDoctorAvailabilityDto1.setAvailabilityDate(DateUtils.localDateTimeToDate(dt1));
        officeDoctorAvailabilityDto1.setUnAvailabilityReason(ALREADY_BOOKED);
        officeDoctorAvailabilityDto1.setStartTime(DateUtils.localDateTimeToDate(dt1));
        Date end1 = DateUtils.localDateTimeToDate(dt1.plusMinutes(offices.get(0).getTimeSlotPerClientInMinutes()));
        officeDoctorAvailabilityDto1.setEndTime(end1);

        LocalDateTime dt2 = LocalDateTime.now().plusDays(3);
        OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto2 = new OfficeDoctorAvailabilityDto();
        officeDoctorAvailabilityDto2.setAvailability(Boolean.FALSE);
        officeDoctorAvailabilityDto2.setOffice(offices.get(1));
        officeDoctorAvailabilityDto2.setAvailabilityDate(DateUtils.localDateTimeToDate(dt2));
        officeDoctorAvailabilityDto2.setUnAvailabilityReason(ALREADY_BOOKED);
        officeDoctorAvailabilityDto2.setStartTime(DateUtils.localDateTimeToDate(dt2));
        Date end2 = DateUtils.localDateTimeToDate(dt2.plusMinutes(offices.get(1).getTimeSlotPerClientInMinutes()));
        officeDoctorAvailabilityDto2.setEndTime(end2);

        OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto3 = new OfficeDoctorAvailabilityDto();
        officeDoctorAvailabilityDto3.setOffice(offices.get(0));
        LocalDateTime ld3 = dt2.plusHours(2);
        officeDoctorAvailabilityDto3.setAvailability(Boolean.TRUE);
        officeDoctorAvailabilityDto3.setAvailabilityDate(DateUtils.localDateTimeToDate(ld3));
        officeDoctorAvailabilityDto3.setStartTime(DateUtils.localDateTimeToDate(ld3));
        Date end3 = DateUtils.localDateTimeToDate(ld3.plusMinutes(offices.get(0).getTimeSlotPerClientInMinutes()));
        officeDoctorAvailabilityDto3.setEndTime(end3);

        OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto4 = new OfficeDoctorAvailabilityDto();
        officeDoctorAvailabilityDto4.setOffice(offices.get(0));
        officeDoctorAvailabilityDto4.setAvailability(Boolean.TRUE);
        LocalDateTime ld4 = ld3.plusDays(3);
        officeDoctorAvailabilityDto4.setAvailabilityDate(DateUtils.localDateTimeToDate(ld4));
        officeDoctorAvailabilityDto4.setStartTime(DateUtils.localDateTimeToDate(ld4));
        Date end4 = DateUtils.localDateTimeToDate(ld4.plusMinutes(offices.get(0).getTimeSlotPerClientInMinutes()));
        officeDoctorAvailabilityDto4.setEndTime(end4);

        OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto5 = new OfficeDoctorAvailabilityDto();
        officeDoctorAvailabilityDto5.setOffice(offices.get(1));
        officeDoctorAvailabilityDto5.setAvailability(Boolean.TRUE);
        LocalDateTime ld5 = ld4.plusDays(2);
        officeDoctorAvailabilityDto5.setAvailabilityDate(DateUtils.localDateTimeToDate(ld5));
        officeDoctorAvailabilityDto5.setStartTime(DateUtils.localDateTimeToDate(ld5));
        Date end5 = DateUtils.localDateTimeToDate(ld5.plusMinutes(offices.get(1).getTimeSlotPerClientInMinutes()));
        officeDoctorAvailabilityDto5.setEndTime(end5);

        List<OfficeDoctorAvailabilityDto> officeDoctorAvailabilities = List.of(
                officeDoctorAvailabilityDto1,
                officeDoctorAvailabilityDto2,
                officeDoctorAvailabilityDto3,
                officeDoctorAvailabilityDto4,
                officeDoctorAvailabilityDto5
        );
        // save office and doctor availabilities
        officeDoctorAvailabilityService.createAll(officeDoctorAvailabilities);
        // select * from doctor_office_availability;


        // book an appointment
        appointmentStatuses = appointmentStatusService.getAll();
        patients = patientService.getAll();
        AppointmentStatusDto appointmentStatusDto = appointmentStatuses.stream().filter(s->s.getState().equals(AppointmentState.BOOKED)).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("AppointmentStatus");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        //James a@b.com    George       g@b.com
        PatientDto jamesPatient = patients.stream().filter(p->p.getContact().getEmail().equals("a@b.com")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Patient");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        PatientDto georgePatient = patients.stream().filter(p->p.getContact().getEmail().equals("g@b.com")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Patient");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        AppointmentDto appointment1 = new AppointmentDto();
        appointment1.setAppointmentStatus(appointmentStatusDto);
        appointment1.setOffice(offices.get(0));
        appointment1.setActualStartTime(DateUtils.localDateTimeToDate(dt1));
        appointment1.setActualEndTime(DateUtils.localDateTimeToDate(DateUtils.dateToLocalDateTimePlusMinutes(end1, 3 + offices.get(0).getTimeSlotPerClientInMinutes())) );
        appointment1.setPatient(jamesPatient);
        appointment1.setBookedDate(DateUtils.localDateTimeToDate(dt1));

        AppointmentDto appointment2 = new AppointmentDto();
        appointment2.setAppointmentStatus(appointmentStatusDto);
        appointment2.setOffice(offices.get(1));
        appointment2.setActualStartTime(DateUtils.localDateTimeToDate(dt2));
        appointment2.setActualEndTime(DateUtils.localDateTimeToDate(DateUtils.dateToLocalDateTimePlusMinutes(end1, 3 + offices.get(0).getTimeSlotPerClientInMinutes())) );
        appointment2.setPatient(georgePatient);
        appointment2.setBookedDate(DateUtils.localDateTimeToDate(dt2));

        List<AppointmentDto> appointments = List.of( appointment1, appointment2  );
        appointmentService.createAll(appointments);
        // select * from appointment;


        //--------------------------------------------------------------------------------------//
        /** DoctorSpecialization initialization  */
        specializations = specializationService.getAll();

        endocrinologist = specializations.stream().filter(s->s.getSpecializationName().equals("Endocrinologist")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Specialization");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        DoctorSpecializationDto doctorWilliamSpecializationDto = new DoctorSpecializationDto();
        doctorWilliamSpecializationDto.setDoctor(doctorWilliam);
        doctorWilliamSpecializationDto.setSpecialization(endocrinologist);
        doctorWilliamSpecializationDto.setRating(5);

        cardiologist = specializations.stream().filter(s->s.getSpecializationName().equals("Cardiologist")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Specialization");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        DoctorSpecializationDto doctorAbbasSpecializationDto = new DoctorSpecializationDto();
        doctorAbbasSpecializationDto.setDoctor(doctorAbbas);
        doctorAbbasSpecializationDto.setSpecialization(cardiologist);
        doctorAbbasSpecializationDto.setRating(9);

        dermatologist = specializations.stream().filter(s->s.getSpecializationName().equals("Dermatologist")).findFirst().orElseThrow( () -> {
            String message = AppUtil.buildMessage("Specialization");
            log.error("Error message: {}", message);
            throw new ResourceNotFoundException(message);
        });
        DoctorSpecializationDto doctorAbbasDermatologistSpecializationDto = new DoctorSpecializationDto();
        doctorAbbasDermatologistSpecializationDto.setDoctor(doctorAbbas);
        doctorAbbasDermatologistSpecializationDto.setSpecialization(dermatologist);
        doctorAbbasDermatologistSpecializationDto.setRating(7);

        List<DoctorSpecializationDto> doctorSpecializations = List.of(
                doctorWilliamSpecializationDto,
                doctorAbbasSpecializationDto,
                doctorAbbasDermatologistSpecializationDto
        );
        // save specializations
        doctorSpecializationService.createAll(doctorSpecializations);
        // select * from doctor_specialization;

        /** Seeing A DoctorDto initialization -  */
        SeeingADoctorDto seeingADoctorWilliam = new SeeingADoctorDto();
        seeingADoctorWilliam.setDoctor(doctorWilliam);
        seeingADoctorWilliam.setPatient(jamesPatient);
        seeingADoctorWilliam.setBehaviourMannerRate(7);
        seeingADoctorWilliam.setRecommended(Boolean.TRUE);
        seeingADoctorWilliam.setReview("This is a good doctor. It was kind.");
        seeingADoctorWilliam.setWaitTimeRate(2);
        seeingADoctorWilliam.setReviewDate(new Date());

        SeeingADoctorDto seeingADoctorAbbas = new SeeingADoctorDto();
        seeingADoctorAbbas.setDoctor(doctorAbbas);
        seeingADoctorAbbas.setPatient(georgePatient);
        seeingADoctorAbbas.setBehaviourMannerRate(10);
        seeingADoctorAbbas.setRecommended(Boolean.TRUE);
        seeingADoctorAbbas.setReview("This is the best doctor I ever met. It was kind to me.");
        seeingADoctorAbbas.setWaitTimeRate(1);
        seeingADoctorAbbas.setReviewDate(new Date());

        List<SeeingADoctorDto> seeingADoctors = List.of( seeingADoctorWilliam, seeingADoctorAbbas  );
        seeingADoctorService.createAll(seeingADoctors);
        // select * from seeing_a_doctor;

        log.info("* ------------------------------------------------------------------------------------------- *");
        log.info("Database is fully instantiated and ready to be used by the application!");
        log.info("* ------------------------------------------------------------------------------------------- *");

        /**
         * Please, run these queries in the database only after the application is up and running.
         * -------------------------------------------------------------------------------------------
         select * from appointment;
         select * from appointment_status;
         select * from doctor;
         select * from doctor_office_availability;
         select * from doctor_specialization;
         select * from hospital;
         select * from office;
         select * from patient;
         select * from specialization;
         select * from seeing_a_doctor;
         */


    }
}
