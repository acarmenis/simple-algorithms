package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.OfficeDoctorAvailabilityDto;
import com.doctor.appointment.service.impls.OfficeDoctorAvailabilityService;
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
@Api("OfficeDoctorAvailabilityResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(OfficeDoctorAvailabilityResource.ENDPOINT)
public class OfficeDoctorAvailabilityResource {

    public static final String ENDPOINT = "/backend/api/v1/office-doctor-availabilities";

    private final OfficeDoctorAvailabilityService officeDoctorAvailabilityService;

    @Autowired
    public OfficeDoctorAvailabilityResource(OfficeDoctorAvailabilityService officeDoctorAvailabilityService) {
        this.officeDoctorAvailabilityService = officeDoctorAvailabilityService;
    }

    @ApiOperation(nickname = "getOfficeDoctorAvailabilityById", value = "Retrieves a OfficeDoctorAvailability by his id")
    @GetMapping(value = "/office-doctor-availability/{id}")
    public ResponseEntity<OfficeDoctorAvailabilityDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(officeDoctorAvailabilityService.getOne(id));
    }

    @ApiOperation(nickname = "getOfficeDoctorAvailabilities", value = "Retrieves all OfficeDoctorAvailabilities")
    @GetMapping(value = "/all")
    public ResponseEntity<List<OfficeDoctorAvailabilityDto>> getAll(){
        return ResponseEntity.ok(officeDoctorAvailabilityService.getAll());
    }

    @ApiOperation(nickname = "addOfficeDoctorAvailability", value = "Adds an OfficeDoctorAvailability")
    @PostMapping(value = "/add/one")
    public ResponseEntity<OfficeDoctorAvailabilityDto> addOne(@RequestBody OfficeDoctorAvailabilityDto officeDoctorAvailabilityDto){
        return ResponseEntity.ok(officeDoctorAvailabilityService.createOne(officeDoctorAvailabilityDto));
    }

}
