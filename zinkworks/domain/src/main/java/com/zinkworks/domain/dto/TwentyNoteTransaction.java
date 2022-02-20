package com.zinkworks.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *TwentyNoteTransaction represents the 20 euros banknote atm dispense transaction
 */
@ToString
@Getter
@Setter
public class TwentyNoteTransaction extends BankTransaction {

    // constant of 20 euros banknote
    private static final int TWENTY = 20;

    // the banknote's number
    private int number;

    // any remainder - not of 20 euros banknote
    private int remainder;

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     */
    public TwentyNoteTransaction(BankAccountWrapper bankAccountWrapper) {
        super(bankAccountWrapper);
    }

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     * @param number number of 20 euros banknote parameter
     * @param remainder any reminder - not of 20 euros banknote
     */
    public TwentyNoteTransaction(BankAccountWrapper bankAccountWrapper, int number, int remainder) {
        super(bankAccountWrapper);
        this.number = number;
        this.remainder = remainder;
    }

    /**
     *
     * @return the number of 20 euros banknote
     */
    private int numberOfNotes() {
        // the actual number of 20 euros banknotes
        int theMainPart = (super.getBankAccountWrapper().getMoney().getAmount()/TWENTY);
        // checking atm for the real stored number of 20 euros banknotes
        if(theMainPart > super.getBankAccountWrapper().getAtmDTO().getTwenty()){
            // storing from the atm the real stored number of 20 euros banknotes
            setNumber(super.getBankAccountWrapper().getAtmDTO().getTwenty());
            // storing any reminder - not of 20 euros banknote - creates new amount of moneys
            setRemainder((theMainPart-getNumber())*TWENTY);
        } else {
            // sets the initial/actual number of 20 euros banknotes if atm has that number as well
            setNumber(theMainPart);
        }
        // returning the number of 20 euros banknote
        return getNumber();
    }

    /**
     *
     * @return remainder that represents money not required in 20 euros banknotes
     */
    private int theRemainder() {
        // sets the new money after the required amounts made of  20 euros banknotes
        setRemainder((getRemainder()+(super.getBankAccountWrapper().getMoney().getAmount()%TWENTY)));
        // return that money
        return getRemainder();
    }

    /**
     * does the deductions
     */
    private void calculateDeductions() {
        // atm
        // sets the new balance of the atm
        super.getBankAccountWrapper().getAtmDTO().setAtmAmount(super.getBankAccountWrapper().getAtmDTO().getAtmAmount()-(getNumber()*TWENTY));
        // sets the new number of 20 euros banknotes for the atm
        super.getBankAccountWrapper().getAtmDTO().setTwenty(super.getBankAccountWrapper().getAtmDTO().getTwenty()-getNumber());

        // bank account
        // sets the new bank account balance
        super.getBankAccountWrapper().getAccountDTO().setBalance(super.getBankAccountWrapper().getAccountDTO().getBalance()-(getNumber()*TWENTY));

        // set new amount
        super.getBankAccountWrapper().setMoney(new Money(getRemainder()));
    }

    /**
     *
     * @return the new money those left
     */
    public boolean hasRemainder() {
        return (getRemainder() != 0);
    }

    /**
     *
     * @return String message
     */
    @Override
    public String message() {
        return  "Dispensing".concat(" ")
                .concat(String.valueOf(getNumber()))
                .concat(" ")
                .concat(("note".concat((getNumber()<=1)?"":"s")))
                .concat(" ")
                .concat("of")
                .concat(" ")
                .concat(String.valueOf(TWENTY))
                .concat("€");
    }

    /**
     * does the computations/money deductions
     */
    @Override
    public void doComputations() {
        numberOfNotes();
        theRemainder();
        calculateDeductions();
    }
}
