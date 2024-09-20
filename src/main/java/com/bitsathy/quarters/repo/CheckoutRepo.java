package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Checkouts;

@Repository
public interface CheckoutRepo extends JpaRepository<Checkouts, Long> {
    
    // @Query("SELECT com.bitsathy.quarters.model.Checkouts(c.id, c.name, c.type, c.faculty) FROM Checkouts c")
    // List<Checkouts> allCheckoutsWithoutPin();

    List<Checkouts> findByPin(String pin);

    List<Checkouts> findByFaculty_Id(Long id);

    @Query("SELECT c FROM Checkouts c JOIN c.faculty f WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.type) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Checkouts> searchCheckouts(String keyword);
}
