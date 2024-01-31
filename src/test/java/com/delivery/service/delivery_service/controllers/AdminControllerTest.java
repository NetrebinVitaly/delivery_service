package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.UpdateOrderStatusRequest;
import com.delivery.service.delivery_service.dto.UserDto;
import com.delivery.service.delivery_service.entities.UserEntity;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.entities.enums.Role;
import com.delivery.service.delivery_service.services.OrderService;
import com.delivery.service.delivery_service.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private UserService service;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private AdministratorController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /api/admin Return status is OK if content type JSON")
    public void getAll_ReturnStatusOk() throws Exception {
        when(service.getAll()).thenReturn(List.of(new UserEntity("user", "user@gmail.com", "pass", Role.CLIENT)));
        mockMvc.perform(get("/api/admin")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("GET /api/admin/ Return HTTP response status is OK if content type JSON ")
    public void findAdminByLogin_ReturnStatusOk() throws Exception {
        when(service.getBy("admin")).thenReturn(new UserEntity());
        mockMvc.perform(get("/api/admin/admin")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("POST /api/admin/ADMIN Return HTTP response status is CREATED")
    public void createUser_ReturnStatusCreated() throws Exception {
        var user = new UserDto("testUser", "user@gmail.com", "pass");
        String json = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/api/admin/ADMIN")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /api/admin/orders Return HTTP response status is OK if content type JSON")
    public void getAllOrders_ReturnStatusOk() throws Exception {
        when(orderService.getAll()).thenReturn(List.of());
        mockMvc.perform(get("/api/admin/orders")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("GET /api/admin/orders Return HTTP response status is OK if content type JSON")
    public void getOrderDescription_ReturnStatusOk() throws Exception {
        when(orderService.showDescription(1L)).thenReturn("TestDescription");
        mockMvc.perform(get("/api/admin/order/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/admin/orders Return HTTP response status is OK")
    public void updateOrderStatus_ReturnStatusOk() throws Exception {
        var request = new UpdateOrderStatusRequest(1L, OrderStatus.ACCEPTED);
        var json = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(patch("/api/admin/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}
