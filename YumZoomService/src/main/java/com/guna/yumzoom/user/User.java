package com.guna.yumzoom.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guna.yumzoom.address.Address;
import com.guna.yumzoom.order.Order;
import com.guna.yumzoom.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    private String password;

    @Column(nullable = false,
            unique = true)
    private String email;

    @Column(nullable = false,
            unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false,
            updatable = false)
    private String createdDate;
    private String lastLoginDate;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Order> Orders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id" , referencedColumnName = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;

    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Restaurant restaurant;
}
