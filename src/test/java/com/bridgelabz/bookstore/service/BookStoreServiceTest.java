package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.enumerator.SortAttribute;
import com.bridgelabz.bookstore.property.FileStorageProperty;
import com.bridgelabz.bookstore.repository.BookStoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Sort;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookStoreServiceTest {

    @Mock
    BookStoreRepository bookStoreRepository;
    @Mock
    FileStorageProperty fileStorageProperty;
    @InjectMocks
    BookStoreService bookStoreService;

    BookDTO bookDTO;

    // SearchBook

    @Test
    void givenAttributeToSearchByBookName_whenSearchBooks_shouldReturnSearchedBooks() {
        bookDTO = new BookDTO("make me ", "abcd", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookDetails.id = 1;
        List books = new ArrayList();
        books.add(bookDetails);
        when(bookStoreRepository.findByAttribute(any())).thenReturn(books);
        List<BookDetails> abc = bookStoreService.searchBook("xyz", 1);
        Assert.assertEquals(1, abc.size());
    }

    @Test
    void givenAttributeToSearchByAuthorName_whenSearchBooks_shouldReturnSearchedBooks() {
        bookDTO = new BookDTO("make me ", "abcd", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        bookDetails.id = 1;
        List books = new ArrayList();
        books.add(bookDetails);
        when(bookStoreRepository.findByAttribute(any())).thenReturn(books);
        List<BookDetails> abc = bookStoreService.searchBook("xyz", 1);
        Assert.assertEquals(1, abc.size());
    }

    @Test
    void givenAttributeToSearch_whenNoBookFound_shouldReturnNoBookFound() {
        try {
            when(bookStoreRepository.findByAttribute(any())).thenThrow(new BookStoreException(BookStoreException.ExceptionType.NO_BOOK_FOUND, "No Book Found"));
            List<BookDetails> abc = bookStoreService.searchBook("xyz", 1);
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.NO_BOOK_FOUND, e.type);
        }
    }

    // GetAllBook

    @Test
    void givenPageNumber_whenGetBooks_shouldReturnBooksOnThatPage() {
        bookDTO = new BookDTO("make me", "abc", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDTO bookDTO1 = new BookDTO("xyz", "xyz", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails bookDetails1 = new BookDetails(bookDTO1);
        List books = new ArrayList();
        books.add(bookDetails);
        books.add(bookDetails1);
        when(bookStoreRepository.findAll()).thenReturn(books);
        List<BookDetails> booksReturned = bookStoreService.getAllBooks(1);
        Assert.assertEquals(2, booksReturned.size());
    }

    @Test
    void givenWrongPageNumber_whenGetBook_shouldReturnException() {
        try {
            bookDTO = new BookDTO("make me", "abc", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
            BookDTO bookDTO1 = new BookDTO("xyz", "xyz", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
            BookDetails bookDetails = new BookDetails(bookDTO);
            BookDetails bookDetails1 = new BookDetails(bookDTO1);
            List books = new ArrayList();
            books.add(bookDetails);
            books.add(bookDetails1);
            when(bookStoreRepository.findAll()).thenThrow(new BookStoreException(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, "Max Page Limit Reached"));
            List<BookDetails> booksReturned = bookStoreService.getAllBooks(12);
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, e.type);
        }
    }

    // Match Image

    @Test
    void givenImageId_WhenIdFound_ShouldEquals() throws MalformedURLException {
        Path path = Paths.get("/home/admin1/Documents/FinalProject/BookStoreBackEnd/src/main/resources/Images/306305a4-5c2a-4de7-9258-aa42b505fde2-sample-image-png-.png");
        Resource resource = new UrlResource(path.toUri());
        when(this.fileStorageProperty.getUploadDir()).thenReturn("/home/admin1/Documents/FinalProject/BookStoreBackEnd/src/main/resources/Images/");
        Resource imageResponse = bookStoreService.getImageResponse("306305a4-5c2a-4de7-9258-aa42b505fde2-sample-image-png-.png");
        Assert.assertEquals(resource, imageResponse);
    }

    //Count No Of Books

    @Test
    void givenStoredBooks_WhenGetCount_ShouldReturnNoOfBooks() {
        when(bookStoreRepository.getCountOfBooks()).thenReturn(6);
        int storedBookCount = bookStoreService.getStoredBookCount();
        Assert.assertEquals(6, storedBookCount);
    }

    //Count books w.r.t search

    @Test
    void givenStoredBooksWithAttribute_WhenGetCount_ShouldReturnNoOfBooks() {
        when(bookStoreRepository.getCountOfSearchBooks("steve")).thenReturn(6);
        int storedBookCount = bookStoreService.getStoredBookCount("steve");
        Assert.assertEquals(6, storedBookCount);
    }

    @Test
    void givenWrongDirectoryPath_WhenDirectoryNotFound_ThrowException() {
        Path path = Paths.get("/home/admin1/FinalProject/BookStoreBackEnd/src/main/resources/Images/306305a4-5c2a-4de7-9258-aa42b505fde2-sample-image-png-.png");
        try {
            Resource resource = new UrlResource(path.toString());
            when(this.fileStorageProperty.getUploadDir()).thenReturn(null);
            Resource imageResponse = bookStoreService.getImageResponse("306305a4-image-png-.png");
        } catch (MalformedURLException e) {
        }
    }

    //Sort Attribute

    @Test
    void whenGetSortAttribute_ThenItShouldReturnSortingEnums() {
        SortAttribute[] sortAttribute = bookStoreService.getSortAttribute();
        Assert.assertEquals(SortAttribute.LOW_TO_HIGH, sortAttribute[0]);
        Assert.assertEquals(SortAttribute.HIGH_TO_LOW, sortAttribute[1]);
        Assert.assertEquals(SortAttribute.NEWEST_ARRIVALS, sortAttribute[2]);
    }

    //Sort

    @Test
    void givenEnumAttribute_WhenGetSortedData_ThenItShouldReturnSortedData() {
        bookDTO = new BookDTO("make me", "abc", 1000.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDTO bookDTO1 = new BookDTO("xyz", "xyz", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails bookDetails1 = new BookDetails(bookDTO1);
        List books = new ArrayList();
        books.add(bookDetails);
        books.add(bookDetails1);
        when(bookStoreRepository.findAll(Sort.by(Sort.Direction.ASC, "bookPrice"))).thenReturn(books);
        List<BookDetails> sortedBookData = bookStoreService.getSortedBookData(SortAttribute.LOW_TO_HIGH, 1);
        Assert.assertEquals(bookDetails, sortedBookData.get(0));
        Assert.assertEquals(bookDetails1, sortedBookData.get(1));
    }

    @Test
    void givenWrongPageNumber_whenGetSortedData_shouldReturnException() {
        try {
            when(bookStoreRepository.findAll(Sort.by(Sort.Direction.ASC, "bookPrice"))).thenThrow(new BookStoreException(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, "Enter Valid Page Number"));
            List<BookDetails> sortedBookData = bookStoreService.getSortedBookData(SortAttribute.LOW_TO_HIGH, 12);
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, e.type);
        }
    }
}
