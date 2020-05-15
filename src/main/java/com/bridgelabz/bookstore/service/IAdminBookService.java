package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookDetails;
import org.springframework.web.multipart.MultipartFile;

public interface IAdminBookService {

    BookDetails addBook(BookDTO bookDTO);
    String uploadImage(MultipartFile bookDTO);
}
