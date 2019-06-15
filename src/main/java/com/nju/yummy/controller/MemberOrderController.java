package com.nju.yummy.controller;

import com.nju.yummy.model.*;
import com.nju.yummy.service.*;
import com.nju.yummy.util.DateUtil;
import com.nju.yummy.util.DeliveryUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@RestController
public class MemberOrderController {

    @Resource
    private MembersService membersService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderSingleService orderSingleService;
    @Resource
    private OrderPackageService orderPackageService;
    @Resource
    private AddressService addressService;

    //对于不同等级的用户平台的提成不同
    private double[] pct = {0.10, 0.08, 0.07, 0.06, 0.05};


    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getOrderInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            String oid = map.get("oid").toString();
            Orders orders = orderService.getOrderById(oid);
            if (orders.getMid() != members.getMid()) {
                res.put("message", "error");
                return res;
            }
            int leftTime = DateUtil.getLeftPayTime(orders.getOrderTime());
            System.out.println(leftTime);
            if (leftTime <= 0 && orders.getState().equals("支付中")) {
                orders.setState("已取消");
                orderService.updateOrder(orders);
            }
            res.put("leftTime", leftTime);
            orders.setOrderTimeString(DateUtil.timestampToString(orders.getOrderTime()));
            orders.setArriveTimeString(DateUtil.timestampToString(orders.getArriveTime()));
            for (Singles singles : orders.getSinglesCollection()) {
                singles.setOrderNum(orderSingleService.getOrderSingle(oid, singles.getSid()).getNum());
            }
            for (Packages packages : orders.getPackagesCollection()) {
                packages.setOrderNum(orderPackageService.getOrderPackage(oid, packages.getPid()).getNum());
            }
            res.put("order", orders);
            Restaurants restaurants = restaurantService.getRestaurantById(orders.getRid());
            res.put("rName", restaurants.getName());
            res.put("rPhone", restaurants.getPhone());
            res.put("message", "success");
            res.put("member", members);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/payOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> payOrder(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            String oid = map.get("oid").toString();
            Orders orders = orderService.getOrderById(oid);
            if (orders.getMid() != members.getMid()) {
                res.put("message", "error");
                return res;
            }
            membersService.payForOrder(members.getMid(), orders.getCost());
            orders.setAddress(map.get("address").toString());
            orders.setState("已支付");
            //用户支付后，将用户消费存入数据库，方便数据统计
            orders.setMemberMoney(orders.getCost());
            orderService.updateOrder(orders);
            res.put("message", "success");
            res.put("balance", membersService.getMemberById(members.getMid()).getBalance());
            int startAddressID = addressService.getAddressByDescription(orders.getAddress()).getAid();
            int endAddressID = orders.getRestaurantsByRid().getAid();
            res.put("estimateTime", DateUtil.estimateArriveTime(DeliveryUtil.getDeliveryInterval(startAddressID - 1, endAddressID - 1)));
        } else {
            res.put("message", "error");
            return res;
        }
        return res;
    }

    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> cancelOrder(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            String oid = map.get("oid").toString();
            Orders orders = orderService.getOrderById(oid);
            if (orders.getMid() != members.getMid()) {
                res.put("message", "error");
                return res;
            }
            //取消订单，将部分款额退还给用户，并从总消费中扣除，同时订单中的用户消费也要对应更新
            //如若只退还部分款额，则剩余部分仍需分给商户及平台
            switch (orders.getState()) {
                case "配送中":
                    orders.setState("已取消");
                    int timeDifference = DateUtil.getTimeDifference(orders.getDeliveryTime());
                    double cost = Double.parseDouble(new DecimalFormat("#.00").format(Math.min(0.5 * orders.getCost(), 0.01 * timeDifference * orders.getCost())));
                    members.setBalance(members.getBalance() + orders.getCost() - cost);
                    members.setTotalCost(members.getTotalCost() + orders.getCost() - cost);
                    membersService.updateMember(members);
                    orders.setMemberMoney(cost);
                    double adminMoney = Double.parseDouble(new DecimalFormat("#.00").format(cost * pct[members.getLevel() - 1]));
                    orders.setAdminMoney(adminMoney);
                    orders.setResMoney(cost - adminMoney);
                    orderService.updateOrder(orders);
                    Restaurants restaurants = restaurantService.getRestaurantById(orders.getRid());
                    restaurants.setTotalMoney(restaurants.getTotalMoney() + orders.getCost() - adminMoney);
                    restaurantService.updateRestaurant(restaurants);
                    res.put("note", "成功取消订单，已退款" + (orders.getCost() - cost) + "元！");
                    break;
                case "已支付":
                    orders.setState("已取消");
                    orders.setMemberMoney(0);
                    members.setBalance(members.getBalance() + orders.getCost());
                    membersService.updateMember(members);
                    res.put("note", "成功取消订单，已退款" + orders.getCost() + "元！");
                    break;
                default:
                    orders.setState("已取消");
                    res.put("note", "订单已取消");
                    break;
            }
            orderService.updateOrder(orders);
            res.put("message", "success");
        } else {
            res.put("message", "error");
            return res;
        }
        return res;
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> recharge(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            members.setBalance(members.getBalance() + Double.parseDouble(map.get("money").toString()));
            membersService.updateMember(members);
            res.put("message", "success");
            res.put("balance", members.getBalance());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> confirmOrder(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            String oid = map.get("oid").toString();
            Orders orders = orderService.getOrderById(oid);
            if (orders.getMid() != members.getMid()) {
                res.put("message", "error");
                return res;
            }
            //确认收货后，自动结算订单，将订单钱分为商户可得和平台可得存储
            orders.setState("已完成");
            orders.setArriveTime(new Timestamp(System.currentTimeMillis()));
            double adminMoney = Double.parseDouble(new DecimalFormat("#.00").format(orders.getCost() * pct[members.getLevel() - 1]));
            orders.setAdminMoney(adminMoney);
            orders.setResMoney(orders.getCost() - adminMoney);
            orderService.updateOrder(orders);
            Restaurants restaurants = restaurantService.getRestaurantById(orders.getRid());
            restaurants.setTotalMoney(restaurants.getTotalMoney() + orders.getCost() - adminMoney);
            restaurantService.updateRestaurant(restaurants);
            membersService.updateLevel(members.getMid(), orders.getCost());
            res.put("message", "success");
            res.put("arriveTime", DateUtil.timestampToString(orders.getArriveTime()));
        } else {
            res.put("message", "error");
            return res;
        }
        return res;
    }

    @RequestMapping(value = "/getMemberOrders", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberOrders(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            Collection<Orders> ordersCollection = members.getOrdersByMid();
            for (Orders orders : ordersCollection) {
                if (orders.getState().equals("支付中") && DateUtil.getLeftPayTime(orders.getOrderTime()) <= 0) {
                    orders.setState("已取消");
                    orderService.updateOrder(orders);
                }
                orders.setOrderTimeString(DateUtil.timestampToString(orders.getOrderTime()));
                orders.setArriveTimeString(DateUtil.timestampToString(orders.getArriveTime()));
                orders.setContent(getOrderContent(orders));
                orders.setRestaurantName(orders.getRestaurantsByRid().getName());
            }
            res.put("message", "success");
            res.put("orders", ordersCollection);
            res.put("userName", members.getName());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/findOrdersByKey", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findOrdersByKey(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        Members members = valid(request);
        if (members != null) {
            res.put("message", "success");
            String startTime = map.get("startTime").toString();
            String endTime = map.get("endTime").toString();
            String state = map.get("orderState").toString();
            String key = map.get("key").toString();
            ArrayList<Orders> orders = orderService.findOrdersByMidAndConditions(members.getMid(), startTime, endTime, state);
            ArrayList<Orders> keyOrders = new ArrayList<>();
            for (Orders order : orders) {
                if (key.equals("") || order.getRestaurantsByRid().getName().contains(key)) {
                    order.setOrderTimeString(DateUtil.timestampToString(order.getOrderTime()));
                    order.setArriveTimeString(DateUtil.timestampToString(order.getArriveTime()));
                    order.setContent(getOrderContent(order));
                    order.setRestaurantName(order.getRestaurantsByRid().getName());
                    keyOrders.add(order);
                }
            }
            res.put("orders", keyOrders);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    static String getOrderContent(Orders orders) {
        String str;
        if (!orders.getSinglesCollection().isEmpty()) {
            str = orders.getSinglesCollection().iterator().next().getName();
        } else {
            str = orders.getPackagesCollection().iterator().next().getName();
        }
        int num = orders.getSinglesCollection().size() + orders.getPackagesCollection().size();
        str += "等" + String.valueOf(num) + "个菜品";
        return str;
    }

    private Members valid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            return null;
        }
        return membersService.getMemberById(Integer.parseInt(session.getAttribute("id").toString()));
    }
}
