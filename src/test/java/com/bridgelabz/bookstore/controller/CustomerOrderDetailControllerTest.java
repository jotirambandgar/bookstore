package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;
import com.bridgelabz.bookstore.model.OrderBookDetail;
import com.bridgelabz.bookstore.service.OrderBookDetailService;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOrderDetailControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderBookDetailService orderBookDetailService;

    Gson gson = new Gson();
    OrderBookDetailDTO orderBookDetailDTO;

    @Test
    void givenRequest_whenGetResponse_ItShouldReturnStatusOk() throws Exception {
        OrderBookDetailDTO[] orderBookDetails = new OrderBookDetailDTO[2];
        orderBookDetails[1] = new OrderBookDetailDTO(1, 1, 6000.0, "aaaa", "5234543212", "123456", "aaaa","girrutannel-0466@yopmail.com", "aaaaaaaa", "aaaaa", "aaaaaa", "aaaa");
        orderBookDetails[1] = new OrderBookDetailDTO(3, 1, 6000.0, "aaaa", "5234543212", "123456", "aaaa","girrutannel-0466@yopmail.com", "aaaaaaaa", "aaaaa", "aaaaaa", "aaaa");
        String jsonDto = gson.toJson(orderBookDetails);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(1);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/customerbookdetails").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @Test
    void givenRequest_WhenGetResponse_ItsResponseShouldReturnCorrect() throws Exception {
        OrderBookDetailDTO[] orderBookDetails = new OrderBookDetailDTO[2];
        orderBookDetails[1] = new OrderBookDetailDTO(1, 1, 6000.0, "aaaa", "5234543212", "123456", "aaaa","girrutannel-0466@yopmail.com", "aaaaaaaa", "aaaaa", "aaaaaa", "aaaa");
        orderBookDetails[1] = new OrderBookDetailDTO(3, 1, 6000.0, "aaaa", "5234543212", "123456", "aaaa","girrutannel-0466@yopmail.com", "aaaaaaaa", "aaaaa", "aaaaaa", "aaaa");
        String jsonDto = gson.toJson(orderBookDetails);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(1);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/customerbookdetails").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("Info Added Successfully"));
        Assert.assertTrue(mvcResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    void givenWrongUrl_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        orderBookDetailDTO = new OrderBookDetailDTO(7, 2, 6000.0, "jjggjmhk", "9312345674", "400086", "tfjn","girrutannel-0466@yopmail.com", "fgbhjn tgyuhj", "cfgvhbj", "gvbhjnmk", "vghjnkml");
        String jsonDto = gson.toJson(orderBookDetailDTO);
        OrderBookDetail orderBookDetail = new OrderBookDetail(orderBookDetailDTO);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(123456);
        MvcResult mvcResult = this.mockMvc.perform(post("/  bookstore/customerbookdetails").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(404, status);
    }

    @Test
    void givenPutRequestInsteadOfPost_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        orderBookDetailDTO = new OrderBookDetailDTO(7, 2, 6000.0, "jjggjmhk", "9312345674", "400086", "tfjn","girrutannel-0466@yopmail.com", "fgbhjn tgyuhj", "cfgvhbj", "gvbhjnmk", "vghjnkml");
        String jsonDto = gson.toJson(orderBookDetailDTO);
        OrderBookDetail orderBookDetail = new OrderBookDetail(orderBookDetailDTO);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(123456);
        MvcResult mvcResult = this.mockMvc.perform(put("/bookstore/customerbookdetails").content(jsonDto)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(405, status);
    }

    @Test
    void givenAnotherContentType_WhenGetResponse_ItShouldReturnStatusBad() throws Exception {
        orderBookDetailDTO = new OrderBookDetailDTO(7, 2, 6000.0, "jjggjmhk", "9312345674", "400086", "tfjn","girrutannel-0466@yopmail.com", "fgbhjn tgyuhj", "cfgvhbj", "gvbhjnmk", "vghjnkml");
        String jsonDto = gson.toJson(orderBookDetailDTO);
        OrderBookDetail orderBookDetail = new OrderBookDetail(orderBookDetailDTO);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(345671);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/customerbookdetails").content(jsonDto)
                .contentType(MediaType.APPLICATION_ATOM_XML)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(415, status);
    }

    @Test
    void givenRequestWithoutConvertItToJson_WhenGetResponse_ItsStatusShouldReturnBad() throws Exception {
        orderBookDetailDTO = new OrderBookDetailDTO(7, 2, 6000.0, "jjggjmhk", "9312345674", "400086", "tfjn","girrutannel-0466@yopmail.com", "fgbhjn tgyuhj", "cfgvhbj", "gvbhjnmk", "vghjnkml");
        OrderBookDetail orderBookDetail = new OrderBookDetail(orderBookDetailDTO);
        when(orderBookDetailService.addOrderBookSummary(any())).thenReturn(123456);
        MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/customerbookdetails").content(String.valueOf(orderBookDetail))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    void givenRequestToController_WhenWrongRequestData_thenShouldThrowException() throws Exception {
        try {
            orderBookDetailDTO = new OrderBookDetailDTO(7, 1, 6000.0, "aaaa", "5234543212", "123456", "aaaa","girrutannel-0466@yopmail.com", "aaaaaaaa", "aaaaa", "aaaaaa", "aaaa");
            String jsonDto = gson.toJson(orderBookDetailDTO);
            when(orderBookDetailService.addOrderBookSummary(any())).thenThrow(new BookStoreException(BookStoreException.ExceptionType.INVALID_DATA, "Invalid Data"));
            MvcResult mvcResult = this.mockMvc.perform(post("/bookstore/customerbookdetails").content(jsonDto)
                    .contentType(MediaType.APPLICATION_JSON)).andReturn();
        } catch (BookStoreException e) {
            Assert.assertEquals(BookStoreException.ExceptionType.INVALID_DATA, e.type);
        }
    }
}