package com.delivery.service.delivery_service.repositories;


import com.delivery.service.delivery_service.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository
        extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long id);
    List<OrderEntity> findAllByClientId(Long id);

    List<OrderEntity> findAllByCourierId(Long id);
}
