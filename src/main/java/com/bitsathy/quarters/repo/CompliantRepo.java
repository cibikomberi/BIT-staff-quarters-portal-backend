package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Compliant;

@Repository
public interface CompliantRepo extends JpaRepository<Compliant, Long> {

    List<Compliant> findByIssuedBy_Id(Long id);

    List<Compliant> findByAssignedTo_Id(Long assignedTo);

    @Query("SELECT c FROM Compliant c JOIN c.issuedBy f JOIN c.assignedTo h WHERE LOWER(c.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(h.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(c.id AS string)  LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Compliant> searchCompliants(String keyword);

    @Query("SELECT c FROM Compliant c JOIN c.issuedBy f JOIN c.assignedTo h WHERE h.id = :id AND (LOWER(c.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(f.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR CAST(c.id AS string)  LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Compliant> searchHandlerCompliants(String keyword, Long id);

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE DATE(issued_on) = CURRENT_DATE", nativeQuery = true)
    Long countIssuedComplaintsToday();

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE status = 'Initiated' or status = 'Ongoing'", nativeQuery = true)
    Long countPendingComplaints();

    @Query(value = "SELECT COUNT(*) FROM compliant WHERE DATE(resolved_on) = CURRENT_DATE", nativeQuery = true)
    Long countResolvedComplaintsToday();

    @Query("SELECT count(c) FROM Compliant c JOIN c.assignedTo h WHERE h.id = :id")
    Long countIssuedComplaintsHandler(Long id);

    @Query("SELECT count(c) FROM Compliant c JOIN c.assignedTo h WHERE h.id = :id AND c.status = 'Issued'")
    Long countPendingComplaintsHandler(Long id);

    @Query("SELECT count(c) FROM Compliant c JOIN c.assignedTo h WHERE h.id = :id AND c.status = 'Resolved'")
    Long countResolvedComplaintsHandler(Long id);
}
