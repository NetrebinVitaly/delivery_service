package com.delivery.service.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderAddressRequest {
    @NotNull
    private Long id;

    @Schema(description = "Order delivery address", example = "Krasnodar, st. Krasnaya d22")
    @NotBlank(message = "Field cannot be empty")
    private String address;
}
