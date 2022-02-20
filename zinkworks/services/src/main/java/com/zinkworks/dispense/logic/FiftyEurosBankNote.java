package com.zinkworks.dispense.logic;

import com.zinkworks.domain.dto.BankAccountWrapper;
import com.zinkworks.domain.dto.FiftyNoteTransaction;
import lombok.extern.slf4j.Slf4j;

/**
 * FiftyEurosBankNote implements the IDispense
 * does the actual dispenses of a 50 note
 */
@Slf4j
public class FiftyEurosBankNote implements IDispense {

    // constant, represents the banknote of 50 euros
    private static final int FIFTY = 50;

    // next routing of the next chain in the calls
    private IDispense dispense;

    /**
     *
     * @param next IDispense
     */
    @Override
    public void setNext(IDispense next) {
        this.dispense=next;
    }

    /**
     *
     * @param bankAccountWrapper a bank account wrapper
     * @return modified bankAccountWrapper
     */
    @Override
    public BankAccountWrapper doDispense(BankAccountWrapper bankAccountWrapper) {
        //filters for amounts greater or equal to FIFTY euros and the atm machine to have any such banknote(s)
        if((bankAccountWrapper.getMoney().getAmount() >= FIFTY)&&(bankAccountWrapper.getAtmDTO().getFifty() > 0)){
            // initiates the FiftyNoteTransaction with the bankAccountWrapper
            FiftyNoteTransaction transaction = new FiftyNoteTransaction(bankAccountWrapper);
            // does the necessary deductions
            transaction.doComputations();
            // after doing the above computations/deductions from/to resets the bankAccountWrapper with the latest changes
            bankAccountWrapper = transaction.getBankAccountWrapper();
            // if the transactions is a real one
            if(!bankAccountWrapper.isSimulation()) {
                // logs the number of dispensed notes
                log.info("\t\t{} ", transaction.message());
            }
            // if Money has still any reminder, it recreates the new amount to be in the next chain of dispenses
            if (transaction.hasRemainder()) {
                // instructs for the next dispense in the chain
                this.dispense.doDispense(bankAccountWrapper);
            }
        } else {
            // if less than the FIFTY euros or/and the atm machine has run out of such banknotes
            this.dispense.doDispense(bankAccountWrapper);
        }
        // returns the modified amounts/balances
        return bankAccountWrapper;
    }
}
