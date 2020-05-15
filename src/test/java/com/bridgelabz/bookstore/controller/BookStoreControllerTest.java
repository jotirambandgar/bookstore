package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.enumerator.SortAttribute;
import com.bridgelabz.bookstore.service.BookStoreService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class
BookStoreControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookStoreService bookStoreService;

    BookDTO bookDTO;
    Gson gson = new Gson();


    @Test
    void givenAttributeToSearchByBookName_whenSearchBooks_shouldReturnSearchedBooks() throws Exception {
        bookDTO = new BookDTO("make me ", "abcd", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List books = new ArrayList();
        books.add(bookDetails);
        when(bookStoreService.searchBook(any(), anyInt())).thenReturn(books);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/search/abcd/1")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(bookDetails.authorName));
    }

    @Test
    void givenAttributeToSearchByAuthorName_whenSearchBooks_shouldReturnSearchedBooks() throws Exception {
        bookDTO = new BookDTO("make me ", "abcd", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        List books = new ArrayList();
        books.add(bookDetails);
        when(bookStoreService.searchBook(any(), anyInt())).thenReturn(books);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/search/abcd/1")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains(bookDetails.authorName));
    }

    @Test
    void givenAttributeToSearch_whenNoBookFound_shouldReturnNoBookFound() throws Exception {
        try {
            when(bookStoreService.searchBook(any(), anyInt())).thenThrow(new BookStoreException(BookStoreException.ExceptionType.NO_BOOK_FOUND, "No Book Found!!"));
            MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/search/zyx/1")).andReturn();
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.NO_BOOK_FOUND, e.type);
        }
    }

    // AllData
    @Test
    void givenPageNumber_whenGetBooks_shouldReturnBooksOnPageNumber() throws Exception {
        bookDTO = new BookDTO("make me", "abc", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDTO bookDTO1 = new BookDTO("xyz", "xyz", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails bookDetails1 = new BookDetails(bookDTO1);
        List books = new ArrayList();
        books.add(bookDetails);
        books.add(bookDetails1);
        when(bookStoreService.getAllBooks(anyInt())).thenReturn(books);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/books/1")).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        Assert.assertTrue(contentAsString.contains("make"));
    }

    @Test
    void givenWrongPageNumber_whenGetBooks_shouldThrowMaxPageLimitReachedException() throws Exception {
        try {
            when(bookStoreService.getAllBooks(anyInt())).thenThrow(new BookStoreException(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, "Max Page Limit Reached"));
            MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/books/12")).andReturn();
            String contentAsString = mvcResult.getResponse().getContentAsString();
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.MAX_PAGE_LIMIT_REACHED, e.type);
        }
    }

    //Book Count

    @Test
    void givenRequest_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        when(bookStoreService.getStoredBookCount()).thenReturn(3);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/count")).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenRequest_WhenGetResponse_ItsResponseShouldReturnCorrect() throws Exception {
        when(bookStoreService.getStoredBookCount()).thenReturn(6);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/count")).andReturn();
        Assert.assertEquals("6",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void givenWrongUrl_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        when(bookStoreService.getStoredBookCount()).thenReturn(3);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore// $ count")).andReturn();
        Assert.assertEquals(404,mvcResult.getResponse().getStatus());
    }


    @Test
    void givenRequestWithAttribute_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        when(bookStoreService.getStoredBookCount("steve")).thenReturn(3);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/count/steve")).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenRequestWithAttribute_WhenGetResponse_ItsResponseShouldReturnCorrect() throws Exception {
        when(bookStoreService.getStoredBookCount("steve")).thenReturn(6);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore//count/steve")).andReturn();
        Assert.assertEquals("6",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void givenWrongUrlWithAttribute_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        when(bookStoreService.getStoredBookCount("steve")).thenReturn(3);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore// $ count/steve")).andReturn();
        Assert.assertEquals(404,mvcResult.getResponse().getStatus());
    }

    //Image

    @Test
    void givenImageUrl_WhenGetResponse_ItShouldReturnStatusOk() throws Exception {
        Path path = Paths.get("/home/admin1/Documents/FinalProject/BookStoreBackEnd/src/main/resources/Images/4d29804d-ebad-4db8-886b-504c137a0669-Twilight 1.jpg");
        Resource resource = new UrlResource(path.toUri());
        when(bookStoreService.getImageResponse
                (any()))
                .thenReturn(resource);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/image/34545345345")).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenWrongImageUrl_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        Path path = Paths.get("/home/admin1/Documents/Project/BookStoreBackEnd/src/main/resources/Images/306305a4-5c2a-4de7-9258-aa42b505fde2-sample-image-png-.png");
        Resource resource = new UrlResource(path.toUri());
        when(bookStoreService.getImageResponse(any()))
                .thenReturn(resource);
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/xcx/34545345345")).andReturn();
        Assert.assertEquals(404,mvcResult.getResponse().getStatus());
    }

    // Get SortAttribute

    @Test
    void givenRequest_WhenGetSortAttribute_ItShouldReturnStatusOk() throws Exception {
        when(bookStoreService.getSortAttribute()).thenReturn(SortAttribute.values());
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sortattribute")).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenRequest_WhenGetSortAttribute_ItShouldReturnCorrectValue() throws Exception {
        when(bookStoreService.getSortAttribute()).thenReturn(SortAttribute.values());
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sortattribute")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("LOW_TO_HIGH"));
    }

    @Test
    void givenWrongUrl_WhenGetSortAttribute_ItShouldReturnStatusBad() throws Exception {
        when(bookStoreService.getSortAttribute()).thenReturn(SortAttribute.values());
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sorrt")).andReturn();
        Assert.assertEquals(404,mvcResult.getResponse().getStatus());
    }

    // Sort

    @Test
    void givenSortAttribute_WhenGetResponse_ItShouldReturnOk() throws Exception {
        when(bookStoreService.getSortAttribute()).thenReturn(SortAttribute.values());
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sort/LOW_TO_HIGH/1")).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenSortAttribute_WhenGetResponse_ItShouldReturnCorrectResult() throws Exception{
        bookDTO = new BookDTO("make me", "abc", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDTO bookDTO1 = new BookDTO("xyz", "xyz", 1500.0, 5, "sdrftgvhbjnkm", "sedcfgvbh", 2013);
        BookDetails bookDetails = new BookDetails(bookDTO);
        BookDetails bookDetails1 = new BookDetails(bookDTO1);
        List books = new ArrayList();
        books.add(bookDetails);
        books.add(bookDetails1);
        when(bookStoreService.getSortedBookData(any(), anyInt())).thenReturn((books));
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sort/LOW_TO_HIGH/1")).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("abc"));
    }

    @Test
    void givenWrongUrl_WhenGetResponse_ItShouldReturnBadStatus() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sort  /LOW_TO_HIGH/1")).andReturn();
        Assert.assertEquals(404,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenAnotherAttribute_WhenGetRespone_ItShouldReturnNull() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/bookstore/sort/LOW/1")).andReturn();
        Assert.assertEquals("Invalid Attribute",mvcResult.getResponse().getContentAsString());
    }
}
