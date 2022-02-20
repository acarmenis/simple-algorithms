package com.zinkworks.domain.dto;

import lombok.*;

/**
 * BankAccountWrapper is used as a wrapper for the dispensing process
 * passing through different chain of responsibilities.
 * It wrappers bank account, atm and user's money request
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class BankAccountWrapper {
    // atm
    private AtmDTO atmDTO;
    // account
    private AccountDTO accountDTO;
    // money
    private Money money;
    // isSimulation -> true/false for transaction state
    private boolean isSimulation;
}
