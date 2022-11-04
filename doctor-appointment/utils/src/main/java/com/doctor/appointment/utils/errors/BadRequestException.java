package com.doctor.appointment.utils.errors;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */
public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 3L;
    public BadRequestException(String msg) {
        super(msg);
    }
}
