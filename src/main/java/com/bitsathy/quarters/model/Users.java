package com.bitsathy.quarters.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class Users {
    @Id
    String id;
    String username;
    String password;
}
