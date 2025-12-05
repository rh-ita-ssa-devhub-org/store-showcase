package com.example.combi.demo.repository;
import java.util.List;
import java.util.Optional;

import com.example.combi.demo.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAll();

    Optional<OrderEntity> findById(Long Id);
}
