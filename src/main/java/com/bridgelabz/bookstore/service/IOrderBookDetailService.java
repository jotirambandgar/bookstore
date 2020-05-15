package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;

public interface IOrderBookDetailService {

    int addOrderBookSummary(OrderBookDetailDTO... orderBookDetailDTO);

}
