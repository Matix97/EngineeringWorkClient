package com.example.exchangetoys.DTOs.UserServiceData;

import lombok.Data;

@Data
public class ChildUpdateDTO {

    private String child_login;
    private int child_radius_area;
    private String availableAge;
    private String availableTag;
    private Integer amount;

}
