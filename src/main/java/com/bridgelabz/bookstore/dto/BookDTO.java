package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    @Length(min = 2, max = 50, message = "Invalid Book Name")
    public String bookName;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z]+[ ]*[a-zA-Z]*$")
    public String authorName;
    @NotNull
    public double bookPrice;
    @NotNull
    public double noOfCopies;
    public String bookDetail;
    public String bookImageSrc;
    @NotNull
    @Range(min = 1000, max = 2020, message = "Invalid Publishing Year")
    public int publishingYear;
}
