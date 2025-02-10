package com.guna.yumzoom.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderIdGen {
    public static String generateOrderIdHybrid() {
        String timestamp = new SimpleDateFormat("MMddHHmm").format(new Date());
        int randomNumber = 100 + new Random().nextInt(900);
        return "ORDER_" + timestamp + "_" + randomNumber;
    }
}
