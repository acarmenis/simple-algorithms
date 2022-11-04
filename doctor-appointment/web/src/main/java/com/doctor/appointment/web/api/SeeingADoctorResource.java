package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.SeeingADoctorDto;
import com.doctor.appointment.service.impls.SeeingADoctorService;
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
@Api("SeeingADoctorResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(SeeingADoctorResource.ENDPOINT)
public class SeeingADoctorResource {

    public static final String ENDPOINT = "/backend/api/v1/see-doctors";

    private final SeeingADoctorService seeingADoctorService;

    @Autowired
    public SeeingADoctorResource(SeeingADoctorService seeingADoctorService) {
        this.seeingADoctorService = seeingADoctorService;
    }

    @ApiOperation(nickname = "getSeeingADoctorById", value = "Retrieves a SeeingADoctor by his id")
    @GetMapping(value = "/see-doctor/{id}")
    public ResponseEntity<SeeingADoctorDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(seeingADoctorService.getOne(id));
    }

    @ApiOperation(nickname = "getSeeingADoctors", value = "Retrieves all SeeingADoctors")
    @GetMapping(value = "/all")
    public ResponseEntity<List<SeeingADoctorDto>> getAll(){
        return ResponseEntity.ok(seeingADoctorService.getAll());
    }

    @ApiOperation(nickname = "addSeeingADoctor", value = "Adds a SeeingADoctor")
    @PostMapping(value = "/add/one")
    public ResponseEntity<SeeingADoctorDto> addOne(@RequestBody SeeingADoctorDto seeingADoctorDto){
        return ResponseEntity.ok(seeingADoctorService.createOne(seeingADoctorDto));
    }

}
