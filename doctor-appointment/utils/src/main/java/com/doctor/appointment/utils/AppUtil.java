package com.doctor.appointment.utils;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
public class AppUtil {

    public static String buildMessage(String entity, Exception e){
        return entity.concat(" ").concat("entities, didn't get retrieved from database").concat(", ").concat(e.getMessage());
    }

    public static String buildMessage(String entity, Long id){
        return entity.concat(" ").concat("entity couldn't get retrieved from database for id: ").concat(String.valueOf(id));
    }

    public static String buildMessage(String entity){
        return entity.concat(" ").concat(", didn't get retrieved from database!");
    }
}
