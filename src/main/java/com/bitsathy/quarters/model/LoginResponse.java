package com.bitsathy.quarters.model;


import java.util.Collection;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginResponse {
    public Long id;
    public String username;
    public String name;
    public String token;
    public Collection<?> roles;
}
