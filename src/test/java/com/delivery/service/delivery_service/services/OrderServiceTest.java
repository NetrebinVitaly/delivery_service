package com.delivery.service.delivery_service.services;

import com.delivery.service.delivery_service.dto.OrderDto;
import com.delivery.service.delivery_service.dto.UpdateOrderAddressRequest;
import com.delivery.service.delivery_service.dto.UpdateOrderStatusRequest;
import com.delivery.service.delivery_service.entities.OrderEntity;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.repositories.OrderRepository;
import com.delivery.service.delivery_service.repositories.UserRepository;
import com.delivery.service.delivery_service.utils.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext context;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ValidationUtil validationUtil;

    @InjectMocks
    OrderService service;

    @BeforeEach
    public void setup() {
        SecurityContextHolder.setContext(context);
    }

    @Test
    void testCreateOrder() {
        var dto = new OrderDto();
        var client = new UserEntity();
        var order = new OrderEntity(null, OrderStatus.PROCESSED, null, null, null, client);

        when(orderRepository.save(order))
                .thenReturn(new OrderEntity(null, OrderStatus.PROCESSED, null, null, null, client));
        when(userRepository.findByLogin("testUser"))
                .thenReturn(Optional.of(client));
        when(SecurityContextHolder.getContext().getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("testUser");

        var result = service.create(dto);
        assertEquals(order, result);

        verify(validationUtil, times(1))
                .isValid(dto);
        verify(userRepository, times(1))
                .findByLogin("testUser");
        verify(orderRepository, times(1))
                .save(any());
    }

    @Test
    void updateOrderStatus_ReturnOrderEntity() {
        var request = new UpdateOrderStatusRequest(1L, OrderStatus.ACCEPTED);
        var orderBefore = OrderEntity
                .builder()
                .status(OrderStatus.PROCESSED)
                .build();
        var orderAfter = OrderEntity
                .builder()
                .status(request.getStatus())
                .build();

        when(orderRepository.findById(request.getOrderId()))
                .thenReturn(Optional.of(orderBefore));

        when(orderRepository.save(orderAfter))
                .thenReturn(orderAfter);

        var result = service.updateOrderStatus(request);

        verify(validationUtil, times(1))
                .isValid(request);
        verify(orderRepository, times(1))
                .findById(request.getOrderId());
        verify(orderRepository, times(1))
                .save(any());
        assertEquals(result, orderAfter);
    }

    @Test
    void updateOrderAddress_ReturnOrderEntity() {
        var request = new UpdateOrderAddressRequest(1L, "AfterTestAddress");
        var orderBefore = OrderEntity
                .builder()
                .address("BeforeTestAddress")
                .build();
        var orderAfter = OrderEntity
                .builder()
                .address(request.getAddress())
                .build();

        when(orderRepository.findById(request.getId()))
                .thenReturn(Optional.of(orderBefore));

        when(orderRepository.save(orderAfter))
                .thenReturn(orderAfter);

        var result = service.updateOrderAddress(request);

        verify(validationUtil, times(1))
                .isValid(request);
        verify(orderRepository, times(1))
                .findById(request.getId());
        verify(orderRepository, times(1))
                .save(any());
        assertEquals(result, orderAfter);
    }

    @Test
    void updateOrderCourierTest_ReturnOrderEntity() {
        var orderId = 1l;
        var order = OrderEntity
                .builder()
                .id(orderId)
                .build();
        var courier = UserEntity
                .builder()
                .login("testUser")
                .build();
        var orderAfter = OrderEntity
                .builder()
                .id(orderId)
                .courier(courier)
                .build();

        when(SecurityContextHolder.getContext().getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("testUser");
        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));
        when(userRepository.findByLogin(authentication.getName()))
                .thenReturn(Optional.of(courier));
        when(orderRepository.save(orderAfter))
                .thenReturn(orderAfter);

        var result = service.updateOrderCourier(orderId);

        assertEquals(orderAfter, result);
        verify(orderRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findByLogin("testUser");
        verify(orderRepository, times(1)).save(orderAfter);


    }

    @Test
    void deleteByOrderId() {
        var orderId = 1l;
        var order = OrderEntity.builder()
                .id(orderId)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(order));

        service.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);

    }

    @Test
    void showDescription_ReturnString() {

        var orderId = 1L;

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(OrderEntity
                        .builder()
                        .id(orderId)
                        .description("TestDescription")
                        .build()));

        var result = service.showDescription(orderId);

        verify(orderRepository, times(1)).findById(orderId);
        assertNotNull(result);
        assertEquals("TestDescription", result);
    }

    @Test
    void getAll_ReturnListOrders() {
        var ordersList = List.of(
                OrderEntity.builder().id(1L).build(),
                OrderEntity.builder().id(2L).build(),
                OrderEntity.builder().id(3L).build()
        );

        when(orderRepository.findAll())
                .thenReturn(List.of(
                        OrderEntity.builder().id(1L).build(),
                        OrderEntity.builder().id(2L).build(),
                        OrderEntity.builder().id(3L).build()
                ));
        var result = service.getAllOrders();

        assertNotNull(result);
        assertEquals(ordersList, result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getAllClientOrders_ReturnListOrderEntities() {
        var client = UserEntity.builder().login("testUser").build();
        var listOrders = List.of(
                OrderEntity.builder().id(1L).client(client).build(),
                OrderEntity.builder().id(2L).client(client).build(),
                OrderEntity.builder().id(3L).client(client).build()
        );

        when(SecurityContextHolder.getContext().getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("testUser");
        when(userRepository.findByLogin(authentication.getName()))
                .thenReturn(Optional.of(client));
        when(orderRepository.findAllByClient(client))
                .thenReturn(Optional.of(List.of(
                        OrderEntity.builder().id(1L).client(client).build(),
                        OrderEntity.builder().id(2L).client(client).build(),
                        OrderEntity.builder().id(3L).client(client).build()
                )));

        var result = service.getAllClientOrders();
        assertNotNull(result);
        assertEquals(listOrders, result);
        assertEquals(result.get(1).getClient(), client);
        verify(orderRepository, times(1)).findAllByClient(client);
    }

    @Test
    void getAllCourierOrders() {
        var courier = UserEntity.builder().login("testUser").build();
        var listOrders = List.of(
                OrderEntity.builder().id(1L).courier(courier).build(),
                OrderEntity.builder().id(2L).courier(courier).build(),
                OrderEntity.builder().id(3L).courier(courier).build()
        );

        when(SecurityContextHolder.getContext().getAuthentication())
                .thenReturn(authentication);
        when(authentication.getName())
                .thenReturn("testUser");
        when(userRepository.findByLogin(authentication.getName()))
                .thenReturn(Optional.of(courier));
        when(orderRepository.findAllByCourier(courier))
                .thenReturn(Optional.of(List.of(
                        OrderEntity.builder().id(1L).courier(courier).build(),
                        OrderEntity.builder().id(2L).courier(courier).build(),
                        OrderEntity.builder().id(3L).courier(courier).build()
                )));

        var result = service.getAllCourierOrders();
        assertNotNull(result);
        assertEquals(result, listOrders);
        assertEquals(result.get(1).getCourier(), courier);
        verify(orderRepository, times(1)).findAllByCourier(courier);
    }

    @Test
    void getAllOrdersWhereCourierIdNull() {
        var listOrders = List.of(
                OrderEntity.builder().id(1L).courier(null).build(),
                OrderEntity.builder().id(2L).courier(null).build(),
                OrderEntity.builder().id(3L).courier(null).build()
        );

        when(orderRepository.findAllByCourier(null)).thenReturn(Optional.of(List.of(
                OrderEntity.builder().id(1L).courier(null).build(),
                OrderEntity.builder().id(2L).courier(null).build(),
                OrderEntity.builder().id(3L).courier(null).build()
        )));

        var result = service.getAllOrdersWhereCourierIdNull();

        assertNotNull(result);
        assertEquals(result, listOrders);
        assertNull(result.get(1).getCourier());

        verify(orderRepository, times(1)).findAllByCourier(null);
    }
}