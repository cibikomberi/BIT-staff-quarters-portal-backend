package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Faculty;


@Repository
public interface FacultyRepo extends JpaRepository<Faculty, Long>{
    
}
