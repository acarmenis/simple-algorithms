package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;
import com.doctor.appointment.service.impls.DoctorSpecializationService;
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
@Api("DoctorSpecializationResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(DoctorSpecializationResource.ENDPOINT)
public class DoctorSpecializationResource {

    public static final String ENDPOINT = "/backend/api/v1/doctor-specialization";

    private final DoctorSpecializationService doctorSpecializationService;

    @Autowired
    public DoctorSpecializationResource(DoctorSpecializationService doctorSpecializationService) {
        this.doctorSpecializationService = doctorSpecializationService;
    }

    @ApiOperation(nickname = "getDoctorSpecializationById", value = "Retrieves a DoctorSpecialization by his id")
    @GetMapping(value = "/doctor-specialization/{id}")
    public ResponseEntity<DoctorSpecializationDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(doctorSpecializationService.getOne(id));
    }

    @ApiOperation(nickname = "getDoctorSpecializations", value = "Retrieves all DoctorSpecializations")
    @GetMapping(value = "/all")
    public ResponseEntity<List<DoctorSpecializationDto>> getAll(){
        return ResponseEntity.ok(doctorSpecializationService.getAll());
    }

    @ApiOperation(nickname = "addDoctorSpecialization", value = "Adds a DoctorSpecialization")
    @PostMapping(value = "/add/one")
    public ResponseEntity<DoctorSpecializationDto> addOne(@RequestBody DoctorSpecializationDto doctorSpecializationDto){
        return ResponseEntity.ok(doctorSpecializationService.createOne(doctorSpecializationDto));
    }

}
