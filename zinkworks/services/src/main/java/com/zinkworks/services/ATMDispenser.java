package com.zinkworks.services;

import com.zinkworks.dispense.logic.*;
import com.zinkworks.domain.dto.BankAccountWrapper;
import com.zinkworks.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ATMDispenser {

    private IDispense dispenseOfFifties;

    public ATMDispenser() {
        // initialize the chain
        this.dispenseOfFifties = new FiftyEurosBankNote();
        IDispense dispenseOfTwenties = new TwentyEurosBankNote();
        IDispense dispenseOfTens = new TenEurosBankNote();
        IDispense dispenseOfFives = new FiveEurosBankNote();

        // set the chain of responsibility
        dispenseOfFifties.setNext(dispenseOfTwenties);
        dispenseOfTwenties.setNext(dispenseOfTens);
        dispenseOfTens.setNext(dispenseOfFives);
    }

    public BankAccountWrapper startDispensing(BankAccountWrapper bankAccountWrapper) throws BusinessException {
            // process the request
        BankAccountWrapper transaction = this.dispenseOfFifties.doDispense(bankAccountWrapper);
        return transaction;
    }
}
