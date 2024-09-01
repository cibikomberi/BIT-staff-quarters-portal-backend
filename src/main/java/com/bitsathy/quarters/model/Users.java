package com.bitsathy.quarters.model;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    String username;
    String password;
    List<String> roles = Arrays.asList("ROLE_USER");
}
