package com.guna.yumzoom.restaurant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RestaurantIdGen {

    public static String generateRestaurantId() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        return "YumZooM_" + timestamp + "_" + randomNumber;
    }
}
