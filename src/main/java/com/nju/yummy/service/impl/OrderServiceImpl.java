package com.nju.yummy.service.impl;

import com.nju.yummy.dao.OrderDAO;
import com.nju.yummy.model.Data;
import com.nju.yummy.model.Members;
import com.nju.yummy.model.Orders;
import com.nju.yummy.service.OrderService;
import com.nju.yummy.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDAO orderDAO;

    @Transactional
    @Override
    public void saveOrder(Orders orders) {
        orderDAO.save(orders);
    }

    @Transactional
    @Override
    public void updateOrder(Orders orders) {
        orderDAO.save(orders);
    }

    @Override
    public Orders getOrderById(String id) {
        return orderDAO.findById(id).orElse(null);
    }

    @Override
    public ArrayList<Orders> getOrdersByMid(int mid) {
        return orderDAO.findAllByMid(mid);
    }

    @Override
    public ArrayList<Orders> getOrdersByRid(String rid) {
        return orderDAO.findAllByRid(rid);
    }

    @Override
    public ArrayList<Orders> getOrdersByMidAndType(int mid, String type) {
        return orderDAO.findAllByMidAndState(mid, type);
    }

    @Override
    public ArrayList<Orders> getOrdersByRidAndType(String rid, String type) {
        return orderDAO.findAllByRidAndState(rid, type);
    }

    @Override
    public Iterable<Orders> getAllOrders() {
        return orderDAO.findAll();
    }

    @Override
    public double getDiscountCost(int level, double cost) {
        switch (level) {
            case 2:
                cost *= 0.95;
                break;
            case 3:
                cost *= 0.9;
                break;
            case 4:
                cost *= 0.85;
                break;
            case 5:
                cost *= 0.8;
        }
        return Double.parseDouble(new DecimalFormat("#.00").format(cost));
    }

    @Override
    public ArrayList<Orders> findOrdersByMidAndConditions(int mid, String startTime, String endTime, String state) {
        if (startTime.equals("") && endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByMid(mid);
        } else if (startTime.equals("") && endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByMidAndState(mid, state);
        } else if (!startTime.equals("") && endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByMidAndOrderTimeAfter(mid, DateUtil.stringToTimestamp(startTime, "start"));
        } else if (!startTime.equals("") && endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByMidAndStateAndOrderTimeAfter(mid, state, DateUtil.stringToTimestamp(startTime, "start"));
        } else if (!startTime.equals("") && !endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByMidAndOrderTimeBetween(mid, DateUtil.stringToTimestamp(startTime, "start"), DateUtil.stringToTimestamp(endTime, "end"));
        } else if (!startTime.equals("") && !endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByMidAndStateAndOrderTimeBetween(mid, state, DateUtil.stringToTimestamp(startTime, "start"), DateUtil.stringToTimestamp(endTime, "end"));
        } else if (startTime.equals("") && !endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByMidAndOrderTimeBefore(mid, DateUtil.stringToTimestamp(endTime, "end"));
        } else if (startTime.equals("") && !endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByMidAndStateAndOrderTimeBefore(mid, state, DateUtil.stringToTimestamp(endTime, "end"));
        }
        return null;
    }

    @Override
    public ArrayList<Orders> findOrdersByRidAndConditions(String rid, String startTime, String endTime, String state) {
        if (startTime.equals("") && endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByRid(rid);
        } else if (startTime.equals("") && endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByRidAndState(rid, state);
        } else if (!startTime.equals("") && endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByRidAndOrderTimeAfter(rid, DateUtil.stringToTimestamp(startTime, "start"));
        } else if (!startTime.equals("") && endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByRidAndStateAndOrderTimeAfter(rid, state, DateUtil.stringToTimestamp(startTime, "start"));
        } else if (!startTime.equals("") && !endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByRidAndOrderTimeBetween(rid, DateUtil.stringToTimestamp(startTime, "start"), DateUtil.stringToTimestamp(endTime, "end"));
        } else if (!startTime.equals("") && !endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByRidAndStateAndOrderTimeBetween(rid, state, DateUtil.stringToTimestamp(startTime, "start"), DateUtil.stringToTimestamp(endTime, "end"));
        } else if (startTime.equals("") && !endTime.equals("") && state.equals("全部")) {
            return orderDAO.findAllByRidAndOrderTimeBefore(rid, DateUtil.stringToTimestamp(endTime, "end"));
        } else if (startTime.equals("") && !endTime.equals("") && !state.equals("全部")) {
            return orderDAO.findAllByRidAndStateAndOrderTimeBefore(rid, state, DateUtil.stringToTimestamp(endTime, "end"));
        }
        return null;
    }

    @Override
    public ArrayList<Data> getStatisticsForMember(int mid) {
        ArrayList<Data> res = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            Data data = new Data();
            String time = DateUtil.getBeforeTimeString(-i);
            Optional<Double> totalMoney = orderDAO.getTotalCostOneDayForMember(mid, DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            Optional<Integer> totalNum = orderDAO.getTotalNumOneDayForMember(mid, DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            data.setTime(time);
            data.setMoney(totalMoney.isPresent() ? totalMoney.get() : 0);
            data.setNum(totalNum.isPresent() ? totalNum.get() : 0);
            res.add(data);
        }
        return res;
    }

    @Override
    public ArrayList<Data> getStatisticsForRes(String rid) {
        ArrayList<Data> res = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            Data data = new Data();
            String time = DateUtil.getBeforeTimeString(-i);
            Optional<Double> totalMoney = orderDAO.getTotalCostOneDayForRestaurant(rid, DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            Optional<Integer> totalNum = orderDAO.getTotalNumOneDayForRestaurant(rid, DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            data.setTime(time);
            data.setMoney(totalMoney.isPresent() ? totalMoney.get() : 0);
            data.setNum(totalNum.isPresent() ? totalNum.get() : 0);
            res.add(data);
        }
        return res;
    }

    @Override
    public ArrayList<Data> getStatisticsForManager() {
        ArrayList<Data> res = new ArrayList<>();
        for (int i = 10; i >= 1; i--) {
            Data data = new Data();
            String time = DateUtil.getBeforeTimeString(-i);
            Optional<Double> totalMoney = orderDAO.getTotalCostOneDayForManager(DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            Optional<Integer> totalNum = orderDAO.getTotalNumOneDayForManager(DateUtil.stringToTimestamp(time, "start"), DateUtil.stringToTimestamp(time, "end"));
            data.setTime(time);
            data.setMoney(totalMoney.isPresent() ? totalMoney.get() : 0);
            data.setNum(totalNum.isPresent() ? totalNum.get() : 0);
            res.add(data);
        }
        return res;
    }

    @Override
    public double getManagerTotalIncome() {
        Optional<Double> money = orderDAO.getTotalMoneyForManager();
        return money.isPresent() ? money.get() : 0.00;
    }
}
