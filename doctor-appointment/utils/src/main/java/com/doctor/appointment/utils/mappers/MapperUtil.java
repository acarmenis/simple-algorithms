package com.doctor.appointment.utils.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andreas Karmenis on 11/1/2022
 * @project doctor-booking-appointment
 */
public class MapperUtil {

    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public static  <S, T> Set<T> mapSet(Set<S> source, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return source
                .stream()
                .map(element -> mapper.map(element, targetClass))
                .collect(Collectors.toSet());
    }

    public static <S, T> T mapOne(S source, Class<T> targetClass) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPreferNestedProperties(false);
        return mapper.map(source, targetClass);
    }


}
