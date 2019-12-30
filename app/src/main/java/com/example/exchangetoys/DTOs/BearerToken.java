package com.example.exchangetoys.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BearerToken {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
}
