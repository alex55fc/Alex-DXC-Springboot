package com.microcompany.accountsservice.exception;

public class CustomerNotOwnerException extends GlobalException {
    protected static final long serialVersionUID = 2L;

    public CustomerNotOwnerException() {
        super("The customer is not the owner.");
    }

    public CustomerNotOwnerException(Long customerId) {
        super("Customer with id: " + customerId + " is not the owner.");
    }
}
