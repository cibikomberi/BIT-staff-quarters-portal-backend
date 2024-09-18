package com.bitsathy.quarters.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Handler extends Users {

    @JsonIgnore
    @OneToMany(mappedBy = "assignedTo")
    private List<Compliant> compliants;

    private Integer activeCount = 0;
    private String category;
}
