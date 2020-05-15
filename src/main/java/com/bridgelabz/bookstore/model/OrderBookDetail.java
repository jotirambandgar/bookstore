package com.bridgelabz.bookstore.model;


import com.bridgelabz.bookstore.dto.OrderBookDetailDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
public class OrderBookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int orderId;
    public int bookIds;
    public int noOfCopies;
    public Double orderPrice;
    public String customerName;
    public String mobileNo;
    public String pincode;
    public String locality;
    public String address;
    public String mailId;
    public String city;
    public String town;
    public String type;

    public OrderBookDetail(OrderBookDetailDTO orderBookDetailDTO) {
        this.bookIds = orderBookDetailDTO.bookIds;
        this.noOfCopies = orderBookDetailDTO.noOfCopies;
        this.orderPrice = orderBookDetailDTO.orderPrice;
        this.customerName = orderBookDetailDTO.customerName;
        this.mobileNo = orderBookDetailDTO.mobileNo;
        this.pincode = orderBookDetailDTO.pincode;
        this.locality = orderBookDetailDTO.locality;
        this.address = orderBookDetailDTO.address;
        this.mailId = orderBookDetailDTO.mailId;
        this.city = orderBookDetailDTO.city;
        this.town = orderBookDetailDTO.town;
        this.type = orderBookDetailDTO.type;
    }
}
