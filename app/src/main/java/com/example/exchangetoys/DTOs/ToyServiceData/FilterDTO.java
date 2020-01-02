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
    private Boolean isDidactic;
    private Boolean isVintage;
    private double latitude;
    private double longitude;
    private Integer radius;
}
