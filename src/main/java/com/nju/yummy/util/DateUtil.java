package com.nju.yummy.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //支付间隔时间为15min
    public static long payInterval = 2 * 60 * 1000L;
    //假设商家配送一餐准备需要10min
    public static long deliveryInterval = 10 * 60 * 1000L;

    public static String timestampToString(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (timestamp == null) {
            return "null";
        }
        return dateFormat.format(timestamp);
    }

    public static Timestamp stringToTimestamp(String str, String state) {
        if (state.equals("start")) {
            return Timestamp.valueOf(str + " 00:00:00");
        } else {
            return Timestamp.valueOf(str + " 23:59:59");
        }
    }

    public static Timestamp stringToTimestamp(String str){
        return Timestamp.valueOf(str.replaceAll("T", " ") + ":00");
    }

    public static int getLeftPayTime(Timestamp timestamp) {
        long seconds = System.currentTimeMillis() - timestamp.getTime();

        if (seconds >= payInterval) {
            return -1;
        } else {
            return (int) ((payInterval - seconds) / 1000);
        }
    }

    public static String estimateArriveTime(int interval) {
        long seconds = System.currentTimeMillis() + interval * 60 * 1000 + deliveryInterval;
        System.out.println(new Timestamp(seconds));
        return timestampToString(new Timestamp(seconds));
    }

    public static int getTimeDifference(Timestamp timestamp) {
        return (int) ((System.currentTimeMillis() - timestamp.getTime()) / (60 * 1000));
    }

    public static String getBeforeTimeString(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, num);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }
}
