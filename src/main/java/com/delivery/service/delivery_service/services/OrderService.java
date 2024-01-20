package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.OrderDto;
import com.delivery.service.delivery_service.dto.UpdateOrderAddressRequest;
import com.delivery.service.delivery_service.dto.UpdateOrderStatusRequest;
import com.delivery.service.delivery_service.entities.OrderEntity;

import java.util.List;

public interface OrderService
        extends Service<OrderEntity, OrderDto, Long> {
    OrderEntity create(OrderDto entity);
    OrderEntity updateOrderStatus(UpdateOrderStatusRequest request);
    OrderEntity updateOrderAddress(UpdateOrderAddressRequest request);
    OrderEntity updateOrderCourier(Long orderId);
    List<OrderEntity> getAllClientOrders();
    List<OrderEntity> getAllCourierOrders();
    List<OrderEntity> getAllOrdersWhereCourierIdNull();
    String showDescription(Long id);
}
