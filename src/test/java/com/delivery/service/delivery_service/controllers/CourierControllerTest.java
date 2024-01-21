package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.UpdateOrderStatusRequest;
import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.delivery.service.delivery_service.services.OrderService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CourierControllerTest {
    @Mock
    private OrderService orderService;
    @InjectMocks
    private CourierController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("GET /api/courier-av Return HTTP response status is OK if content type is JSON")
    void getAllAvailableOrders() throws Exception {
        mockMvc.perform(get("/api/courier-av")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("GET /api/courier/order/ Return HTTP response status is OK")
    void getOrderDescription() throws Exception {
        mockMvc.perform(get("/api/courier/order/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/courier/order Return HTTP response status is OK")
    void updateOrderStatus() throws Exception {
        var request = new UpdateOrderStatusRequest(1L, OrderStatus.ACCEPTED);
        var json = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(patch("/api/courier/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/courier/order/ Return HTTP response status is OK")
    void selectOrder() throws Exception {
        mockMvc.perform(patch("/api/courier/order/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/courier-nom Return HTTP response status is OK if content type is JSON")
    void getAllNominatedOrders() throws Exception {
        mockMvc.perform(get("/api/courier-nom")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }
}