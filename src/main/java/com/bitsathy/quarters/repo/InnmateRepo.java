package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Innmate;


@Repository
public interface InnmateRepo extends JpaRepository<Innmate, Integer>{
    List<Innmate> findByUsername(String username);
}
