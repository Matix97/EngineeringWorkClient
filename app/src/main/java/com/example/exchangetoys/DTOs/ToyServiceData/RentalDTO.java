package com.example.exchangetoys.DTOs.ToyServiceData;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
    private Date rentDate;
    private Date suggestedReturnDate;
    //private Date returnDate;
    private ArrayList<String> photos;
  //  private String currentOwner; in token
    private String futureHolder;
    private Long toyIdToTransaction;
    private Long secondToyIdToTransaction;//must when exchange
   // private Integer ifReturned;//1 if yes, 0 if no
    private String typOfTransaction;//exchange(wymiana), commitment(oddanie), rental(wypożyczenie), {timeExchange, endlessExchange, moneyCommitment, freeCommitment, moneyTimeRental, freeTimeRental}
    private Double money;

}
