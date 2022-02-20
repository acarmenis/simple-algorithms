package com.zinkworks.states.impls;

import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.domain.dto.TransactionType;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;
import com.zinkworks.states.IState;
import lombok.extern.slf4j.Slf4j;

/**
 * EnterCashCardState, part of the application states sequences.
 * Represents the Enter Cash Card State in the slot of the atm.
 * Not much to do at this stage
 */
@Slf4j
public class EnterCashCardState implements IState {
    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) {
        // change a line since it is the very first state/step of the transaction
        log.info("\n");
        // logs to console the transaction type and numbers the step/state
        log.info("1. {}", TransactionType.CARD_ENTER_AT_THE_ATM.getMessage());
    }
}
