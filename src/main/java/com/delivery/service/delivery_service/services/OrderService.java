package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.OrderDto;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.exceptions.NotFoundException;
import com.delivery.service.delivery_service.repositories.OrderRepository;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.utils.ValidationUtil;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    ValidationUtil validationUtil;


    public OrderEntity create(@Valid OrderDto dto) {
        validationUtil.isValid(dto);
        UserEntity client = userRepository.findByLogin(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getName())
                .orElseThrow(() -> new NotFoundException("User not found exception"));
        return orderRepository.save(OrderEntity
                .builder()
                .address(dto.getAddress())
                .description(dto.getDescription())
                .status(OrderStatus.PROCESSED)
                .client(client)
                .build());
    }

    public OrderEntity updateOrderStatus(Long orderId, OrderStatus status) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public OrderEntity updateOrderAddress(Long orderId, String address) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        order.setAddress(address);
        return orderRepository.save(order);
    }

    public OrderEntity updateOrderCourier(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        var courier = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("User not found exception"));
        order.setCourier(courier);
        return orderRepository.save(order);
    }

    public String showDescription(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found")).getDescription();
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<OrderEntity> getAllClientOrders() {
        UserEntity client = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return orderRepository.findAllByClient(client).get();
    }

    public List<OrderEntity> getAllCourierOrders() {
        UserEntity courier = userRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return orderRepository.findAllByCourier(courier).get();
    }

    public List<OrderEntity> getAllOrdersWhereCourierIdNull() {
        return orderRepository.findAllByCourier(null).get();
    }

}
