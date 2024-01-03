package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.OrderDto;
import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.services.OrderService;
import com.delivery.service.delivery_service.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {
    UserService userService;
    OrderService orderService;


    @Operation(summary = "Register new client")
    @PostMapping("/client")
    ResponseEntity<UserEntity> createClient(@RequestBody UserDto dto){
        return new ResponseEntity<>(userService.registration(dto), HttpStatus.OK);
    }

    @Operation(summary = "Create new order")
    @PostMapping("/client/order")
    public ResponseEntity<OrderEntity> createNewOrder(@RequestBody OrderDto dto) {
        return new ResponseEntity<>(orderService.create(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Change delivery address")
    @PatchMapping("client/order/{id}/{address}")
    public ResponseEntity<OrderEntity>
    updateAddressByOrderId(@PathVariable Long id,
                           @PathVariable String address) {
        return new ResponseEntity<>(orderService.updateOrderAddress(id, address)
                , HttpStatus.OK);
    }

    @Operation(summary = "Get order description by id")
    @GetMapping("/client/order/{id}")
    public ResponseEntity<String> getOrderDescription(@PathVariable Long id) {
        return new ResponseEntity<>(orderService.showDescription(id), HttpStatus.OK);
    }


}
