package com.appointment.users.exception;

public class AccountDisabledException extends RuntimeException{
    public AccountDisabledException(String accountIsDisabled) {
        super("Account disabled.Please contact admin");
    }
}

