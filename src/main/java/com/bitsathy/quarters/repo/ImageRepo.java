package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long>{
    
}