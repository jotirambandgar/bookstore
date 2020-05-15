package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.service.AdminBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookstore/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    AdminBookService bookService;

    @PostMapping("/addbook")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody BookDTO bookDto, BindingResult result) throws BookStoreException {
        if (result.hasErrors()) {
            throw new BookStoreException(BookStoreException.ExceptionType.INVALID_DATA, "Invalid Data");
        }
        BookDetails bookDetails = bookService.addBook(bookDto);
        ResponseDTO userData = new ResponseDTO("Book Added Successfully", bookDetails);
        return new ResponseEntity<ResponseDTO>(userData, HttpStatus.OK);
    }

    @PostMapping("/uploadimage")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        String uploadImage = bookService.uploadImage(file);
        return uploadImage;
    }
}
