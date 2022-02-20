package com.zinkworks.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *FiveNoteTransaction represents the 5 euros banknote atm dispense transaction
 */
@ToString
@Getter
@Setter
public class FiveNoteTransaction extends BankTransaction {

    // constant of 5 euros banknote
    private static final int FIVE = 5;

    // the banknote's number
    private int number;

    // any remainder - not of 5 euros banknote
    private int remainder;

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     */
    public FiveNoteTransaction(BankAccountWrapper bankAccountWrapper) {
        super(bankAccountWrapper);
    }

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     * @param number number of 5 euros banknote parameter
     * @param remainder any reminder - not of 5 euros banknote
     */
    public FiveNoteTransaction(BankAccountWrapper bankAccountWrapper, int number, int remainder) {
        super(bankAccountWrapper);
        this.number = number;
        this.remainder = remainder;
    }

    /**
     *
     * @return the number of 5 euros banknote
     */
    public int numberOfNotes() {
        // the actual number of 5 euros banknotes
        int theMainPart = (super.getBankAccountWrapper().getMoney().getAmount()/FIVE);
        // checking atm for the real stored number of 5 euros banknotes
        if(theMainPart > super.getBankAccountWrapper().getAtmDTO().getFive()){
            // storing from the atm the real stored number of 5 euros banknotes
            setNumber(super.getBankAccountWrapper().getAtmDTO().getFive());
            // storing any reminder - not of 5 euros banknote - creates new amount of moneys
            setRemainder((theMainPart-getNumber())*FIVE);
        } else {
            // sets the initial/actual number of 5 euros banknotes if atm has that number as well
            setNumber(theMainPart);
        }
        // returning the number of 5 euros banknote
        return getNumber();
    }

    /**
     *
     * @return remainder that represents money not required in 5 euros banknotes
     */
    public int theRemainder() {
        // sets the new money after the required amounts made of 5 euros banknotes
        setRemainder((getRemainder()+(super.getBankAccountWrapper().getMoney().getAmount()%FIVE)));
        // return that money
        return getRemainder();
    }

    /**
     * does the deductions
     */
    public void calculateDeductions() {
        // atm
        // sets the new balance of the atm
        super.getBankAccountWrapper().getAtmDTO().setAtmAmount(super.getBankAccountWrapper().getAtmDTO().getAtmAmount()-(getNumber()*FIVE));
        // sets the new number of 5 euros banknotes for the atm
        super.getBankAccountWrapper().getAtmDTO().setFive(super.getBankAccountWrapper().getAtmDTO().getFive()-getNumber());

        // bank account
        // sets the new bank account balance
        super.getBankAccountWrapper().getAccountDTO().setBalance(super.getBankAccountWrapper().getAccountDTO().getBalance()-(getNumber()*FIVE));

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

    @Override
    public String message() {
        return  "Dispensing".concat(" ")
                .concat(String.valueOf(getNumber()))
                .concat(" ")
                .concat(("note".concat((getNumber()<=1)?"":"s")))
                .concat(" ")
                .concat("of")
                .concat(" ")
                .concat(String.valueOf(FIVE))
                .concat("€");
    }

    @Override
    public void doComputations() {
        numberOfNotes();
        theRemainder();
        calculateDeductions();
    }
}
