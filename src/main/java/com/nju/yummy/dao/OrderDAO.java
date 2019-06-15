package com.nju.yummy.dao;

import com.nju.yummy.model.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

public interface OrderDAO extends CrudRepository<Orders, String> {

    public ArrayList<Orders> findAllByMid(int mid);

    public ArrayList<Orders> findAllByRid(String rid);

    public ArrayList<Orders> findAllByMidAndState(int mid, String state);

    public ArrayList<Orders> findAllByRidAndState(String rid, String state);

    //mid search
    public ArrayList<Orders> findAllByMidAndOrderTimeBetween(int mid, Timestamp start, Timestamp end);

    public ArrayList<Orders> findAllByMidAndOrderTimeBefore(int mid, Timestamp end);

    public ArrayList<Orders> findAllByMidAndOrderTimeAfter(int mid, Timestamp after);

    public ArrayList<Orders> findAllByMidAndStateAndOrderTimeBefore(int mid, String state, Timestamp end);

    public ArrayList<Orders> findAllByMidAndStateAndOrderTimeAfter(int mid, String state, Timestamp start);

    public ArrayList<Orders> findAllByMidAndStateAndOrderTimeBetween(int mid, String state, Timestamp start, Timestamp end);

    //rid search
    public ArrayList<Orders> findAllByRidAndOrderTimeBetween(String rid, Timestamp start, Timestamp end);

    public ArrayList<Orders> findAllByRidAndOrderTimeBefore(String rid, Timestamp end);

    public ArrayList<Orders> findAllByRidAndOrderTimeAfter(String rid, Timestamp after);

    public ArrayList<Orders> findAllByRidAndStateAndOrderTimeBefore(String rid, String state, Timestamp end);

    public ArrayList<Orders> findAllByRidAndStateAndOrderTimeAfter(String rid, String state, Timestamp start);

    public ArrayList<Orders> findAllByRidAndStateAndOrderTimeBetween(String rid, String state, Timestamp start, Timestamp end);

    //mid statistics
    @Query("select sum(o.memberMoney) as totalCost from Orders o where o.mid=:mid and o.orderTime>=:startTime and o.orderTime<=:endTime group by o.mid")
    public Optional<Double> getTotalCostOneDayForMember(int mid, Timestamp startTime, Timestamp endTime);

    @Query("select count(o) as totalNum from Orders o where o.mid=:mid and o.orderTime>=:startTime and o.orderTime<=:endTime")
    public Optional<Integer> getTotalNumOneDayForMember(int mid, Timestamp startTime, Timestamp endTime);

    //rid statistics
    @Query("select sum(o.resMoney) as totalCost from Orders o where o.rid=:rid and o.orderTime>=:startTime and o.orderTime<=:endTime group by o.rid")
    public Optional<Double> getTotalCostOneDayForRestaurant(String rid, Timestamp startTime, Timestamp endTime);

    @Query("select count(o) as totalNum from Orders o where o.rid=:rid and o.orderTime>=:startTime and o.orderTime<=:endTime")
    public Optional<Integer> getTotalNumOneDayForRestaurant(String rid, Timestamp startTime, Timestamp endTime);

    @Query("select sum(o.adminMoney) as totalCost from Orders o where o.orderTime>=:startTime and o.orderTime<=:endTime")
    public Optional<Double> getTotalCostOneDayForManager(Timestamp startTime, Timestamp endTime);

    @Query("select count(o) as totalNum from Orders o where o.orderTime>=:startTime and o.orderTime<=:endTime")
    public Optional<Integer> getTotalNumOneDayForManager(Timestamp startTime, Timestamp endTime);

    @Query("select sum(o.adminMoney) as totalCost from Orders o")
    public Optional<Double> getTotalMoneyForManager();

}
