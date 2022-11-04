package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.AppointmentDto;
import com.doctor.appointment.service.impls.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-booking-appointment
 */
@Api("AppointmentResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(AppointmentResource.ENDPOINT)
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AppointmentResource {

    public static final String ENDPOINT = "/backend/api/v1/appointments";

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentResource(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @ApiOperation(nickname = "getAppointmentById", value = "Retrieves a Appointment by his id")
    @GetMapping(value = "/appointment/{id}")
    public ResponseEntity<AppointmentDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getOne(id));
    }

    @ApiOperation(nickname = "getAppointments", value = "Retrieves all Appointments")
    @GetMapping(value = "/all")
    public ResponseEntity<List<AppointmentDto>> getAll(){
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @ApiOperation(nickname = "addAppointment", value = "Adds a Appointment")
    @PostMapping(value = "/add/one")
    public ResponseEntity<AppointmentDto> addOne(@RequestBody AppointmentDto appointmentDto){
        return ResponseEntity.ok(appointmentService.createOne(appointmentDto));
    }

}
