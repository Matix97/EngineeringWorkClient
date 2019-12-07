package com.example.exchangetoys.DTOs.ToyServiceData;

import com.example.exchangetoys.DTOs.UserServiceData.Child;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuggestedToy {
    Child child;
    Toy toy;
}
