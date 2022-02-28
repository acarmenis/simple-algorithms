package com.zinkworks.states.impls;

import com.zinkworks.app.utils.AppUtils;
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
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * WithdrawalState, part of the application states sequences.
 * Represents the Withdrawal state.
 * Does several checks before proceeding to the next state of dispensing
 */
@Slf4j
@Component
public class WithdrawalState implements IState {

    /**
     *
     */
    @Override
    public void getAction() { }

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
        log.info("3. {}", TransactionType.WITHDRAWAL.getMessage());
        // logs to console the client's requested money
        log.info("\t\tRequested money: {}€.", atmClientRequest.getMoney());
        // further checks as for the request money validation
        boolean isMoneyValid = ValidationUtil.isValidMoney(atmClientRequest.getMoney());
        // logs the above error if so
        ErrorLog.logTheError((!isMoneyValid), ErrorType.MONEY_BAD_FORMAT, HttpStatus.BAD_REQUEST);
        // converts the stringified money to integer number
        int requestedMoney = AppUtils.convertFromStringToInt(atmClientRequest.getMoney());
        // logs if the client's requested money is 0 or negative amount
        ErrorLog.logTheError((requestedMoney<=0), ErrorType.ZERO_AMOUNT_REQUEST, HttpStatus.BAD_REQUEST);
        // logs if the client's requested money is not a multiple amount of 5
        ErrorLog.logTheError((requestedMoney%5!=0), ErrorType.DISPENSE_AMOUNT_NOT_MULTIPLE_OF, HttpStatus.BAD_REQUEST);
        // retrieves from the H2 database the Account based on the provided pin number.
        Optional<Account> optionalAccount = accountService.findAccountByPin(atmClientRequest.getPin());
        // logs in case the Account cannot be retrieved form database
        ErrorLog.logTheError((optionalAccount.isEmpty()||optionalAccount.get().getBalance()<=0), ErrorType.ZERO_ACCOUNT_BALANCE, HttpStatus.BAD_REQUEST);
        // logs for debugging/info purposes the Account
        log.info("\t\twas successfully, retrieved user's account from database: {}", optionalAccount.isPresent());
        // logs in case client's requested money exceeds his/her balance account
        ErrorLog.logTheError(((optionalAccount.get().getBalance()<requestedMoney)), ErrorType.REQUESTED_MONEY_EXCEEDS_YOUR_ACCOUNT_BALANCE, HttpStatus.BAD_REQUEST);
        // logs in case client's requested money Overdrafts his/her balance account Overdraft restriction with the actual Overdraft amount
        ErrorLog.logExtraInfoError(((optionalAccount.get().getOverDraft()<requestedMoney)), ErrorType.EXCEEDED_ISSUER_LIMIT, HttpStatus.BAD_REQUEST, String.valueOf(optionalAccount.get().getOverDraft()).concat("€."));
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
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) { }
}
