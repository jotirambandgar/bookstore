package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@ToString
@Setter
@NoArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    public String bookName;
    public String authorName;
    public double bookPrice;
    public double noOfCopies;
    public String bookDetail;
    public String bookImageSrc;
    public int publishingYear;

    public BookDetails(BookDTO bookDTO) {
        this.bookName = bookDTO.bookName;
        this.authorName = bookDTO.authorName;
        this.bookPrice = bookDTO.bookPrice;
        this.noOfCopies = bookDTO.noOfCopies;
        this.bookDetail = bookDTO.bookDetail;
        this.bookImageSrc = bookDTO.bookImageSrc;
        this.publishingYear = bookDTO.publishingYear;
    }
}
