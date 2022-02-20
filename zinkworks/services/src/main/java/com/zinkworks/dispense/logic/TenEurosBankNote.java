package com.zinkworks.dispense.logic;

import com.zinkworks.domain.dto.BankAccountWrapper;
import com.zinkworks.domain.dto.TenNoteTransaction;
import lombok.extern.slf4j.Slf4j;

/**
 * TenEurosBankNote implements the IDispense
 * does the actual dispenses of a 10 note
 */
@Slf4j
public class TenEurosBankNote implements IDispense {

    // constant, represents the banknote of 10 euros
    private static final int TEN = 10;

    // next routing of the next chain in the calls
    private IDispense dispense;

    /**
     * he sets the next chain responsibility
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
        //filters for amounts greater or equal to ten euros and the atm machine to have any such banknote(s)
        if((bankAccountWrapper.getMoney().getAmount() >= TEN)&&(bankAccountWrapper.getAtmDTO().getTen() > 0)){
            // initiates the TenNoteTransaction with the bankAccountWrapper
            TenNoteTransaction transaction = new TenNoteTransaction(bankAccountWrapper);
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
            if (transaction.hasReminder()) {
                // instructs for the next dispense in the chain
                this.dispense.doDispense(bankAccountWrapper);
            }
        } else {
            // if less than the TEN euros or/and the atm machine has run out of such banknotes
            this.dispense.doDispense(bankAccountWrapper);
        }
        // returns the modified amounts/balances
        return bankAccountWrapper;
    }
}
