package com.nju.yummy.controller;

import com.nju.yummy.model.*;
import com.nju.yummy.service.*;
import com.nju.yummy.util.IDUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegisterController {

    @Resource
    private MembersService membersService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private AddressService addressService;

    @RequestMapping(value = "/registerForMember", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerForMember(@RequestParam Map<String, Object> map) {
        String email = map.get("userID").toString();
        String password = map.get("password").toString();
        String phone = map.get("phone").toString();
        String name = map.get("name").toString();
        String message = membersService.register(email, password, name, phone);
        Map<String, Object> res = new HashMap<>();
        res.put("message", message);
        return res;
    }

    @RequestMapping(value = "/registerForRestaurant", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerForRestaurant(@RequestParam Map<String, Object> map) {
        String name = map.get("userName").toString();
        String password = map.get("password").toString();
        String type = map.get("type").toString();
        String phone = map.get("phone").toString();
        String addressDescription = map.get("address").toString();
        String description = map.get("description").toString();
        Address address = addressService.getAddressByDescription(addressDescription);
        System.out.println(address.getAid());
        String rid = IDUtil.getNewRid();
        Restaurants restaurants = new Restaurants(rid, name, password, type, phone, description, address.getAid());
        restaurants.setPicPath("src/main/resources/static/img/restaurant_icon/restaurant_default.png");//restaurants.setAddressByAid(address);
        Map<String, Object> res = new HashMap<>();
        res.put("message", restaurantService.register(restaurants));
        res.put("id", rid);
        return res;
    }

    @RequestMapping(value = "/getAllAddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllAddress() {
        Iterable<String> address = addressService.getAllDescription();
        Map<String, Object> res = new HashMap<>();
        res.put("address", address);
        return res;
    }

}