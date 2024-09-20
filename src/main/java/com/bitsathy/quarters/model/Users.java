package com.bitsathy.quarters.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq",initialValue = 5,allocationSize = 1)
    Long id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String name;

    @JsonIgnore
    String password;
    
    @Column(unique = true, nullable = false)
    String email;
    
    @Column(unique = true, nullable = false)
    Long phone;
    
    @Column(nullable = false)
    String roles;
    
    @Column(nullable = false)
    String designation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "image")
    @JsonIgnore
    Image image;
}
