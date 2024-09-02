package com.bitsathy.quarters.model;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    public String id;
    public String username;
    public String token;
    public List<String> roles;
}
