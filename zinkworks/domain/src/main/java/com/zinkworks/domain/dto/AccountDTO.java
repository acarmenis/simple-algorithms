package com.zinkworks.domain.dto;

import com.zinkworks.domain.Account;
import lombok.*;

/**
 * AccountDTO  a dto for the Account entity
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AccountDTO {

    // id
    private Long id;

    //accountNumber
    private Long accountNumber;

    //pin
    private String pin;

    //balance
    private Integer balance;

    //overDraft
    private Integer overDraft;

    /**
     * construct that can gets initialized through the Account entity
     * @param account parameter
     */
    public AccountDTO(Account account){
        this(account.getId(), account.getAccountNumber(), account.getPin(), account.getBalance(), account.getOverDraft());
    }

    /**
     *  dto-to-account-converter
     * @param accountDTO parameter
     * @return Account
     */
    public Account ftoAccount(AccountDTO accountDTO){
        return new Account(accountDTO.getId(), accountDTO.getAccountNumber(), accountDTO.getPin(), accountDTO.getBalance(), accountDTO.getOverDraft());
    }

    /**
     *  account-to-dto-converter
     * @param account parameter
     * @return AccountDTO
     */
    public AccountDTO fromAccount(Account account){
        return new AccountDTO(account.getId(), account.getAccountNumber(), account.getPin(), account.getBalance(), account.getOverDraft());
    }
}
