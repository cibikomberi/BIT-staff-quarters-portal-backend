package com.bitsathy.quarters.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.bitsathy.quarters.model.Handler;
import java.util.List;


public interface HandlerRepo extends JpaRepository<Handler, Long> {
    List<Handler> findByCategory(String category);
}
