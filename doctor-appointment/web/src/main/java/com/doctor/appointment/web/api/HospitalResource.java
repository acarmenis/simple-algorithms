package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.HospitalDto;
import com.doctor.appointment.service.impls.HospitalService;
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
@Api("HospitalResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(HospitalResource.ENDPOINT)
public class HospitalResource {

    public static final String ENDPOINT = "/backend/api/v1/hospitals";

    private final HospitalService hospitalService;

    @Autowired
    public HospitalResource(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @ApiOperation(nickname = "getHospitalById", value = "Retrieves a Hospital by his id")
    @GetMapping(value = "/hospital/{id}")
    public ResponseEntity<HospitalDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(hospitalService.getOne(id));
    }

    @ApiOperation(nickname = "getHospitals", value = "Retrieves all Hospitals")
    @GetMapping(value = "/all")
    public ResponseEntity<List<HospitalDto>> getAll(){
        return ResponseEntity.ok(hospitalService.getAll());
    }

    @ApiOperation(nickname = "addHospital", value = "Adds a Hospital")
    @PostMapping(value = "/add/one")
    public ResponseEntity<HospitalDto> addOne(@RequestBody HospitalDto hospitalDto){
        return ResponseEntity.ok(hospitalService.createOne(hospitalDto));
    }

}
