package com.zinkworks.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 *TenNoteTransaction represents the 10 euros banknote atm dispense transaction
 */
@ToString
@Getter
@Setter
public class TenNoteTransaction extends BankTransaction {

    // constant of 10 euros banknote
    private static final int TEN = 10;

    // the banknote's number
    private int number;

    // any remainder - not of 10 euros banknote
    private int remainder;

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     */
    public TenNoteTransaction(BankAccountWrapper bankAccountWrapper) {
        super(bankAccountWrapper);
    }

    /**
     *
     * @param bankAccountWrapper BankAccountWrapper parameter
     * @param number number of 10 euros banknote parameter
     * @param remainder any reminder - not of 10 euros banknote
     */
    public TenNoteTransaction(BankAccountWrapper bankAccountWrapper, int number, int remainder) {
        super(bankAccountWrapper);
        this.number = number;
        this.remainder = remainder;
    }

    /**
     *
     * @return the number of 10 euros banknote
     */
    public int numberOfNotes() {
        // the actual number of 10 euros banknotes
        int theMainPart = (super.getBankAccountWrapper().getMoney().getAmount()/TEN);
        // checking atm for the real stored number of 10 euros banknotes
        if(theMainPart > super.getBankAccountWrapper().getAtmDTO().getTen()){
            // storing from the atm the real stored number of 10 euros banknotes
            setNumber(super.getBankAccountWrapper().getAtmDTO().getTen());
            // storing any reminder - not of 10 euros banknote - creates new amount of moneys
            setRemainder((theMainPart-getNumber())*TEN);
        } else {
            // sets the initial/actual number of 10 euros banknotes if atm has that number as well
            setNumber(theMainPart);
        }
        // returning the number of 10 euros banknote
        return getNumber();
    }

    /**
     *
     * @return remainder that represents money not required in 20 euros banknotes
     */
    public int theRemainder() {
        // sets the new money after the required amounts made of  10 euros banknotes
        setRemainder((getRemainder()+(super.getBankAccountWrapper().getMoney().getAmount()%TEN)));
        // return that money
        return getRemainder();
    }

    /**
     * does the deductions
     */
    public void calculateDeductions() {
        // atm
        // sets the new balance of the atm
        super.getBankAccountWrapper().getAtmDTO().setAtmAmount(super.getBankAccountWrapper().getAtmDTO().getAtmAmount()-(getNumber()*TEN));
        // sets the new number of 10 euros banknotes for the atm
        super.getBankAccountWrapper().getAtmDTO().setTen(super.getBankAccountWrapper().getAtmDTO().getTen()-getNumber());

        // bank account
        // sets the new bank account balance
        super.getBankAccountWrapper().getAccountDTO().setBalance(super.getBankAccountWrapper().getAccountDTO().getBalance()-(getNumber()*TEN));

        // set new amount
        super.getBankAccountWrapper().setMoney(new Money(getRemainder()));
    }

     /**
     *
     * @return the new money those left
     */
    public boolean hasReminder() {
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
                .concat(String.valueOf(TEN))
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
