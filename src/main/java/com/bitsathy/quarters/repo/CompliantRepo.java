package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Compliant;


@Repository
public interface CompliantRepo extends JpaRepository<Compliant, Integer>{
    
}