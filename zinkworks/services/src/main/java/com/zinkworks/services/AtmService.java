package com.zinkworks.services;

import com.zinkworks.domain.Atm;
import com.zinkworks.domain.error.types.ErrorType;
import com.zinkworks.exceptions.BusinessException;
import com.zinkworks.persistence.IAtmRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * AtmService, a service which deals with atm repository and its database transactions
 */
@Slf4j
@Service
public class AtmService implements IAtmService {

    // atmRepository atm repository
    private final IAtmRepository atmRepository;

    /**
     *
     * @param atmRepository autowired through the constructor dependency
     */
    @Autowired
    public AtmService(IAtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    /**
     *
     * @param id used to get the atm entity by its id - since it is the only one entity, its id is always 1L
     * @return
     */
    @Override
    @Transactional
    public Atm findOne(long id) {
        // gets the atm from database
        Optional<Atm> optionalAtm = atmRepository.findById(id);
        // checks if the atm entity exists
        if(optionalAtm.isEmpty()){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ATM_NOT_FOUND, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the atm entity
        return optionalAtm.get();
    }

    /**
     *
     * @return atm, an atm entity
     */
    @Override
    @Transactional
    public Atm getAtm() {
        // retrieves all the atm entities, we know that just one atm entity exists
        List<Atm> atms = atmRepository.findAll();
        // checks if the atm entity exists
        if(CollectionUtils.isEmpty(atms)){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ATMS_NOT_FOUND, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the atm entity
        return atms.get(0);
    }

    /**
     *
     * @param atm atm, an atm entity to be saved
     * @return atm, an saved new atm entity
     */
    @Override
    @Transactional
    public Atm save(Atm atm) {
        // saves the new created atm and retrieves it back
        Atm savedAtm = atmRepository.save(atm);
        // checks if the atm entity exists
        if(savedAtm == null){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ATM_NOT_SAVED, HttpStatus.BAD_REQUEST);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the atm entity
        return savedAtm;
    }

    /**
     *
     * @param atm, an atm entity to be updated
     * @return
     */
    @Override
    @Transactional
    public Atm update(Atm atm) {
        // saves the updated atm and retrieves it back
        Atm updatedAtm = atmRepository.save(atm);
        // checks if the atm entity exists
        if(updatedAtm == null){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ATM_NOT_UPDATED, HttpStatus.BAD_REQUEST);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the atm entity
        return updatedAtm;
    }
}
