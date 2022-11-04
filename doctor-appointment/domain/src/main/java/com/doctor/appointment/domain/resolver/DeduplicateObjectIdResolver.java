package com.doctor.appointment.domain.resolver;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;

import java.util.HashMap;

/**
 * @author Andreas Karmenis on 11/2/2022
 * @project doctor-booking-appointment
 */

/**
 * A resolver class that overrides the SimpleObjectIdResolver functionality
 * in order to solve duplicate object errors during DTO Serializing
 */
public class DeduplicateObjectIdResolver extends SimpleObjectIdResolver {
    @Override
    public void bindItem(ObjectIdGenerator.IdKey id, Object ob) {
        if (_items == null) {
            _items = new HashMap<>();
        }
        _items.put(id, ob);
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object context) {
        return new DeduplicateObjectIdResolver();
    }
}
