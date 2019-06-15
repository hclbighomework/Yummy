package com.nju.yummy.controller;

import com.nju.yummy.model.Orders;
import com.nju.yummy.model.Packages;
import com.nju.yummy.model.Restaurants;
import com.nju.yummy.model.Singles;
import com.nju.yummy.service.*;
import com.nju.yummy.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.nju.yummy.controller.MemberOrderController.getOrderContent;

@RestController
public class RestaurantOrderController {

    @Resource
    private RestaurantService restaurantService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderSingleService orderSingleService;
    @Resource
    private OrderPackageService orderPackageService;

    @RequestMapping(value = "/getUnresolvedOrders", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUnresolvedOrders(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            res.put("message", "success");
            res.put("rName", restaurant.getName());
            ArrayList<Orders> orders = orderService.getOrdersByRidAndType(rid, "已支付");
            for (Orders order : orders) {
                order.setMemberName(order.getMembersByMid().getName());
                order.setOrderTimeString(DateUtil.timestampToString(order.getOrderTime()));
                order.setContent(getOrderContent(order));
            }
            res.put("orders", orders);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getOrderInfoForRestaurant", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderInfoForRestaurant(@RequestParam Map<String, Object> map) {
        String oid = map.get("oid").toString();
        Orders orders = orderService.getOrderById(oid);
        orders.setOrderTimeString(DateUtil.timestampToString(orders.getOrderTime()));
        for (Singles singles : orders.getSinglesCollection()) {
            singles.setOrderNum(orderSingleService.getOrderSingle(oid, singles.getSid()).getNum());
        }
        for (Packages packages : orders.getPackagesCollection()) {
            packages.setOrderNum(orderPackageService.getOrderPackage(oid, packages.getPid()).getNum());
        }
        Map<String, Object> res = new HashMap<>();
        res.put("order", orders);
        res.put("memberName", orders.getMembersByMid().getName());
        res.put("phone", orders.getMembersByMid().getPhone());
        return res;
    }

    @RequestMapping(value = "/deliveryOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deliveryOrder(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            res.put("message", "success");
            String oid = map.get("oid").toString();
            Orders order = orderService.getOrderById(oid);
            order.setDeliveryTime(new Timestamp(System.currentTimeMillis()));
            order.setState("配送中");
            orderService.updateOrder(order);
            ArrayList<Orders> orders = orderService.getOrdersByRidAndType(rid, "已支付");
            for (Orders order1 : orders) {
                order1.setMemberName(order1.getMembersByMid().getName());
                order1.setOrderTimeString(DateUtil.timestampToString(order1.getOrderTime()));
                order1.setContent(getOrderContent(order1));
            }
            res.put("orders", orders);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getResOrders", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getResOrders(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            res.put("message", "success");
            res.put("rName", restaurant.getName());
            Collection<Orders> orders = restaurant.getOrdersByRid();
            for (Orders order : orders) {
                order.setMemberName(order.getMembersByMid().getName());
                order.setOrderTimeString(DateUtil.timestampToString(order.getOrderTime()));
                order.setContent(getOrderContent(order));
            }
            res.put("orders", orders);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/searchOrdersForRes", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchOrdersForRes(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            res.put("message", "success");
            String startTime = map.get("startTime").toString();
            String endTime = map.get("endTime").toString();
            String state = map.get("orderState").toString();
            String key = map.get("key").toString();
            ArrayList<Orders> orders = orderService.findOrdersByRidAndConditions(rid, startTime, endTime, state);
            ArrayList<Orders> keyOrders = new ArrayList<>();
            for (Orders order : orders) {
                if (key.equals("") || order.getMembersByMid().getName().contains(key)) {
                    order.setOrderTimeString(DateUtil.timestampToString(order.getOrderTime()));
                    order.setArriveTimeString(DateUtil.timestampToString(order.getArriveTime()));
                    order.setContent(getOrderContent(order));
                    order.setMemberName(order.getMembersByMid().getName());
                    keyOrders.add(order);
                }
            }
            res.put("orderList", keyOrders);
        } else {
            res.put("message", "error");
        }
        return res;
    }
}
