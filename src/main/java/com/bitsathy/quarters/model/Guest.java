package com.bitsathy.quarters.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String place;

    private Date fromDate;
    private Date toDate;

    @ManyToOne()
    @JoinColumn(name = "faculty", nullable = false)
    private Faculty faculty;
}
