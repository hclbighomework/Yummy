package com.nju.yummy.controller;

import com.nju.yummy.model.*;
import com.nju.yummy.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

@RestController
public class MemberController {
    @Resource
    private MembersService membersService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private SingleService singleService;
    @Resource
    private PackageService packageService;
    @Resource
    private MemberAddressService memberAddressService;
    @Resource
    private AddressService addressService;
    @Resource
    private OrderService orderService;

    @RequestMapping(value = "/getMemberInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberInfo(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            res.put("message", "success");
            res.put("username", members.getName());
            Iterable<Restaurants> restaurants = restaurantService.getApprovedRestaurants();
            res.put("resList", restaurants);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getRestaurantByType", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getRestaurantByType(@RequestParam Map<String, Object> map) {
        String type = map.get("type").toString();
        Iterable<Restaurants> restaurants;
        if (type.equals("全部店家")) {
            restaurants = restaurantService.findAll();
        } else {
            restaurants = restaurantService.getRestaurantByType(type);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("resList", restaurants);
        return res;
    }

    @RequestMapping(value = "/searchRestaurant", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchRestaurant(@RequestParam Map<String, Object> map) {
        String name = map.get("key").toString();
        ArrayList<Restaurants> restaurants = restaurantService.getByNameKey(name);
        Map<String, ArrayList<Singles>> singleMap = new HashMap<>();
        for (Restaurants restaurant : restaurants) {
            singleMap.put(restaurant.getRid(), new ArrayList<>(restaurant.getSinglesByRid()));
        }
        //获取包含name的单品列表，将其添加到map中
        ArrayList<Singles> singles = singleService.getSinglesByNameKey(name);
        for (Singles single : singles) {
            if (!singleMap.containsKey(single.getRid())) {
                ArrayList<Singles> singlesList = new ArrayList<>();
                singlesList.add(single);
                singleMap.put(single.getRid(), singlesList);
            } else {
                if (!singleMap.get(single.getRid()).contains(single)) {
                    singleMap.get(single.getRid()).add(single);
                }
            }
        }
        //获取包含name的套餐列表，将其添加到map中
        Map<String, ArrayList<Packages>> packageMap = new HashMap<>();
        for (Restaurants restaurant : restaurants) {
            packageMap.put(restaurant.getRid(), new ArrayList<>(restaurant.getPackagesByRid()));
        }
        ArrayList<Packages> packages = packageService.getPackagesByNameKey(name);
        for (Packages aPackage : packages) {
            if (!packageMap.containsKey(aPackage.getRid())) {
                ArrayList<Packages> packageList = new ArrayList<>();
                packageList.add(aPackage);
                packageMap.put(aPackage.getRid(), packageList);
            } else {
                if (!packageMap.get(aPackage.getRid()).contains(aPackage)) {
                    packageMap.get(aPackage.getRid()).add(aPackage);
                }
            }
        }
        //将符合name的单品信息和套餐信息塞进对应的restaurant中，传回前端
        ArrayList<Restaurants> restaurantsArrayList = new ArrayList<>();
        for (String s : singleMap.keySet()) {
            Restaurants restaurant = restaurantService.getRestaurantById(s);
            restaurant.setSinglesByRid(singleMap.get(s));
            if (packageMap.containsKey(s)) {
                restaurant.setPackagesByRid(packageMap.get(s));
            } else {
                restaurant.setPackagesByRid(new ArrayList<>());//如果是null，无法转成json传回前端
            }
            restaurantsArrayList.add(restaurant);
        }
        for (String s : packageMap.keySet()) {
            if (!singleMap.containsKey(s)) {
                Restaurants restaurant = restaurantService.getRestaurantById(s);
                restaurant.setSinglesByRid(new ArrayList<>());
                restaurant.setPackagesByRid(packageMap.get(s));
                restaurantsArrayList.add(restaurant);
            }
        }
        Map<String, Object> res = new HashMap<>();
        res.put("resList", restaurantsArrayList);
        return res;
    }

    @RequestMapping(value = "/getMemberProfile", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberProfile(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            for (Address address : members.getAddressCollection()) {
                address.setDefaultState(memberAddressService.getMemberAddress(id, address.getAid()).getDefaultState());
            }
            res.put("message", "success");
            res.put("member", members);
            res.put("allAddress", addressService.getAllDescription());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateMemberInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateMemberInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            res.put("message", "success");
            members.setName(map.get("newName").toString());
            members.setPhone(map.get("newPhone").toString());
            membersService.updateMember(members);
            res.put("name", members.getName());
            res.put("phone", members.getPhone());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateMemberPassword", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateMemberPassword(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            res.put("message", "success");
            if (members.getPassword().equals(map.get("oldPassword").toString())) {
                members.setPassword(map.get("newPassword").toString());
                membersService.updateMember(members);
                res.put("message", "success");
            } else {
                res.put("message", "old");
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/deleteMember", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteMember(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            res.put("message", "success");
            membersService.deleteMember(members.getMid());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getMemberStatistics", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMemberStatistics(HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            res.put("message", "success");
            res.put("name", members.getName());
            res.put("totalMoney", members.getTotalCost());
            res.put("orderNum", members.getOrdersByMid().size());
            res.put("statistics", orderService.getStatisticsForMember(id));
        } else {
            res.put("message", "error");
        }
        return res;
    }
}
