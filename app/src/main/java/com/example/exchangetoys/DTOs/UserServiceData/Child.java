package com.example.exchangetoys.DTOs.UserServiceData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Child {
    private Long child_id;
    private String child_parent_id;//generally it's parent's email
    private String child_name;
    private String child_login;
    private String child_password;
    private int child_age;
    private int child_radius_area;
    private double child_latitude;
    private double child_longitude;

}
