package com.microcompany.accountsservice.exception;

public class CustomerNotAllowedException extends GlobalException {
    protected static final long serialVersionUID = 2L;

    public CustomerNotAllowedException() {
        super("The customer does not have permission to perform the action.");
    }

    public CustomerNotAllowedException(Long customerId) {
        super("Customer with id: " + customerId + " does not have permission to perform the action.");
    }
}
