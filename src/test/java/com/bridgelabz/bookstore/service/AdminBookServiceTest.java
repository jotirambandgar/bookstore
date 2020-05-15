package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.property.FileStorageProperty;
import com.bridgelabz.bookstore.repository.BookStoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import java.io.File;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminBookServiceTest {

    @Mock
    BookStoreRepository bookStoreRepository;

    @Mock
    FileStorageProperty fileStorageProperty;

    @InjectMocks
    AdminBookService bookService;

    BookDTO bookDTO;

    @Test
    void givenBookDTO_whenBooksStore_ItShouldReturnBookDetails() {
        bookDTO = new BookDTO("make me think", "steve", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(bookStoreRepository.save(any())).thenReturn(bookDetails);
        BookDetails bookDetails1 = bookService.addBook(bookDTO);
        Assert.assertEquals(bookDetails, bookDetails1);
    }

    @Test
    void givenSameBookDTO_whenBooksStore_ItShouldReturnBookDetails() {
        bookDTO = new BookDTO("make me think", "steve", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        when(bookStoreRepository.save(any())).thenReturn(bookDetails);
        when(bookStoreRepository.findBybookName(bookDTO.bookName)).thenReturn(Optional.of(bookDetails));
        BookDetails bookDetails2 = bookService.addBook(bookDTO);
        Assert.assertEquals(bookDetails, bookDetails2);
    }

    @Test
    void givenImage_whenStoreInDirectory_ReturnTrue() {
        when(fileStorageProperty.getUploadDir()).thenReturn("/home/admin1/Documents/FinalProject/TestImage/");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "filename.png", "text/plain", "some xml".getBytes());
        bookService.uploadImage(multipartFile);
        String path = fileStorageProperty.getUploadDir();
        Assert.assertTrue(new File(path).exists());
    }

    @Test
    void givenImage_whenNotStoreInDirectory_ReturnFalse() {
        when(fileStorageProperty.getUploadDir()).thenReturn("/home/admin1/Documents/FinalProject/TestImage/");
        MockMultipartFile multipartFile = new MockMultipartFile("data", "filename.png", "text/plain", "some xml".getBytes());
        String path = bookService.uploadImage(multipartFile);
        Assert.assertFalse(new File(path + "adg").exists());
    }

    @Test
    void givenWrongImageName_whenItIsNotStore_thenTrowException() {
        try {
            when(fileStorageProperty.getUploadDir()).thenReturn("/home/admin1/Documents/FinalProject/TestImage/");
            MockMultipartFile multipartFile = new MockMultipartFile("data", ".png", "text/plain", "some xml".getBytes());
            String path = bookService.uploadImage(multipartFile);
        } catch (BookStoreException b) {
            Assert.assertEquals(BookStoreException.ExceptionType.INVALID_FILE_NAME, b.type);
        }
    }

    @Test
    void givenNullDirectory_whenDirectoryNotFound_thenTrowException() {
        try {
            when(fileStorageProperty.getUploadDir()).thenReturn(null);
            MockMultipartFile multipartFile = new MockMultipartFile("data", "filename.png", "text/plain", "some xml".getBytes());
            String path = bookService.uploadImage(multipartFile);
        } catch (BookStoreException b) {
            Assert.assertEquals(BookStoreException.ExceptionType.DIRECTORY_NOT_FOUND, b.type);
        }
    }
}
