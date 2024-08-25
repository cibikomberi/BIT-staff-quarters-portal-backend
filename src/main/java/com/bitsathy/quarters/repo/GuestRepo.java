package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Guest;


@Repository
public interface GuestRepo extends JpaRepository<Guest, Integer> {
    
}
