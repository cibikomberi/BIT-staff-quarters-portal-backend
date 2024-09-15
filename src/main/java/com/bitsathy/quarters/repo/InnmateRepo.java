package com.bitsathy.quarters.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Innmate;


@Repository
public interface InnmateRepo extends JpaRepository<Innmate, Integer>{
//    List<Innmate> findByUsername(String username);

//    @Query("SELECT i FROM Innmate i WHERE LOWER(i.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.relation) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.bloodGroup) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    List<Innmate> searchInnmates(String keyword);
}
