package com.bitsathy.quarters.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compliant")
public class Compliant {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int compliantId;
    private String category;
    private String title;
    private String description;
    private String status;

    private String issuedBy;
    private LocalDateTime issuedOn;
    private LocalDateTime resolvedOn;
    private String availableTime;
    private String assignedTo;
}