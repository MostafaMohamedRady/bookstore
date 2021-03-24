package com.bookstore.demo.exception;

public class BookStoreException extends RuntimeException {

    private static final long serialVersionUID = 7719259325762564064L;

    public BookStoreException(String message) {
        super(message);
    }
}
