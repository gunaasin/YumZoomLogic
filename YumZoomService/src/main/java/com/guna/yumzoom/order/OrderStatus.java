package com.guna.yumzoom.order;

public enum OrderStatus {
    PENDING,       // Order placed but not yet processed
    CONFIRMED,     // Order confirmed by the restaurant
    PREPARING,     // Order is being prepared
    READY,         // Order is ready for pickup/delivery
    OUT_FOR_DELIVERY, // Order is on its way
    DELIVERED,     // Order has been delivered
    CANCELLED;     // Order was cancelled
}