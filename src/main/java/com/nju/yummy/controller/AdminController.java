package com.nju.yummy.controller;

import com.nju.yummy.model.Restaurants;
import com.nju.yummy.service.MembersService;
import com.nju.yummy.service.OrderService;
import com.nju.yummy.service.RestaurantService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    @Resource
    private RestaurantService restaurantService;
    @Resource
    private MembersService membersService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/getUnApprovedRes", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUnApprovedRes(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("manager")) {
            res.put("message", "error");
            return res;
        }
        Iterable<Restaurants> restaurants = restaurantService.getUnApprovedRestaurants();
        res.put("message", "success");
        res.put("restaurants", restaurants);
        return res;
    }

    @RequestMapping(value = "/approveRestaurant", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> approveRestaurant(String[] rids, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("manager")) {
            res.put("message", "error");
            return res;
        }
        for (String rid : rids) {
            Restaurants restaurants = restaurantService.getRestaurantById(rid);
            restaurants.setState(1);
            restaurantService.updateRestaurant(restaurants);
        }
        res.put("message", "success");
        res.put("restaurants", restaurantService.getUnApprovedRestaurants());
        return res;
    }

    @RequestMapping(value = "/getUserStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserStatistics(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("manager")) {
            res.put("message", "error");
            return res;
        }
        res.put("message", "success");
        res.put("restaurants", restaurantService.findAll());
        res.put("members", membersService.getAllMember());
        res.put("statistics", orderService.getStatisticsForManager());
        return res;
    }

    @RequestMapping(value = "/getIncomeStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getIncomeStatistics(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("manager")) {
            res.put("message", "error");
            return res;
        }
        res.put("message", "success");
        res.put("num", orderService.getAllOrders().spliterator().getExactSizeIfKnown());
        res.put("money", orderService.getManagerTotalIncome());
        res.put("statistics", orderService.getStatisticsForManager());
        return res;
    }
}
