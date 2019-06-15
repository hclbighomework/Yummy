package com.nju.yummy.util;

import com.nju.yummy.service.RestaurantService;
import com.nju.yummy.service.impl.RestaurantServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Component
public class IDUtil {

    private static RestaurantService restaurantService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService) {
        IDUtil.restaurantService = restaurantService;
    }

    public static String getNewRid() {
        ArrayList<String> rids = restaurantService.getAllRid();
        //System.out.println(rids);
        String str = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        String result = "";
        while (rids.contains(result) || result.equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < 7; i++){
                int number = random.nextInt(36);
                stringBuilder.append(str.charAt(number));
            }
            result = stringBuilder.toString();
        }
        return result;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getNewOid() {
        String date = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        System.out.println(date);
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int num = random.nextInt(10);
            stringBuilder.append(String.valueOf(num));
        }
        return date + stringBuilder.toString();
    }
}