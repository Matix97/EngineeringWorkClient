package com.example.exchangetoys.DTOs.UserServiceData;

import com.example.exchangetoys.DTOs.ToyServiceData.SuggestedToy;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Adult {
    private String name;
    private String surname;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    //address                   //na start musimy wybrać gdzie mieszkamy
    private double latitude;
    private double longitude;

    private ArrayList<Child> childList;
    private ArrayList<Integer> yourAdvert;//integer to keyOfToy
    private ArrayList<Integer> yourRentedToys;
    private ArrayList<SuggestedToy> childSuggestions;

}
