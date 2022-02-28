package com.zinkworks.domain.dto;


import lombok.*;

/**
 * BankTransaction is an abstract and common parent transaction
 * for any of the current banknotes of {5,10,20 and 50}
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public abstract class BankTransaction {

    /**
     * the wrapper bank account,atm and users money request
      */
    private BankAccountWrapper bankAccountWrapper;

    /**
     * message is overriden from all its children
     * @return String message for any particular banknote
     */
    public abstract String message();

    /**
     * message is doComputations from all its children
     * does the necessary computations, deductions
     */
    public abstract void doComputations();

}
