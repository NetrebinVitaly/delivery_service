package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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
    OrderService orderService;

    @Operation(summary = "Get all available orders")
    @GetMapping("/courier-av")
    public ResponseEntity<List<OrderEntity>> getAllAvailableOrders() {
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

    @Operation(summary = "Select order by order_id")
    @PatchMapping("/courier/order/{id}")
    public ResponseEntity<OrderEntity> selectOrder(@PathVariable Long id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return new ResponseEntity<>(orderService.updateOrderCourier(id, token), HttpStatus.OK);
    }

    @Operation(summary = "Get all nominated orders")
    @GetMapping("/courier-nom")
    public ResponseEntity<List<OrderEntity>> getAllNominatedOrders(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return new ResponseEntity<>(orderService.getAllCourierOrders(token), HttpStatus.OK);
    }

}
