package com.nju.yummy.controller;

import com.nju.yummy.model.Members;
import com.nju.yummy.model.Restaurants;
import com.nju.yummy.service.MembersService;
import com.nju.yummy.service.RestaurantService;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Resource
    private MembersService membersService;
    @Resource
    private RestaurantService restaurantService;

    @RequestMapping(value = "/loginForMember", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginForMember(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String email = map.get("userID").toString();
        String password = map.get("password").toString();
        System.out.println(email + "\t" + password);
        Members members = membersService.login(email, password);
        Map<String, Object> res = new HashMap<>();
        if (members == null) {
            res.put("message", "noMember");
        } else if (members.getState() == 0) {
            res.put("message", "inactive");
        } else if (members.getState() == -1) {
            res.put("message", "deleted");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("id", members.getMid());
            session.setAttribute("type", "member");
            res.put("message", "success");
        }
        return res;
    }

    @RequestMapping(value = "/loginForRestaurant", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginForRestaurant(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String rid = map.get("userID").toString();
        String password = map.get("password").toString();
        Restaurants restaurants = restaurantService.login(rid, password);
        Map<String, Object> res = new HashMap<>();
        if (restaurants == null) {
            res.put("message", "noRestaurant");
        } else if (restaurants.getState() == 0) {
            res.put("message", "unApproved");
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("id", rid);
            session.setAttribute("type", "restaurant");
            res.put("message", "success");
        }
        return res;
    }

    @RequestMapping(value = "/loginForManager", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginForManager(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        String id = map.get("userID").toString();
        String password = map.get("password").toString();
        Map<String, Object> res = new HashMap<>();
        if (id.equals("admin") && password.equals("admin")) {
            res.put("message", "success");
        } else {
            res.put("message", "error");
        }
        HttpSession session = request.getSession();
        session.setAttribute("id", "admin");
        session.setAttribute("type", "manager");
        return res;
    }
}
