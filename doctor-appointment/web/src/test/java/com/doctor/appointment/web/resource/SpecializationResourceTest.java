package com.doctor.appointment.web.resource;

import com.doctor.appointment.domain.dtos.SpecializationDto;
import com.doctor.appointment.service.impls.SpecializationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-appointment
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpecializationService.class })
public class SpecializationResourceTest {

    @SpyBean // we can decide which methods to mock
    private SpecializationService specializationService;


    @Before
    public void init(){

        SpecializationDto endocrinologist = new SpecializationDto();
        endocrinologist.setSpecializationName("Endocrinologist");

        SpecializationDto allergist = new SpecializationDto();
        allergist.setSpecializationName("Allergist");

        SpecializationDto immunologist = new SpecializationDto();
        immunologist.setSpecializationName("Immunologist");

        SpecializationDto cardiologist = new SpecializationDto();
        cardiologist.setSpecializationName("Cardiologist");

        SpecializationDto dermatologist = new SpecializationDto();
        dermatologist.setSpecializationName("Dermatologist");

        List<SpecializationDto> specializations = List.of(
                endocrinologist,
                allergist,
                immunologist,
                cardiologist,
                dermatologist
        );

        specializationService.createAll(specializations);
    }

    @Test
    public void testAssertThatEqual() {
        assertEquals(2, specializationService.getAll().size());
    }
}