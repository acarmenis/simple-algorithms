package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.DoctorDto;
import com.doctor.appointment.domain.dtos.DoctorSpecializationDto;
import com.doctor.appointment.service.impls.DoctorService;
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
@Api("DoctorResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(DoctorResource.ENDPOINT)
public class DoctorResource {

    public static final String ENDPOINT = "/backend/api/v1/doctors";

    private final DoctorService doctorService;

    @Autowired
    public DoctorResource(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @ApiOperation(nickname = "getDoctorById", value = "Retrieves a Doctor by his id")
    @GetMapping(value = "/doctor/{id}")
    public ResponseEntity<DoctorDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.getOne(id));
    }

    @ApiOperation(nickname = "getDoctors", value = "Retrieves all Doctors")
    @GetMapping(value = "/all")
    public ResponseEntity<List<DoctorDto>> getAll(){
        return ResponseEntity.ok(doctorService.getAll());
    }

    @ApiOperation(nickname = "addDoctor", value = "Adds a Doctor")
    @PostMapping(value = "/add/one")
    public ResponseEntity<DoctorDto> addOne(@RequestBody DoctorDto doctorDto){
        return ResponseEntity.ok(doctorService.createOne(doctorDto));
    }

    @ApiOperation(nickname = "getDoctorAvailabilitiesOfASpecializationAndHospitalName", value = "Retrieves Doctor Availabilities Of A Specialization And Hospital Name")
    @GetMapping(value = {"/doctor/{specialization}/{hospital}", "/doctor/{specialization}/{hospital}/{optionalAvailable}"})
    public ResponseEntity<List<DoctorSpecializationDto>> getDoctorAvailabilities(@PathVariable String specialization,  @PathVariable String hospital ){
        return ResponseEntity.ok(doctorService.doctorSpecializationAndHospital( specialization, hospital));
    }

    @ApiOperation(nickname = "getPagedDoctors", value = "Retrieve a pageable list Doctor  entries.")
    @GetMapping(value = "/all-paged")
    public ResponseEntity<List<DoctorDto>> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "2") Integer pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "true", required = false) Boolean ascending){
        return ResponseEntity.ok(doctorService.getAll(pageNo, pageSize, sortBy, ascending));
    }


}
