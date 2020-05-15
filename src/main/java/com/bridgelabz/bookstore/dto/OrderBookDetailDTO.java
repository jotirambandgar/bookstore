package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
public class OrderBookDetailDTO {

    @NotNull
    public int bookIds;

    @NotNull
    public int noOfCopies;

    @NotNull
    public Double orderPrice;

    @NotNull
    @Pattern(regexp ="^[a-zA-Z]{3,20}$",message = "Enter Valid Name")
    public String customerName;

    @NotNull
    @Pattern(regexp = "^[5-9]{1}[0-9]{9}$",message = "Enter Valid Mobile Number")
    public String mobileNo;

    @NotNull
    @Pattern(regexp = "^[0-9]{6}$",message = "Enter Valid Pincode")
    public String pincode;

    @NotNull
    @Pattern(regexp = "^[a-zA-z0-9]+$", message = "Enter Valid Locality")
    public String locality;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    public String mailId;

    @NotNull
    public String address;

    @NotNull
    @Pattern(regexp = "^[a-zA-z]+$", message = "Enter Valid City")
    public String city;

    @NotNull
    @Pattern(regexp ="^[a-zA-Z0-9]+$",message = "Enter Valid LandMark")
    public String town;

    public String type;
}
