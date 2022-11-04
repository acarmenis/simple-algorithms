package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.PatientDto;
import com.doctor.appointment.service.impls.PatientService;
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
@Api("PatientResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(PatientResource.ENDPOINT)
public class PatientResource {

    public static final String ENDPOINT = "/backend/api/v1/patients";

    private final PatientService patientService;

    @Autowired
    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    @ApiOperation(nickname = "getDoctorById", value = "Retrieves a Doctor by his id")
    @GetMapping(value = "/patient/{id}")
    public ResponseEntity<PatientDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(patientService.getOne(id));
    }

    @ApiOperation(nickname = "getPatients", value = "Retrieves all Patients")
    @GetMapping(value = "/all")
    public ResponseEntity<List<PatientDto>> getAll(){
        return ResponseEntity.ok(patientService.getAll());
    }

    @ApiOperation(nickname = "addPatient", value = "Adds a Patient")
    @PostMapping(value = "/add/one")
    public ResponseEntity<PatientDto> addOne(@RequestBody PatientDto patientDto){
        return ResponseEntity.ok(patientService.createOne(patientDto));
    }

}
