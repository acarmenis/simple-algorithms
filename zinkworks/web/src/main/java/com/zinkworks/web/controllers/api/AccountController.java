package com.zinkworks.web.controllers.api;

import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;
import com.zinkworks.states.IState;
import com.zinkworks.states.context.TransactionTypeContext;
import com.zinkworks.states.impls.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * AccountController, is a rest controller for the api.
 * It accepts the client requests and serves the responses.
 */
@Slf4j
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AccountController {

    // IAccountService service for account entities database transactions
    private final IAccountService accountService;
    // IAtmService service for atm entities database transactions
    private final IAtmService atmService;
    // ATMDispenser service implements the chain responsibility design pattern
    // used for atm dispensing
    private final ATMDispenser atmDispenser;

    // AccountController construction via services autowiring
    @Autowired
    public AccountController(IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) {
        this.accountService = accountService;
        this.atmService = atmService;
        this.atmDispenser = atmDispenser;
    }

    // api's end point
    @PostMapping(value = "/atm/client/request")
    public ResponseEntity<?> enterPin(@Valid @RequestBody AtmClientRequest atmRequest){

        // is the context for setting up the state design pattern of the atm sequence of states.
        TransactionTypeContext context = new TransactionTypeContext();

        // initializing the states variations
        IState enterCashCardState = new EnterCashCardState();
        IState enterPinState = new EnterPinState();
        IState withdrawalState = new WithdrawalState();
        IState dispenseState = new DispenseState();
        IState cashCardExportState = new CashCardExportState();

        // set stage with step status 1
        context.setTransactionState(enterCashCardState);
        context.getAction(atmRequest, null, null, null);

        // set stage with step status 2
        context.setTransactionState(enterPinState);
        context.getAction(atmRequest, accountService, atmService, null);

        // set stage with step status 3
        context.setTransactionState(withdrawalState);
        context.getAction(atmRequest, accountService, atmService, null);

        // set stage with step status 4
        context.setTransactionState(dispenseState);
        context.getAction(atmRequest, accountService, atmService, atmDispenser);

        // set stage with step status 5
        context.setTransactionState(cashCardExportState);
        context.getAction(atmRequest, null, null, null);

        // returns the response code
        return ResponseEntity.ok().build();
    }



}
