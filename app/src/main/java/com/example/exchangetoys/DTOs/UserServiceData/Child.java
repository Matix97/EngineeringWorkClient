package com.example.exchangetoys.DTOs.UserServiceData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Child {
    private String parent;
    private String childName;//must be unique
    private String password;
    private int areaRadius;
}
