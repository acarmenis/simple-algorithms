package com.zinkworks.dispense.logic;

import com.zinkworks.domain.dto.BankAccountWrapper;

/**
 * IDispense interface
 */
public interface IDispense {
     /**
     *  sets the next chain of responsibility
     * @param next IDispense
     */
    void setNext(IDispense next);

    /**
     *
     * @param bankAccountWrapper a bank account wrapper
     * @return BankAccountWrapper
     */
    BankAccountWrapper doDispense(BankAccountWrapper bankAccountWrapper);
}
