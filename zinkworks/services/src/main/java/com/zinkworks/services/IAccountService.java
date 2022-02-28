package com.zinkworks.services;

import com.zinkworks.domain.Account;

import java.util.List;
import java.util.Optional;

/**
 * IAccountService exposes the methods used for interaction with the Account repository at the persistence layer.
 * Implemented by AccountService
 */
public interface IAccountService {
    // gets one account through the id
    Account findOne(long id);
    // gets all accounts
    List<Account> findAll();
    // save the Account entity
    Account save(Account account);
    // updates the Account entity
    Account update(Account account);
    // gets one account through the pin Number
    Optional<Account> findAccountByPin(String pinNumber);
}
