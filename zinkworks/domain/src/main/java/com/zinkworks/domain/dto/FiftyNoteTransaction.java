package com.zinkworks.domain.dto;

import lombok.*;

/**
 *FiftyNoteTransaction represents the 50 euros banknote atm dispense transaction
 */
@ToString
@Getter
@Setter
public class FiftyNoteTransaction extends BankTransaction {

    // constant of 50 euros banknote
    private static final int FIFTY = 50;

    // the banknote's number
    private int number;

    // any remainder - not of 50 euros banknote
    private int remainder;

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     */
    public FiftyNoteTransaction(BankAccountWrapper bankAccountWrapper) {
        super(bankAccountWrapper);
    }

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     * @param number number of 50 euros banknote parameter
     * @param remainder any remainder - not of 50 euros banknote
     */
    public FiftyNoteTransaction(BankAccountWrapper bankAccountWrapper, int number, int remainder) {
        super(bankAccountWrapper);
        this.number = number;
        this.remainder = remainder;
    }

    /**
     *
     * @return the number of 50 euros banknote
     */
    public int numberOfNotes() {
        // the actual number of 50 euros banknotes
        int theMainPart = (super.getBankAccountWrapper().getMoney().getAmount()/FIFTY);
        // checking atm for the real stored number of 50 euros banknotes
        if(theMainPart > super.getBankAccountWrapper().getAtmDTO().getFifty()){
            // storing from the atm the real stored number of 50 euros banknotes
            setNumber(super.getBankAccountWrapper().getAtmDTO().getFifty());
            // storing any reminder - not of 50 euros banknote - creates new amount of moneys
            setRemainder((theMainPart-getNumber())*FIFTY);
        } else {
            // sets the initial/actual number of 50 euros banknotes if atm has that number as well
            setNumber(theMainPart);
        }
        // returning the number of 50 euros banknote
        return getNumber();
    }

    /**
     *
     * @return remainder that represents money not required in 50 euros banknotes
     */
    public int theRemainder() {
        setRemainder((getRemainder()+(super.getBankAccountWrapper().getMoney().getAmount()%FIFTY)));
        return getRemainder();
    }

    /**
     * does the deductions
     */
    public void calculateDeductions() {
        // atm
        // sets the new balance of the atm
        super.getBankAccountWrapper().getAtmDTO().setAtmAmount(super.getBankAccountWrapper().getAtmDTO().getAtmAmount()-(getNumber()*FIFTY));
        // sets the new number of 50 euros banknotes for the atm
        super.getBankAccountWrapper().getAtmDTO().setFifty(super.getBankAccountWrapper().getAtmDTO().getFifty()-getNumber());

        // bank account
        // sets the new bank account balance
        super.getBankAccountWrapper().getAccountDTO().setBalance(super.getBankAccountWrapper().getAccountDTO().getBalance()-(getNumber()*FIFTY));

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
                .concat(String.valueOf(FIFTY))
                .concat("€");
    }

    @Override
    public void doComputations() {
        numberOfNotes();
        theRemainder();
        calculateDeductions();
    }
}
