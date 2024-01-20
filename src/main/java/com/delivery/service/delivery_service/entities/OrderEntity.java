package com.delivery.service.delivery_service.entities;


import com.delivery.service.delivery_service.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders",
        indexes = {
                @Index(columnList = "order_id", name = "order_id_index"),
                @Index(columnList = "client", name = "client_index"),
                @Index(columnList = "courier", name = "courier_index")})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    Long id;

    @Basic
    @Column(name = "order_status")
    OrderStatus status;


    @Basic
    @Column(name = "description", length = 500)
    String description;

    @Basic
    @Column(name = "order_address", nullable = false)
    String address;

    @ManyToOne
    @JoinColumn(name = "courier", referencedColumnName = "login")
    @JsonIgnoreProperties(value = {"login", "password", "roles"})
    UserEntity courier;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "login")
    @JsonIgnoreProperties(value = {"login", "password", "roles"})
    UserEntity client;

}
