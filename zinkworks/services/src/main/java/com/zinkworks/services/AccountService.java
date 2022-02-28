package com.zinkworks.services;

import com.zinkworks.domain.Account;
import com.zinkworks.domain.error.types.ErrorType;
import com.zinkworks.exceptions.BusinessException;
import com.zinkworks.persistence.IAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * AccountService, a service which deals with atm repository and its database transactions
 */
@Slf4j
@Service
public class AccountService implements IAccountService {

    // bankAccountRepo account repository
    private final IAccountRepository bankAccountRepo;

    /**
     *
     * @param bankAccountRepo  autowired through the constructor dependency
     */
    @Autowired
    public AccountService(IAccountRepository bankAccountRepo) {
        this.bankAccountRepo = bankAccountRepo;
    }

    /**
     *
     * @param id athe account id
     * @return account entity
     */
    @Override
    @Transactional
    public Account findOne(long id) {
        // gets an account by its id
        Optional<Account> optionalAccount = bankAccountRepo.findById(id);
        // checks if the account was successfully retrieved
        if(optionalAccount.isEmpty()){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the account entity
        return optionalAccount.get();
    }

    /**
     *
     * @return accounts list
     */
    @Override
    @Transactional
    public List<Account> findAll() {
        // gets all accounts
        List<Account> accounts = bankAccountRepo.findAll();
        // checks if the account collection was successfully retrieved
        if(CollectionUtils.isEmpty(accounts)){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ACCOUNTS_NOT_FOUND, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the accounts
        return accounts;
    }

    /**
     *
     * @param account to be saved
     * @return the saved account
     */
    @Override
    @Transactional
    public Account save(Account account) {
        // saves the new created Account and retrieves it back
        Account savedAccount = bankAccountRepo.save(account);
        // checks if the Account entity exists
        if(savedAccount == null){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ACCOUNTS_NOT_SAVED, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the saved account
        return savedAccount;
    }

    /**
     *
     * @param account to be updated
     * @return the updated account
     */
    @Override
    @Transactional
    public Account update(Account account) {
        // saves the new updated Account and retrieves it back
        Account updatedAccount = save(account);
        // checks if the Account entity exists
        if(updatedAccount == null){
            // if it does not exist, just throws a business exception with a relative error message
            BusinessException businessException = new BusinessException(ErrorType.DATABASE_ACCOUNTS_NOT_UPDATED, HttpStatus.NOT_FOUND);
            // logs in the console with a relative error message
            log.error("Error code: {}, Error message: {}",businessException.getErrorType().getId(), businessException.getErrorType().getMessage());
            // throws exception in order to be caught from the advice controller and to be a such response back to the user
            throw businessException;
        }
        // return the updated account
        return updatedAccount;
    }

    /**
     *
     * @param pinNumber the pin number
     * @return account entity
     */
    @Override
    @Transactional
    public Optional<Account> findAccountByPin(String pinNumber) {
        // filter all possible account by the pin number
        return this.findAll()
                .stream()
                .filter(account -> account.getPin().equals(pinNumber))
                .findFirst();
    }
}
