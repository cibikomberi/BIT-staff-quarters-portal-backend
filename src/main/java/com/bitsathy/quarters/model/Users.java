package com.bitsathy.quarters.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq",initialValue = 1,allocationSize = 1)
    Long id;
    String username;
    String name;

    @JsonIgnore
    String password;
    String designation;
    String email;
    Long phone;

    String roles;

    @JsonIgnore
    private String imageName;

    @JsonIgnore
    private String imageType;
    
    @Lob
    @JsonIgnore
    private byte[] profileImage;
}
