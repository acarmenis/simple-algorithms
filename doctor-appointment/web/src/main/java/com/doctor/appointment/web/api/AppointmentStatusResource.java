package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.AppointmentStatusDto;
import com.doctor.appointment.service.impls.AppointmentStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/4/2022
 * @project doctor-booking-appointment
 */
@Api("AppointmentStatusResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(AppointmentStatusResource.ENDPOINT)
public class AppointmentStatusResource {

    public static final String ENDPOINT = "/backend/api/v1/appointment-statuses";

    private final AppointmentStatusService appointmentStatusService;

    @Autowired
    public AppointmentStatusResource(AppointmentStatusService appointmentStatusService) {
        this.appointmentStatusService = appointmentStatusService;
    }

    @ApiOperation(nickname = "getAppointmentStatusById", value = "Retrieves a AppointmentStatus by his id")
    @GetMapping(value = "/appointment-status/{id}")
    public ResponseEntity<AppointmentStatusDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(appointmentStatusService.getOne(id));
    }

    @ApiOperation(nickname = "getAppointmentStatuses", value = "Retrieves all AppointmentStatuses")
    @GetMapping(value = "/all")
    public ResponseEntity<List<AppointmentStatusDto>> getAll(){
        return ResponseEntity.ok(appointmentStatusService.getAll());
    }

    @ApiOperation(nickname = "addAppointmentStatus", value = "Adds a AppointmentStatus")
    @PostMapping(value = "/add/one")
    public ResponseEntity<AppointmentStatusDto> addOne(@RequestBody AppointmentStatusDto appointmentStatusDto){
        return ResponseEntity.ok(appointmentStatusService.createOne(appointmentStatusDto));
    }

}
