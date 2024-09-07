package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Compliant;



@Repository
public interface CompliantRepo extends JpaRepository<Compliant, Integer>{
    List<Compliant> findByIssuedBy(String issuedBy);
}
