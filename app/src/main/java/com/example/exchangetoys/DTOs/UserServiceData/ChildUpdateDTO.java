package com.example.exchangetoys.DTOs.UserServiceData;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ChildUpdateDTO {

    private String child_login;
    private int child_radius_area;
    private ArrayList<String > availableAge;
    private String availableTag;

}
