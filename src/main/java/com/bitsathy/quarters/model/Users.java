package com.bitsathy.quarters.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
