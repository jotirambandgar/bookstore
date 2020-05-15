package com.bridgelabz.bookstore.exception;

public class BookStoreException extends RuntimeException {

    public ExceptionType type;

    public enum ExceptionType {
        INVALID_DATA, NO_BOOK_FOUND, MAX_PAGE_LIMIT_REACHED, DIRECTORY_NOT_FOUND, INVALID_FILE_NAME, FILE_NOT_STORED, ORDER_QUANTITY_GREATER_THEN_STOCK, AUTHENTICATION_ERROR, INTERNAL_SERVER_ERROR, INVALID_FILE_PATH
    }

    public BookStoreException(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
