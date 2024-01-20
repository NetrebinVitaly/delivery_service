package com.delivery.service.delivery_service.dto;

import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusRequest {

    @NotNull
    private Long orderId;

    @Schema(description = "Order status", example = "PROCESSED")
    @NotBlank(message = "Field cannot be empty")
    private OrderStatus status;

}
