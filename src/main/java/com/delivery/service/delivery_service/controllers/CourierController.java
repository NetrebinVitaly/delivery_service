package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.UpdateOrderStatusRequest;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.services.DefaultOrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api")
public class CourierController {
    DefaultOrderService defaultOrderService;

    @Operation(summary = "Get all available orders")
    @GetMapping("/courier-av")
    public ResponseEntity<List<OrderEntity>> getAllAvailableOrders() {
        return new ResponseEntity<>(defaultOrderService.getAllOrdersWhereCourierIdNull(), HttpStatus.OK);
    }

    @Operation(summary = "Get order description by id")
    @GetMapping("/courier/order/{id}")
    public ResponseEntity<String> getOrderDescription(@PathVariable Long id) {
        return new ResponseEntity<>(defaultOrderService.showDescription(id), HttpStatus.OK);
    }

    @Operation(summary = "Change order status")
    @PatchMapping("/courier/order")
    public ResponseEntity<OrderEntity> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        return new ResponseEntity<>(defaultOrderService.updateOrderStatus(request), HttpStatus.OK);
    }

    @Operation(summary = "Select order by order_id")
    @PatchMapping("/courier/order/{id}")
    public ResponseEntity<OrderEntity> selectOrder(@PathVariable Long id){
        return new ResponseEntity<>(defaultOrderService.updateOrderCourier(id), HttpStatus.OK);
    }

    @Operation(summary = "Get all nominated orders")
    @GetMapping("/courier-nom")
    public ResponseEntity<List<OrderEntity>> getAllNominatedOrders() {
        return new ResponseEntity<>(defaultOrderService.getAllCourierOrders(), HttpStatus.OK);
    }

}
