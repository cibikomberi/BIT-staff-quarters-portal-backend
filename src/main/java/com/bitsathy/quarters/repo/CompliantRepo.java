package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Compliant;

@Repository
public interface CompliantRepo extends JpaRepository<Compliant, Integer> {

    List<Compliant> findByIssuedBy(String issuedBy);

    @Query("SELECT c FROM Compliant c WHERE LOWER(c.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.issuedBy) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.assignedTo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(c.compliantId AS string)  LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Compliant> searchCompliants(String keyword);

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE DATE(issued_on) = CURRENT_DATE", nativeQuery = true)
    Long countIssuedComplaintsToday();

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE status = 'Initiated' or status = 'Pending'", nativeQuery = true)
    Long countPendingComplaints();

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE DATE(resolved_on) = CURRENT_DATE", nativeQuery = true)
    Long countResolvedComplaintsToday();
}
