package com.delivery.service.delivery_service.repositories;


import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository
        extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findById(Long id);
    Optional<List<OrderEntity>> findAllByCourier(UserEntity courier);
    Optional<List<OrderEntity>> findAllByClient(UserEntity client);

}
