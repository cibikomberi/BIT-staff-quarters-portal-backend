package com.bitsathy.quarters.model;

// import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "faculty")
public class Faculty {
    @Id
    private String id;
    private String name;
    private String department;
    private String email;
    private Long phone;
    private String designation;
    private String quartersNo;
    private String address;
    private Long aadhar;

    // List<Integer> compliants;
    // List<Integer> innmates;
    // List<Integer> guests;
}
