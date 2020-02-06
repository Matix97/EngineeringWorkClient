package com.example.exchangetoys.DTOs.UserServiceData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Adult {
//    private String name;
//    private String surname;
//    private String password;
//    private String phoneNumber;
//    private String emailAddress;
//    //address                   //na start musimy wybrać gdzie mieszkamy
//    private double latitude;
//    private double longitude;
//
//    private ArrayList<Child> childList;
//    private ArrayList<Integer> yourAdvert;//integer to keyOfToy
//    private ArrayList<Integer> yourRentedToys;
//    private ArrayList<SuggestedToy> childSuggestions;

    private Long adult_id;

    private String adult_name;

    private String adult_surname;

    private String adult_password;

    private String adult_phone_number;

    private String adult_email_address;

    private String adult_suggested_toys_list;
}
