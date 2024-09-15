package com.bitsathy.quarters.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Handler extends Users {

    @OneToMany(mappedBy = "assignedTo")
    private List<Compliant> compliants;
    private Integer activeCount;
    private String category;
}
