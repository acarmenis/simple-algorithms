package com.zinkworks.dispense.logic;

import com.zinkworks.domain.dto.BankAccountWrapper;
import com.zinkworks.domain.dto.FiveNoteTransaction;
import lombok.extern.slf4j.Slf4j;

/**
 * FiveEurosBankNote implements the IDispense
 * does the actual dispenses of a 5 note
 */
@Slf4j
public class FiveEurosBankNote implements IDispense {

    // constant, represents the banknote of 5 euros
    private static final int FIVE = 5;

    // next routing of the next chain in the calls
    private IDispense dispense;

    /**
     *  The sets the next chain responsibility
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
        //filters for amounts greater or equal to five euros and the atm machine to have any such banknote(s)
        if((bankAccountWrapper.getMoney().getAmount() >= FIVE)&&(bankAccountWrapper.getAtmDTO().getFive() > 0)){
            // initiates the FiveNoteTransaction with the bankAccountWrapper
            FiveNoteTransaction fiveNoteTransaction = new FiveNoteTransaction(bankAccountWrapper);
            // does the necessary deductions
            fiveNoteTransaction.doComputations();
            // after doing the above computations/deductions from/to resets the bankAccountWrapper with the latest changes
            bankAccountWrapper = fiveNoteTransaction.getBankAccountWrapper();
            // if the transactions is a real one
            if(!bankAccountWrapper.isSimulation()) {
                // logs the number of dispensed notes
                log.info("\t\t{} ", fiveNoteTransaction.message());
            }
            // if Money has still any reminder, it recreates the new amount to be in the next chain of dispenses
            if (fiveNoteTransaction.hasRemainder()) {
                // instructs for the next dispense in the chain
                this.dispense.doDispense(bankAccountWrapper);
            }
        } else {
            // if less than the FIVE euros or/and the atm machine has run out of such banknotes
            this.dispense.doDispense(bankAccountWrapper);
        }
        // returns the modified amounts/balances
        return bankAccountWrapper;
    }
}
