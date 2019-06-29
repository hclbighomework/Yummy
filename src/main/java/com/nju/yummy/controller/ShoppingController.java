package com.nju.yummy.controller;

import com.nju.yummy.model.*;
import com.nju.yummy.service.*;
import com.nju.yummy.util.IDUtil;
import com.nju.yummy.util.ImageUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ShoppingController {

    @Resource
    private MembersService membersService;
    @Resource
    private RestaurantService restaurantService;
    @Resource
    private SingleService singleService;
    @Resource
    private PackageService packageService;
    @Resource
    private PackageSingleService packageSingleService;
    @Resource
    private MemberAddressService memberAddressService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderSingleService orderSingleService;
    @Resource
    private OrderPackageService orderPackageService;
    @Resource
    private ReductionService reductionService;

    @RequestMapping(value = "/getShoppingInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getShoppingInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
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
            String rid = map.get("rid").toString();
            Restaurants restaurants = restaurantService.getRestaurantById(rid);
            restaurants.setPackagesByRid(packageService.getValidPackagesForRid(restaurants.getRid()));
            restaurants.setSinglesByRid(singleService.getValidSinglesForRid(restaurants.getRid()));
            restaurants.setReductionsByRid(reductionService.getSortedReductionByRid(restaurants.getRid()));
            restaurants.setImgData(ImageUtil.imageToBase64(restaurants.getPicPath()));
            for (Packages packages : restaurants.getPackagesByRid()) {
                packages.setImgData(ImageUtil.imageToBase64(packages.getPicPath()));
            }
            for (Singles singles : restaurants.getSinglesByRid()) {
                singles.setImgData(ImageUtil.imageToBase64(singles.getPicPath()));
            }
            res.put("restaurant", restaurants);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/singleSorted", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> singleSorted(@RequestParam Map<String, Object> map) {
        String rid = map.get("rid").toString();
        String sortType = map.get("sort").toString();
        Map<String, Object> res = new HashMap<>();
        res.put("singleList", singleService.getSortedValidSinglesForRid(rid, sortType));
        return res;
    }

    @RequestMapping(value = "/packageSorted", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> packageSorted(@RequestParam Map<String, Object> map) {
        String rid = map.get("rid").toString();
        String sortType = map.get("sort").toString();
        Map<String, Object> res = new HashMap<>();
        res.put("packageList", packageService.getSortedValidPackagesForRid(rid, sortType));
        return res;
    }

    @RequestMapping(value = "/getPackageInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPackageInfo(@RequestParam Map<String, Object> map) {
        String rid = map.get("rid").toString();
        String pName = map.get("pName").toString();
        Restaurants restaurants = restaurantService.getRestaurantById(rid);
        Packages packages = packageService.getPackageByNameAndRid(pName, restaurants.getRid());
        for (Singles single: packages.getSinglesCollection()) {
            single.setPackageNum(packageSingleService.getPackageSingle(packages.getPid(), single.getSid()).getNum());
            //System.out.println(single.getPackageNum());
        }
        Map<String, Object> res = new HashMap<>();
        res.put("packageInfo", packages);
        return res;
    }

    @RequestMapping(value = "/setOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setOrder(String[] singleName, int[] singleNum, String[] packageName, int[] packageNum, String rid, String note, double cost, HttpServletRequest request) {
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
            String oid = IDUtil.getNewOid();
            Orders order = new Orders();
            order.setOid(oid);
            order.setMid(members.getMid());
            order.setRid(rid);
            order.setCost(orderService.getDiscountCost(members.getLevel(), cost));
            order.setNote(note);
            order.setOrderTime(new Timestamp(System.currentTimeMillis()));
            order.setState("支付中");
            order.setMemberMoney(0.00);
            order.setResMoney(0.00);
            order.setAdminMoney(0.00);
            order.setOrderSinglesByOid(new ArrayList<>());
            order.setOrderPackagesByOid(new ArrayList<>());
            for (Address address : members.getAddressCollection()) {
                MemberAddress memberAddress = memberAddressService.getMemberAddress(id, address.getAid());
                if (memberAddress.getDefaultState() == 1) {
                    order.setAddress(address.getDescription());
                    break;
                }
            }
            orderService.saveOrder(order);
            if (singleName != null && singleName.length > 0) {
                for (int i = 0; i < singleName.length; i++) {
                    System.out.println(singleName[i]);
                    Singles singles = singleService.getSingleByNameAndRid(singleName[i], rid);
                    OrderSingle orderSingle = new OrderSingle();
                    orderSingle.setOid(oid);
                    orderSingle.setSid(singles.getSid());
                    orderSingle.setNum(singleNum[i]);
                    orderSingleService.addOrderSingle(orderSingle);
                    singles.setNum(singles.getNum() - singleNum[i]);
                    singleService.updateSingle(singles);
                    //order.getOrderSinglesByOid().add(orderSingle);
                }
            }
            if (packageName != null && packageName.length > 0) {
                for (int i = 0; i < packageName.length; i++) {
                    Packages packages = packageService.getPackageByNameAndRid(packageName[i], rid);
                    OrderPackage orderPackage = new OrderPackage();
                    orderPackage.setOid(oid);
                    orderPackage.setPid(packages.getPid());
                    orderPackage.setNum(packageNum[i]);
                    orderPackageService.addOrderPackage(orderPackage);
                    packages.setNum(packages.getNum() - packageNum[i]);
                    packageService.updatePackage(packages);
                    //order.getOrderPackagesByOid().add(orderPackage);
                }
            }
            res.put("oid", oid);
        } else {
            res.put("message", "error");
        }

        return res;
    }
}
