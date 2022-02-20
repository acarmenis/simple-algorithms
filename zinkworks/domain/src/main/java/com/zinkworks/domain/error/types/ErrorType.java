package com.zinkworks.domain.error.types;

import lombok.Getter;
import lombok.ToString;

/**
 * ErrorType is an enum type which holds the id and message errors used in the application
 */
@ToString
@Getter
public enum ErrorType {

    DATABASE("A0001", "A database error has occurred."),

    // Account
    DATABASE_ACCOUNT_NOT_FOUND("A0002", "Account cannot be found in database."),
    DATABASE_ACCOUNTS_NOT_FOUND("A0003", "Accounts cannot be found in database."),
    DATABASE_ACCOUNTS_NOT_SAVED("A0004", "Account cannot be saved in database."),
    DATABASE_ACCOUNTS_NOT_UPDATED("A0005", "Account cannot be updated in database."),

    // Atm
    DATABASE_ATM_NOT_FOUND("A0006", "Atm cannot be found in database."),
    DATABASE_ATMS_NOT_FOUND("A0007", "Atms cannot be found in database."),
    DATABASE_ATM_NOT_SAVED("A0008", "Atm cannot be saved in database."),
    DATABASE_ATM_NOT_UPDATED("A0009", "Atm cannot be updated in database."),
    ATM_CANNOT_SERVE_THE_REQUESTED_AMOUNT("A0010", "Atm cannot serve the requested amount."),

    // Invalids
    INVALID_TRANSACTION("D0012",	"Invalid transaction."),
    INVALID_AMOUNT("D0013", "Invalid Amount."),
    INVALID_CARD_NUMBER("D0014", "Invalid Card Number."),
    INVALID_PIN("D0055", "Invalid PIN."),
    PIN_MISMATCHES("D0056", "PIN numbers, do not match."),
    CANNOT_VERIFY_PIN("D0083", "Cannot verify PIN."),

    NO_CREDIT_CARD_ACCOUNT("D0039", "No credit account."),
    EXPIRED_CARD("D0054", "Expired Card."),
    NUMBER_OF_PIN_TRIES_EXCEEDED("D0075" , "Number of PIN tries exceeded."),

    DISPENSE_AMOUNT_NOT_MULTIPLE_OF("D0025", "Dispense amount must be a multiple of 5€."),

    EXCEEDED_ISSUER_LIMIT("D0024", "Exceeds issuer withdrawal limit."),
    EXCEEDED_WITHDRAWAL_LIMIT("D0061", "Exceed withdrawal limit."),
    INSUFFICIENT_FUNDS("D0051", "Insufficient funds."),
    MONEY_BAD_FORMAT("D0052", "Money in wrong format."),
    MONEY_CANNOT_BE_CONVERTED_FROM_STRING("D0053", "Money cannot be converted from string type."),

    ZERO_ACCOUNT_BALANCE("D0054", "Zero account balance."),
    REQUESTED_MONEY_EXCEEDS_YOUR_ACCOUNT_BALANCE("D0055", "Requested money exceeds your account balance."),
    ZERO_AMOUNT_REQUEST("D0156", "Requested an mount of 0€."),

    TRANSACTION_NOT_PERMITTED("D0057", "Transaction not permitted -card"),

    NO_ACCOUNT("D0078", "No Account."),

    BANK_UNAVAILABLE("D0091"	, "Bank unavailable."),
    SYSTEM_UNAVAILABLE("D0092", "System unavailable."),
    TRANSACTION_SERIAL_NUMBER_MISMATCH("D0093", "Transaction serial number mismatch."),

    NO_CONNECTION("D1000", "No Connection."),
    TRANSMISSION_ERROR("D1200", "Transmission error."),
    LOW_CASH("20002", "Low Cash.");

    private final String id;
    private final String message;

    ErrorType(String id, String message) {
        this.id = id;
        this.message = message;
    }

}
