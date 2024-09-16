package com.bitsathy.quarters.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Guest;

import java.util.List;


@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {
    List<Guest> findByFaculty_Id(Long id);
}
