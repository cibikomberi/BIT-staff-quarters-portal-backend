package com.bitsathy.quarters.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bitsathy.quarters.model.Handler;
import java.util.List;

@Repository
public interface HandlerRepo extends JpaRepository<Handler, Long> {
    List<Handler> findByCategory(String category);
}
