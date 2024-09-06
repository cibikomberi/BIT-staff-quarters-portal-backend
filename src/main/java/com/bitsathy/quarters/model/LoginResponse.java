package com.bitsathy.quarters.model;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    public String id;
    public String username;
    public String token;
    public String roles;
}
