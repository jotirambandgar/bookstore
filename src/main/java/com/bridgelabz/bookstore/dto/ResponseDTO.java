package com.bridgelabz.bookstore.dto;

import com.bridgelabz.bookstore.model.BookDetails;

public class ResponseDTO {

    public int orderBookDetail;
    public String message;
    public BookDetails bookDetails;

    public ResponseDTO(String message, BookDetails bookDetails) {
        this.message = message;
        this.bookDetails = bookDetails;
    }

    public ResponseDTO(String message, int orderBookDetail) {
        this.message=message;
        this.orderBookDetail=orderBookDetail;
    }
}
