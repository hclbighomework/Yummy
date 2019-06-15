package com.nju.yummy.controller;

import com.nju.yummy.service.MembersService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Resource
    private MembersService membersService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "pages/login.html";
    }

    @RequestMapping("/member_register")
    public String registerForMember() {
        return "pages/member/memberRegister.html";
    }

    @RequestMapping("/restaurant_register")
    public String registerForRestaurant() {
        return "pages/restaurant/restaurantRegister.html";
    }

    @RequestMapping("/member_main")
    public String mainForMember() {
        return "pages/member/memberMain.html";
    }

    @RequestMapping("/member_profile")
    public String memberInfo() {
        return "pages/member/memberInfo.html";
    }

    @RequestMapping("/member_order")
    public String memberOrder() {
        return "pages/member/memberOrder.html";
    }

    @RequestMapping("/member_shopping")
    public String memberShop() {
        return "pages/member/restaurantInfo.html";
    }

    @RequestMapping("/member_consume")
    public String memberConsume() {
        return "pages/member/memberConsume.html";
    }

    @RequestMapping("/restaurant_main")
    public String restaurantMain() {
        return "pages/restaurant/restaurantMain.html";
    }

    @RequestMapping("/restaurant_goods")
    public String restaurantGoods() {
        return "pages/restaurant/restaurantGoods.html";
    }

    @RequestMapping("/restaurant_profile")
    public String restaurantInfo() {
        return "pages/restaurant/restaurantInfo.html";
    }

    @RequestMapping("/restaurant_orders")
    public String restaurantStatistics(){
        return "pages/restaurant/orderStatistics.html";
    }

    @RequestMapping("/restaurant_income")
    public String restaurantIncome() {
        return "pages/restaurant/restaurantIncome.html";
    }

    @RequestMapping("/manager_main")
    public String managerMain() {
        return "pages/manager/managerMain.html";
    }

    @RequestMapping("/manager_statistics")
    public String dataStatistics() {
        return "pages/manager/dataStatistics.html";
    }

    @RequestMapping("/manager_income")
    public String managerIncome() {
        return "pages/manager/managerIncome.html";
    }

    @RequestMapping("/active")
    public String active(String code) {
        membersService.makeActive(code);
        return "pages/member/active.html";
    }

    @RequestMapping("/order_detail")
    public String orderDetail() {
        return "pages/member/orderDetail.html";
    }
}
