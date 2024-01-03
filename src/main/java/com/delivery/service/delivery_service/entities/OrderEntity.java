package com.delivery.service.delivery_service.entities;


import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders", schema = "delivery_schema")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @Basic
    @NotNull
    @Column(name = "order_status")
    OrderStatus status;


    @Basic
    @Column(name = "description", length = 500)
    String description;

    @Basic
    @NotNull
    @Column(name = "order_address", nullable = false)
    String address;

    @Basic
    @Column(name = "courier_id")
    Long courierId;

    @Basic
    @Column(name = "client_id")
    Long clientId;

}
