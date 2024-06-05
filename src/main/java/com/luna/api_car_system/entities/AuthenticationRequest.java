package com.luna.api_car_system.entities;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
