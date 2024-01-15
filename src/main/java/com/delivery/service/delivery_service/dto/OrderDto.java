package com.delivery.service.delivery_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDto {
    @Schema(description = "Contents of the order", example = "Product1, Product2")
    @NotBlank(message = "Field cannot be empty")
    String description;

    @Schema(description = "Order delivery address", example = "Krasnodar, st. Krasnaya d22")
    @NotBlank(message = "Field cannot be empty")
    String address;
}