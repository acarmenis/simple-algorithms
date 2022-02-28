package com.zinkworks.services;

import com.zinkworks.domain.Atm;

/**
 * IAtmService exposes the methods used for interaction with the atm repository at the persistence layer.
 * Implemented by AtmService
 */
public interface IAtmService {
    // gets one Atm through the id
    Atm findOne(long id);
    // gets the only Atm without the id
    Atm getAtm();
    // save the Atm entity
    Atm save(Atm atm);
    // updates the Atm entity
    Atm update(Atm atm);
}
