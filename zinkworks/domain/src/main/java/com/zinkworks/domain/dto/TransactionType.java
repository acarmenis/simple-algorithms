package com.zinkworks.domain.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * TransactionType enum type. It lists each time the held transaction
 */
@ToString
@Getter
public enum TransactionType {

    CARD_ENTER_AT_THE_ATM("Cash card entrance in the atm' slot."),
    ENTER_PIN_AT_THE_ATM("Pin entered at the atm machine."),
    WITHDRAWAL("Withdrawal attempt."),
    DISPENSE("Dispense from ATM transaction."),
    DEPOSIT("Deposit attempt."),
    BALANCE_ENQUIRY("Balance enquiry."),
    CARD_EXPORTED_FROM_ATM("Cash card exported from atm' slot."),;

    // the transaction strigified message
    private final String message;

    /**
     *
     * @param message the string transaction type value
     */
    TransactionType(String message) {
        this.message = message;
    }

}
