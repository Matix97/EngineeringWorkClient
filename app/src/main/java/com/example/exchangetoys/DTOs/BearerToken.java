package com.example.exchangetoys.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BearerToken {
    private int userID;
    private String expiresAt;
    private String string;
    private int id;
}
