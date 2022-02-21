package com.zinkworks.states.impls;

import com.zinkworks.app.utils.AppUtils;
import com.zinkworks.domain.Account;
import com.zinkworks.domain.Atm;
import com.zinkworks.domain.dto.*;
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
 * DispenseState, part of the application states sequences.
 * Represents the dispense state.
 * Does several checks before proceeding to the next state of dispensing
 * Also, here it is when the real transaction/despense and user account amounts are charged/deducted.
 * The atm amounts also, gets undergone to several deductions.
 */
@Slf4j
public class DispenseState implements IState {

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
    public void getAction(AtmClientRequest atmClientRequest) { }

    /**
     *
     * @param atmClientRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     */
    @Override
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService) {  }

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
    public void getAction(AtmClientRequest atmClientRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) {
        // logs to console the transaction type and numbers the step/state
        log.info("4. {}", TransactionType.DISPENSE.getMessage());
        // retrieves from the H2 database the atm.
        Atm atm = atmService.findOne(1L);

        // converts the stringified money to integer number/does no need for additional checks at this point
        // since it has gone through it twice before getting here.
        int requestedMoney = AppUtils.convertFromStringToInt(atmClientRequest.getMoney());
        // logs if the client's requested money is 0 or negative amount
        ErrorLog.logTheError((atm.getAtmAmount()<=0), ErrorType.ZERO_ACCOUNT_BALANCE, HttpStatus.BAD_REQUEST);
        // logs if the client's requested money exceeds the user's account balance.
        ErrorLog.logTheError((requestedMoney>atm.getAtmAmount()), ErrorType.REQUESTED_MONEY_EXCEEDS_YOUR_ACCOUNT_BALANCE, HttpStatus.BAD_REQUEST);
        // retrieves from the H2 database the Account based on the provided pin number.
        Optional<Account> optionalAccount = accountService.findAccountByPin(atmClientRequest.getPin());
        // logs in case the Account cannot be retrieved form database
        ErrorLog.logTheError(optionalAccount.isEmpty(), ErrorType.PIN_MISMATCHES, HttpStatus.BAD_REQUEST);
        // logs for debugging/info purposes the Account
        log.info("\t\twas successfully, retrieved user's account from database: {}", optionalAccount.isPresent());
        // logs in case the atm cannot serve money
        ErrorLog.logTheError((atm.getAtmAmount()<requestedMoney), ErrorType.ATM_CANNOT_SERVE_THE_REQUESTED_AMOUNT, HttpStatus.BAD_REQUEST);
        // extracts the account if it has one gotten
        Account userBankAccount = optionalAccount.get();
        // logs the bank account user balance before deduction
        log.info("Bank Account Balance Before Deduction {}€.", userBankAccount.getBalance());
        // logs the atm amounts before despensing/deduction
        log.info("ATM Balance Before Deduction {}€, 50€->{}, 20€->{}, 10€->{}, 5€->{}", atm.getAtmAmount(), atm.getFifty(), atm.getTwenty(), atm.getTen(), atm.getFive());
        // sets an account dto from the account entity as wrapper with the account, atm , money and if the transaction is a simulation or not
        // please, note that the simulations, is used just for not logging messages in the console.
        // its actual purpose was to real test the amount deductions before doing the real transaction, but currently is not fully implemented/used
        // the false value paramater, indicates that it is not going to simulate the transaction and it is the real one.
        BankAccountWrapper bankAccountWrapper = new BankAccountWrapper(new AtmDTO(atm), new AccountDTO(userBankAccount), new Money(requestedMoney), false);
        // sets up the atm chain responsibility - design pattern for the note dispensing and return the account/atm/mone wrapper with balance updates
        // it replace bankAccountWrapper with any change on its contents
        bankAccountWrapper = atmDispenser.startDispensing(bankAccountWrapper);

        // sets to the actual account entity with the new modified account dto
        userBankAccount.setBalance(bankAccountWrapper.getAccountDTO().getBalance());
        // stores/assigns the modified entity account in the database and re-return it from the database
        userBankAccount = accountService.update(userBankAccount);

        // sets to the actual atm entity with the new modified account dto
        atm = bankAccountWrapper.getAtmDTO().toAtm(bankAccountWrapper.getAtmDTO());
        // stores/assigns the modified entity atm in the database and re-return it from the database
        atm = atmService.update(atm);

        // logs Account after the real deductions
        log.info("Bank Account Balance After Deduction {}€.", userBankAccount.getBalance());
        // logs ATM after the real deductions
        log.info("ATM Balance After Deduction {}€, 50€->{}, 20€->{}, 10€->{}, 5€->{}", atm.getAtmAmount(), atm.getFifty(), atm.getTwenty(), atm.getTen(), atm.getFive());
    }
}
