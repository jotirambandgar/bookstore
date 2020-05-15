package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.OrderBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderBookDetailRepository extends JpaRepository<OrderBookDetail ,Integer> {

    Optional<OrderBookDetail> findByOrderId(int orderId);
}
