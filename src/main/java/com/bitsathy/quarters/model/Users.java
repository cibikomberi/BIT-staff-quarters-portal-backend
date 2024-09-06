package com.bitsathy.quarters.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    String name;
    @OneToOne(cascade = CascadeType.ALL)
    Faculty details;
    String password;

    private String roles;
}
