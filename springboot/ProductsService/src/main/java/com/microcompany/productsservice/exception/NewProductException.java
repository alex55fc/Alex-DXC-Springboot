package com.microcompany.productsservice.exception;

public class NewProductException extends GlobalProductException {
    private static final long serialVersionUID = 1L;

    public NewProductException(String message) {
        super(message);
    }
}
