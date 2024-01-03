package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CourierController {
    OrderService orderService;

    @Operation(summary = "Get all orders")
    @GetMapping("/courier")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrdersWhereCourierIdNull(), HttpStatus.OK);
    }

    @Operation(summary = "Get order description by id")
    @GetMapping("/courier/order/{id}")
    public ResponseEntity<String> getOrderDescription(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.showDescription(id), HttpStatus.OK);
    }

    @Operation(summary = "Change order status")
    @PatchMapping("/courier/order/{id}/{status}")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long id,
                                                         @PathVariable OrderStatus status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(id, status), HttpStatus.OK);
    }
}
