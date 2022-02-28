package com.zinkworks.states;

import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;

/**
 * IState sets the states action
 */
public interface IState {

    /**
     *
     */
    void getAction();

    /**
     *
     * @param atmClientRequest atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     */
    void getAction(AtmClientRequest atmClientRequest);

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     */
    void getAction(AtmClientRequest atmClientRequest, IAccountService accountService);

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     */
    void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService);

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser);
}
