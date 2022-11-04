package com.doctor.appointment.utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Andreas Karmenis on 11/3/2022
 * @project doctor-booking-appointment
 */
@Slf4j
public class DateUtils {

    // 2021.03.24.16.34.26
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    // 2021-03-24T16:44:39.083+08:00
    private static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    // 2021-03-24 16:48:05
    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 2022-09-28T11:42.00
    private static final SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm.ss");

    // 2022-09-28
    private static final SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateTime(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(sdf4.toPattern());
        return dateTimeFormatter.format(LocalDateTime.now());
    }

    public static String getTimestamp(){
        // method 1
        Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // number of milliseconds since January 1, 1970, 00:00:00 GMT
        // method 2 - via Date  ->  Date date = new Date();
        return sdf1.format(timestamp);
    }

    public static Date convertStringToDate(String date){
        Date d = null;
        try {
            d = sdf5.parse(date);
        } catch (ParseException e) {
           log.error("Date Parse Exception: {}", e.getMessage());
        }
        return d;
    }

    public static Date addDays(String date, int days) {
        Date d = null;
        String s = LocalDate.parse(date).plusDays(days).toString();
        try {
            d = sdf5.parse(s);
        } catch (ParseException e) {
            log.error("Date Parse Exception: {}", e.getMessage());
        }
        return d;
    }

    //Convert java.util.Date to java.time.LocalDate
    public static LocalDate dateToLocalDate(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate(); // LocalDate localDate =
    }

    //Convert java.time.LocalDate to java.util.Date
    public static Date localDateToDate(LocalDate  localDate ){
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    // Convert java.time.LocalDateTime to java.util.Date
    public static Date localDateTimeToDate(LocalDateTime localDateTime ){
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    // Convert java.util.LocalDateTime to java.util.Date
    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    // Convert java.util.Date + minutes to java.util.LocalDateTime
    public static LocalDateTime dateToLocalDateTimePlusMinutes(Date date, long minutes){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return ldt.plusMinutes(minutes);
    }








}
