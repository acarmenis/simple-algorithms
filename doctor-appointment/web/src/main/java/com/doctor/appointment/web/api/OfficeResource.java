package com.doctor.appointment.web.api;

import com.doctor.appointment.domain.dtos.OfficeDto;
import com.doctor.appointment.service.impls.OfficeService;
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
@Api("OfficeResource Crud Endpoints")
@Slf4j
@RestController
@RequestMapping(OfficeResource.ENDPOINT)
public class OfficeResource {

    public static final String ENDPOINT = "/backend/api/v1/offices";

    private final OfficeService officeService;

    @Autowired
    public OfficeResource(OfficeService officeService) {
        this.officeService = officeService;
    }

    @ApiOperation(nickname = "getDoctorById", value = "Retrieves an Office by his id")
    @GetMapping(value = "/office/{id}")
    public ResponseEntity<OfficeDto> getOne(@PathVariable Long id){
        return ResponseEntity.ok(officeService.getOne(id));
    }

    @ApiOperation(nickname = "getOffices", value = "Retrieves all Offices")
    @GetMapping(value = "/all")
    public ResponseEntity<List<OfficeDto>> getAll(){
        return ResponseEntity.ok(officeService.getAll());
    }

    @ApiOperation(nickname = "addOffice", value = "Adds a Office")
    @PostMapping(value = "/add/one")
    public ResponseEntity<OfficeDto> addOne(@RequestBody OfficeDto officeDto){
        return ResponseEntity.ok(officeService.createOne(officeDto));
    }

}
