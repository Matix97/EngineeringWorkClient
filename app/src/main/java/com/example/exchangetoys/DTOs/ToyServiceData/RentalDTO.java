package com.example.exchangetoys.DTOs.ToyServiceData;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
   // private Date rentDate;
   // private Date suggestedReturnDate;
   private String suggestedReturnDate;//yyyy-MM-dd
    private ArrayList<String> photos;
    private String futureHolderEmail;
    private String futureHolderPassword;
    private Long toyIdTo;

}
