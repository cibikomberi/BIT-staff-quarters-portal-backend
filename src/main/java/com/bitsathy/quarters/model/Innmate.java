package com.bitsathy.quarters.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "innmate")
public class Innmate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String relation;
    private Integer age;
    private String bloodGroup;
    private Long aadhar;
    private Boolean isWorking;
    private Boolean isStaying;

    @ManyToOne
    @JoinColumn(name = "faculty", nullable = false)
    private Faculty faculty;
}
