package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, String> {

    Users findByUsername(String username);
    
}
