package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.service.OrderBookDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookstore")
@CrossOrigin("*")
public class CustomerOrderDetailController {

    @Autowired
    OrderBookDetailService orderBookDetailService;

    @PostMapping("/customerbookdetails")
    public ResponseEntity<ResponseDTO> addBook(@Valid @RequestBody OrderBookDetailDTO... orderBookDetailDTO) throws BookStoreException {
        int orderBookDetail = orderBookDetailService.addOrderBookSummary(orderBookDetailDTO);
        ResponseDTO responseData = new ResponseDTO("Info Added Successfully", orderBookDetail);
        return new ResponseEntity<ResponseDTO>(responseData, HttpStatus.OK);
    }
}