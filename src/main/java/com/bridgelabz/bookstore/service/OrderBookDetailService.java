package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookDetails;
import com.bridgelabz.bookstore.model.OrderBookDetail;
import com.bridgelabz.bookstore.repository.BookStoreRepository;
import com.bridgelabz.bookstore.repository.OrderBookDetailRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

@Service
public class OrderBookDetailService implements IOrderBookDetailService {

    @Autowired
    OrderBookDetailRepository orderBookDetailRepository;

    @Autowired
    BookStoreRepository bookStoreRepository;

    @Autowired
    JavaMailSender javaMailSender;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public int addOrderBookSummary(OrderBookDetailDTO... orderBookDetailDTO) {
        int orderId = getOrderId();
        Arrays.stream(orderBookDetailDTO).forEach(value -> {
            Optional<BookDetails> byId = bookStoreRepository.findById(value.bookIds);
            if (value.noOfCopies > byId.get().noOfCopies)
                throw new BookStoreException(BookStoreException.ExceptionType.ORDER_QUANTITY_GREATER_THEN_STOCK, "Order quantity is greater then stock");
        });
        List<OrderBookDetail> collect = Arrays.stream(orderBookDetailDTO).map(value -> {
            OrderBookDetail orderBookDetail = new OrderBookDetail(value);
            orderBookDetail.orderId = orderId;
            OrderBookDetail bookDetail = orderBookDetailRepository.save(orderBookDetail);
            return bookDetail;
        }).collect(Collectors.toList());
        updateStock(collect);
        try {
            sendEmail(collect);
        } catch (MessagingException e) {
            throw new BookStoreException(BookStoreException.ExceptionType.AUTHENTICATION_ERROR, "Authentication_Error");
        }
        return collect.get(0).orderId;
    }

    private int getOrderId() {
        boolean unique = false;
        int orderId = 0;
        while (!unique) {
            orderId = (int) Math.floor(100000 + Math.random() * 900000);
            Optional<OrderBookDetail> byId = orderBookDetailRepository.findByOrderId(orderId);
            if (byId.isEmpty())
                unique = true;
        }
        return orderId;
    }

    private void updateStock(List<OrderBookDetail> orderBookDetail) {
        orderBookDetail.stream().forEach(value -> {
            bookStoreRepository.updateStock(value.noOfCopies, value.bookIds);
        });
    }

    private void sendEmail(List<OrderBookDetail> orderBookDetail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(orderBookDetail.get(0).mailId);
        helper.setText("Dear " + orderBookDetail.get(0).customerName + ",\nCongratulations! Your order for the books is Successfully Placed. \nYour order id is "
                + orderBookDetail.get(0).orderId + ".\nYou can use this order id to track the order");
        helper.setSubject("BookStore Order Placed");
        executorService.submit(() -> {
            try {
                javaMailSender.send(message);
            } catch (Exception e) {
                throw new BookStoreException(BookStoreException.ExceptionType.INTERNAL_SERVER_ERROR,"INTERNAL_SERVER_ERROR");
            }
        });
    }
}
