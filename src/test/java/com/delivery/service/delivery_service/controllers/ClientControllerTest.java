package com.delivery.service.delivery_service.controllers;

import com.delivery.service.delivery_service.dto.OrderDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @InjectMocks
    private ClientController controller;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("POST /api/client/order Return status is CREATED")
    void createNewOrder_ReturnStatusCreated() throws Exception {
        var order = new OrderDto("Some food", "Krasnodar");
        String json = new ObjectMapper().writeValueAsString(order);
        mockMvc.perform(post("/api/client/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PATCH /api/client/order Return status is Ok if content type is JSON")
    void updateAddressByOrderId_ReturnStatusOk() throws Exception {
        var request = new UpdateOrderStatusRequest(1L, OrderStatus.ACCEPTED);
        var json = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(patch("/api/client/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/client/order/ Return status is Ok")
    void getOrderDescription() throws Exception {
        mockMvc.perform(get("/api/client/order/")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/client/order Return status is Ok if content type is JSON")
    void getAllOrders() throws Exception {
        mockMvc.perform(get("/api/client/order")).andExpectAll(
                status().isOk(),
                content().contentType(MediaType.APPLICATION_JSON)
        );
    }

    @Test
    @DisplayName("DELETE /api/client/order/ Return status is Ok")
    void deleteOrder() throws Exception {
        mockMvc.perform(delete("/api/client/order/1")).andExpect(status().isOk());
    }
}