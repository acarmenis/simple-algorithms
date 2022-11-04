package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.SpecializationDto;
import com.doctor.appointment.service.impls.SpecializationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
@Api("SpecializationResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(SpecializationResource.ENDPOINT)
public class SpecializationResource {

    public static final String ENDPOINT = "/backend/api/v1/specializations";

    private final SpecializationService specializationService;

    @Autowired
    public SpecializationResource(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @ApiOperation(nickname = "getDoctorById", value = "Retrieves a Doctor by his id")
    @GetMapping(value = "/specialization/{id}")
    public ResponseEntity<SpecializationDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(specializationService.getOne(id));
    }

    @ApiOperation(nickname = "getSpecializations", value = "Retrieves all Specializations")
    @GetMapping(value = "/all")
    public ResponseEntity<List<SpecializationDto>> getAll(){
        return ResponseEntity.ok(specializationService.getAll());
    }

    @ApiOperation(nickname = "addSpecialization", value = "Adds a Specialization")
    @PostMapping(value = "/add/one")
    public ResponseEntity<SpecializationDto> addOne(@RequestBody SpecializationDto specializationDto){
        return ResponseEntity.ok(specializationService.createOne(specializationDto));
    }

}
