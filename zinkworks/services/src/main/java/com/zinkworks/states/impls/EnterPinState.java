package com.zinkworks.states.impls;

import com.zinkworks.app.utils.validations.ValidationUtil;
import com.zinkworks.domain.Account;
import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.domain.dto.TransactionType;
import com.zinkworks.domain.error.types.ErrorType;
import com.zinkworks.exceptions.ErrorLog;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;
import com.zinkworks.states.IState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * EnterPinState, part of the application states sequences.
 * Represents the enter of the pin state.
 * Does several related to the pin's checks before proceeding to the next state of dispensing
 */
@Slf4j
public class EnterPinState implements IState {

    /**
     *
     */
    @Override
    public void getAction() {  }

    /**
     *
     * @param atmClientRequest atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest) {}

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService) {
        // logs to console the transaction type and numbers the step/state
        log.info("2. {}", TransactionType.ENTER_PIN_AT_THE_ATM.getMessage());
        // logs to console the client's requested pin
        log.info("\t\tuser's input pin: {}", atmClientRequest.getPin());
        // further checks as for the request pin's validation
        boolean isPinValid = ValidationUtil.isValidPinCode(atmClientRequest.getPin());
        // logs the state of the pin depending on its restrictions
        log.info("\t\tis user's input a valid pin: {}", isPinValid);
        // logs if the client's requested pin is invalid
        ErrorLog.logTheError((!isPinValid), ErrorType.INVALID_PIN, HttpStatus.BAD_REQUEST);
        // retrieves from the H2 database the Account based on the provided pin number.
        Optional<Account> optionalAccount = accountService.findAccountByPin(atmClientRequest.getPin());
        // logs in case the Account cannot be retrieved form database - due to the wrong pin number maybe
        ErrorLog.logTheError(optionalAccount.isEmpty(), ErrorType.PIN_MISMATCHES, HttpStatus.BAD_REQUEST);
        // logs for debugging/info purposes the Account
        log.info("\t\twas successfully, retrieved user's account from database: {}", optionalAccount.isPresent());
    }

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService) { }

    /**
     *
     * @param atmRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    @Override
    public void getAction(AtmClientRequest atmRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) { }

}
