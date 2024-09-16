package com.bitsathy.quarters.model;

import java.util.List;

// import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "faculty")
public class Faculty extends Users{

    private String facultyId;
    private String department;
    private String designation;
    private String quartersNo;
    private String address;
    private Long aadhar;

    @JsonIgnore
    @OneToMany(mappedBy = "issuedBy", fetch = FetchType.LAZY)
    private List<Compliant> compliants;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Guest> guests;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
    private List<Innmate> innmates;
}
