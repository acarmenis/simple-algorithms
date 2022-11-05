package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.AddAppointmentRequest;
import com.doctor.appointment.domain.dtos.Availability;
import com.doctor.appointment.service.impls.AppointmentService;
import com.doctor.appointment.service.impls.OfficeDoctorAvailabilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-appointment
 */
@Api("DashBoardController Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(DashBoardController.ENDPOINT)
public class DashBoardController {

    public static final String ENDPOINT = "/backend/api/v1/booking";

    private final OfficeDoctorAvailabilityService officeDoctorAvailabilityService;
    private final AppointmentService appointmentService;

    @Autowired
    public DashBoardController(OfficeDoctorAvailabilityService officeDoctorAvailabilityService,
                               AppointmentService appointmentService) {
        this.officeDoctorAvailabilityService = officeDoctorAvailabilityService;
        this.appointmentService = appointmentService;
    }

    @ApiOperation(nickname = "getAvailableAppointments", value = "Retrieves Available Appointments for a hospital, specialization")
    @GetMapping(value = {"/{specialization}/{hospital}", "/{enabled}/{specialization}/{hospital}" })
    public ResponseEntity<List<Availability>> availableAppointmentsForSpecialization(
            @PathVariable Optional<Boolean> enabled,
            @PathVariable String specialization,
            @PathVariable String hospital){
        return ResponseEntity.ok(officeDoctorAvailabilityService.getAvailabilitiesOfADoctor(Boolean.TRUE, specialization, hospital));
    }

    @ApiOperation(nickname = "bookAnAppointment", value = "Booking an available appointment for enrolled patient, a hospital and for a specialization")
    @PostMapping(value = {"/book-appointment" })
    public ResponseEntity<?> bookingAppointment(@RequestBody AddAppointmentRequest addAppointmentRequest ){
        return ResponseEntity.ok(appointmentService.bookAppointmentFor(addAppointmentRequest));
    }

}
