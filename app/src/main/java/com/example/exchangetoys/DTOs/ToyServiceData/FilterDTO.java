package com.example.exchangetoys.DTOs.ToyServiceData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterDTO {
    private String mainCategory;
    private String age;
    private String tags;
    private String anyKeyword;
    private boolean isDidactic;
    private boolean isVintage;
    private double latitude;
    private double longitude;
}
