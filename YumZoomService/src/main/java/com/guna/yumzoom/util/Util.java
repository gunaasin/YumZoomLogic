package com.guna.yumzoom.util;

import com.guna.yumzoom.order.OrderStatus;

public class Util {

    public static boolean isValidOrderStatus(String status) {
        try {
            OrderStatus.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
