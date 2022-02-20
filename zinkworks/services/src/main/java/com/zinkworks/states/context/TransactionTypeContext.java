package com.zinkworks.states.context;

import com.zinkworks.domain.dto.AtmClientRequest;
import com.zinkworks.services.ATMDispenser;
import com.zinkworks.services.IAccountService;
import com.zinkworks.services.IAtmService;
import com.zinkworks.states.IState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TransactionTypeContext set the state context
 */
@Getter
@Setter
@NoArgsConstructor
public class TransactionTypeContext implements IState {
    // the transactionState state
    private IState transactionState;
    /**
     *
     * @param atmRequest Is the client's request. Encompasses the user's pin and the requested money.
     * @param accountService the account service in the service layer, used for interactions with repositories account transactions.
     * @param atmService the atm service in the service layer, used for interactions with repositories account transactions.
     * @param atmDispenser the chain responsibility atm design pattern - used for the actual dispensing
     */
    @Override
    public void getAction(AtmClientRequest atmRequest, IAccountService accountService, IAtmService atmService, ATMDispenser atmDispenser) {
        this.transactionState.getAction(atmRequest, accountService, atmService, atmDispenser);
    }
}
