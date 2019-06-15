package com.nju.yummy.service;

import com.nju.yummy.model.Data;
import com.nju.yummy.model.Orders;

import java.util.ArrayList;
import java.util.Map;

public interface OrderService {

    public void saveOrder(Orders orders);

    public void updateOrder(Orders orders);

    public Orders getOrderById(String id);

    public ArrayList<Orders> getOrdersByMid(int mid);

    public ArrayList<Orders> getOrdersByRid(String rid);

    public ArrayList<Orders> getOrdersByMidAndType(int mid, String type);

    public ArrayList<Orders> getOrdersByRidAndType(String rid, String type);

    public Iterable<Orders> getAllOrders();

    public double getDiscountCost(int level, double cost);

    public ArrayList<Orders> findOrdersByMidAndConditions(int mid, String startTime, String endTime, String state);

    public ArrayList<Orders> findOrdersByRidAndConditions(String rid, String startTime, String endTime, String state);

    public ArrayList<Data> getStatisticsForMember(int mid);

    public ArrayList<Data> getStatisticsForRes(String rid);

    public ArrayList<Data> getStatisticsForManager();

    public double getManagerTotalIncome();
}
