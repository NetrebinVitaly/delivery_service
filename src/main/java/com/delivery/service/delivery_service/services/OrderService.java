package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.OrderDto;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.exceptions.NotFoundException;
import com.delivery.service.delivery_service.repositories.OrderRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;

    public OrderEntity create(OrderDto dto) {
        return orderRepository.save(OrderEntity
                .builder()
                .id(dto.getId())
                .address(dto.getAddress())
                .description(dto.getDescription())
                .status(OrderStatus.PROCESSED)
                .clientId(dto.getClientId())
                .build());
    }

    public OrderEntity updateOrderStatus(Long orderId, OrderStatus status) {
        if(!orderRepository.findById(orderId).isPresent()){
            throw new NotFoundException("Order not found");
        }
        OrderEntity entity = orderRepository.findById(orderId).get();
        entity.setStatus(status);
        return orderRepository.save(entity);
    }

    public OrderEntity updateOrderAddress(Long orderId, String address) {
        if(!orderRepository.findById(orderId).isPresent()){
            throw new NotFoundException("Order not found");
        }
        OrderEntity entity = orderRepository.findById(orderId).get();
        entity.setAddress(address);
        return orderRepository.save(entity);
    }

    public String showDescription(Long id) {
        if(!orderRepository.findById(id).isPresent()){
            throw new NotFoundException("Order not found");
        }
        return orderRepository.findById(id).get().getDescription();
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getAllOrdersWhereCourierIdNull() {
        return orderRepository.findAll().stream().filter(orderEntity -> orderEntity.getCourierId() == null || orderEntity.getCourierId() == 0).toList();
    }

}
