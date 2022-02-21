package com.zinkworks.states.impls;

import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.domain.dto.TransactionType;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;
import com.zinkworks.states.IState;
import lombok.extern.slf4j.Slf4j;


/**
 * DepositState, part of the application states sequences.
 * Represents the Deposit State
 * Not much to do at this stage/ not essential used
 */
@Slf4j
public class DepositState implements IState {

    @Override
    public void getAction() {
        // logs to console the transaction type and numbers the step/state
        log.info("{}", TransactionType.DEPOSIT);
    }

    /**
     *
     * @param atmClientRequest atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest) {  }

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService) {

    }

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService) {

    }

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) {}
}