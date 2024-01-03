package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.services.OrderService;
import com.delivery.service.delivery_service.services.UserService;
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
public class AdministratorController {

    UserService service;
    OrderService orderService;

    @Operation(summary = "Creating user")
    @PostMapping("/admin/{role}")
    public ResponseEntity<UserEntity> createAdmin(@RequestBody UserDto dto,
                                                  @PathVariable Role role){
        return new ResponseEntity<>(service.createUser(dto, role), HttpStatus.OK);
    }

    @Operation(summary = "find admin by login")
    @GetMapping("/admin/{login}")
    public ResponseEntity<UserEntity> findAdminByLogin(@PathVariable String login) {
        return new ResponseEntity<>(service.getUserByLogin(login), HttpStatus.OK);
    }

    @Operation(summary = "Get all users")
    @GetMapping("/admin")
    public ResponseEntity<List<UserEntity>> getAll() {
        return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/admin/orders")
    public ResponseEntity<List<OrderEntity>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @Operation(summary = "Get order description by id")
    @GetMapping("/admin/order/{id}")
    public ResponseEntity<String> getOrderDescription(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.showDescription(id), HttpStatus.OK);
    }

    @Operation(summary = "Change order status")
    @PatchMapping("/admin/order/{id}/{status}")
    public ResponseEntity<OrderEntity> updateOrderStatus(@PathVariable Long id,
                                                         @PathVariable OrderStatus status) {
        return new ResponseEntity<>(orderService.updateOrderStatus(id, status), HttpStatus.OK);
    }


}
