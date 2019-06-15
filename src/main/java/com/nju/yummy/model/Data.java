package com.nju.yummy.model;

/**
 * 该类并不需要持久化存储
 * 只是在前端展示数据统计时，map的无序性会导致统计数据顺序错误
 * 故设置此类，改为传对象数组到前端
 */
public class Data {
    private String time;
    private int num;
    private double money;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
