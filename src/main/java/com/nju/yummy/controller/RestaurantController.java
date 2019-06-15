package com.nju.yummy.controller;

import com.nju.yummy.model.Members;
import com.nju.yummy.model.Reduction;
import com.nju.yummy.model.Restaurants;
import com.nju.yummy.service.AddressService;
import com.nju.yummy.service.OrderService;
import com.nju.yummy.service.ReductionService;
import com.nju.yummy.service.RestaurantService;
import com.nju.yummy.service.impl.OrderServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestaurantController {

    @Resource
    private RestaurantService restaurantService;
    @Resource
    private AddressService addressService;
    @Resource
    private ReductionService reductionService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/getResInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getRestaurantInfo(HttpServletRequest request) {
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
            res.put("restaurant", restaurant);
            res.put("addressList", addressService.getAllDescription());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateResInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateResInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            String name = map.get("newName").toString();
            if (restaurantService.getRestaurantByName(name) == null || restaurantService.getRestaurantByName(name).getRid().equals(rid)) {
                restaurant.setName(name);
                restaurant.setType(map.get("newType").toString());
                restaurant.setPhone(map.get("newPhone").toString());
                restaurant.setAid(addressService.getAddressByDescription(map.get("newAddress").toString()).getAid());
                restaurant.setDescription(map.get("newDescription").toString());
                restaurant.setState(0);
                restaurantService.updateRestaurant(restaurant);
                res.put("message", "success");
            } else {
                res.put("message", "nameUsed");
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateResPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateResPwd(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            if (restaurant.getPassword().equals(map.get("oldPassword").toString())) {
                res.put("message", "success");
                restaurant.setPassword(map.get("newPassword").toString());
                restaurantService.updateRestaurant(restaurant);
            } else {
                res.put("message", "pwdError");
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateResCost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateResCost(@RequestParam Map<String, Object> map, HttpServletRequest request) {
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
            restaurant.setMinCost(Double.parseDouble(map.get("newMinCost").toString()));
            restaurant.setHumanCost(Double.parseDouble(map.get("newHumanCost").toString()));
            restaurantService.updateRestaurant(restaurant);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/saveReduction", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveReduction(double[] fullCost, double[] reduceCost, HttpServletRequest request) {
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
            for (Reduction reduction : restaurant.getReductionsByRid()) {
                reductionService.deleteReduction(reduction);
            }
            if (fullCost == null || fullCost.length == 0) {
                res.put("reductionList", reductionService.getSortedReductionByRid(rid));
            } else {
                for (int i = 0; i < fullCost.length; i++) {
                    Reduction reduction = new Reduction();
                    reduction.setRid(rid);
                    reduction.setFullCost(fullCost[i]);
                    reduction.setReduceCost(reduceCost[i]);
                    reductionService.addReduction(reduction);
                }
                res.put("reductionList", reductionService.getSortedReductionByRid(rid));
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getRestaurantStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberStatistics(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String id = session.getAttribute("id").toString();
        Restaurants restaurants = restaurantService.getRestaurantById(id);
        if (restaurants != null) {
            res.put("message", "success");
            res.put("name", restaurants.getName());
            res.put("totalMoney", restaurants.getTotalMoney());
            res.put("orderNum", restaurants.getOrdersByRid().size());
            res.put("statistics", orderService.getStatisticsForRes(id));
        } else {
            res.put("message", "error");
        }
        return res;
    }
}
