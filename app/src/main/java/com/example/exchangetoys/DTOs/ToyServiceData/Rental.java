package com.example.exchangetoys.DTOs.ToyServiceData;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rental {
    private Date rentDate;
    private Date suggestedReturnDate;
    private Date returnDate;
    private ArrayList<Integer> photos;
    private String howRentEmail;
}
