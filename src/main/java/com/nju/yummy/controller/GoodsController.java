package com.nju.yummy.controller;

import com.nju.yummy.model.PackageSingle;
import com.nju.yummy.model.Packages;
import com.nju.yummy.model.Restaurants;
import com.nju.yummy.model.Singles;
import com.nju.yummy.service.PackageService;
import com.nju.yummy.service.PackageSingleService;
import com.nju.yummy.service.RestaurantService;
import com.nju.yummy.service.SingleService;
import com.nju.yummy.util.DateUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GoodsController {

    @Resource
    private RestaurantService restaurantService;
    @Resource
    private SingleService singleService;
    @Resource
    private PackageService packageService;
    @Resource
    private PackageSingleService packageSingleService;

    @RequestMapping(value = "/getGoods", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getGoodsForRestaurant(HttpServletRequest request) {
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
            Collection<Singles> singlesCollection = restaurant.getSinglesByRid();
            Collection<Packages> packagesCollection = restaurant.getPackagesByRid();
            for (Singles singles : singlesCollection) {
                singles.setStartTimeString(DateUtil.timestampToString(singles.getStartTime()));
                singles.setEndTimeString(DateUtil.timestampToString(singles.getEndTime()));
            }
            for (Packages packages : packagesCollection) {
                packages.setStartTimeString(DateUtil.timestampToString(packages.getStartTime()));
                packages.setEndTimeString(DateUtil.timestampToString(packages.getEndTime()));
            }
            res.put("singleList", restaurant.getSinglesByRid());
            res.put("packageList", restaurant.getPackagesByRid());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getPackageSingle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPackageInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            String pName = map.get("packageName").toString();
            Packages packages = packageService.getPackageByNameAndRid(pName, rid);
            for (Singles singles : packages.getSinglesCollection()) {
                singles.setPackageNum(packageSingleService.getPackageSingle(packages.getPid(), singles.getSid()).getNum());
            }
            res.put("message", "success");
            res.put("singles", packages.getSinglesCollection());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/addSingle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addSingle(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            String name = map.get("singleName").toString();
            if (singleService.getSingleByNameAndRid(name, rid) != null) {
                res.put("message", "single");
            } else {
                Singles single = new Singles();
                single.setRid(rid);
                single.setName(name);
                single.setCost(Double.parseDouble(map.get("singleCost").toString()));
                single.setNum(Integer.parseInt(map.get("singleNum").toString()));
                single.setType(map.get("singleType").toString());
                single.setDiscount(Double.valueOf(map.get("singleDiscount").toString()));
                single.setStartTime(DateUtil.stringToTimestamp(map.get("startTime").toString()));
                single.setEndTime(DateUtil.stringToTimestamp(map.get("endTime").toString()));
                singleService.addSingle(single);
                Collection<Singles> singlesCollection = restaurantService.getRestaurantById(rid).getSinglesByRid();
                for (Singles singles : singlesCollection) {
                    singles.setStartTimeString(DateUtil.timestampToString(singles.getStartTime()));
                    singles.setEndTimeString(DateUtil.timestampToString(singles.getEndTime()));
                }
                res.put("message", "success");
                res.put("singleList", singlesCollection);
            }

        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateSingle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateSingle(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            String name = map.get("singleName").toString();
            Singles single = singleService.getSingleByNameAndRid(name, rid);
            single.setCost(Double.parseDouble(map.get("singleCost").toString()));
            single.setNum(Integer.parseInt(map.get("singleNum").toString()));
            single.setType(map.get("singleType").toString());
            single.setDiscount(Double.valueOf(map.get("singleDiscount").toString()));
            single.setStartTime(DateUtil.stringToTimestamp(map.get("startTime").toString()));
            single.setEndTime(DateUtil.stringToTimestamp(map.get("endTime").toString()));
            singleService.updateSingle(single);
            Collection<Singles> singlesCollection = restaurantService.getRestaurantById(rid).getSinglesByRid();
            for (Singles singles : singlesCollection) {
                singles.setStartTimeString(DateUtil.timestampToString(singles.getStartTime()));
                singles.setEndTimeString(DateUtil.timestampToString(singles.getEndTime()));
            }
            res.put("message", "success");
            res.put("singleList", singlesCollection);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/addPackage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPackage(String[] singleName, int[] singleNum, String packageName, double packageCost, int packageNum, String packageType,
                                          int packageDiscount, String startTime, String endTime, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        Restaurants restaurant = restaurantService.getRestaurantById(rid);
        if (restaurant != null) {
            if (packageService.getPackageByNameAndRid(packageName, rid) != null) {
                res.put("message", "package");
                return res;
            }
            res.put("message", "success");
            Packages packages = new Packages();
            packages.setName(packageName);
            packages.setNum(packageNum);
            packages.setCost(packageCost);
            packages.setType(packageType);
            packages.setDiscount((double) packageDiscount);
            packages.setStartTime(DateUtil.stringToTimestamp(startTime));
            packages.setEndTime(DateUtil.stringToTimestamp(endTime));
            packages.setRid(rid);
            packageService.addPackage(packages);
            int pid = packageService.getPackageByNameAndRid(packageName, rid).getPid();
            Map<String, Integer> singleMap = new HashMap<>();
            for (int i = 0; i < singleName.length; i++) {
                if (!singleMap.containsKey(singleName[i])) {
                    singleMap.put(singleName[i], singleNum[i]);
                } else {
                    singleMap.put(singleName[i], singleMap.get(singleName[i]) + singleNum[i]);
                }
            }
            for (String s : singleMap.keySet()) {
                PackageSingle packageSingle = new PackageSingle();
                packageSingle.setNum(singleMap.get(s));
                packageSingle.setPid(pid);
                packageSingle.setSid(singleService.getSingleByNameAndRid(s, rid).getSid());
                packageSingleService.addPackageSingle(packageSingle);
            }
            Collection<Packages> packagesCollection = restaurantService.getRestaurantById(rid).getPackagesByRid();
            for (Packages p : packagesCollection) {
                p.setStartTimeString(DateUtil.timestampToString(p.getStartTime()));
                p.setEndTimeString(DateUtil.timestampToString(p.getEndTime()));
            }
            res.put("packages", packagesCollection);
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updatePackage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePackage(String[] singleName, int[] singleNum, String packageName, double packageCost, int packageNum, String packageType,
                                          int packageDiscount, String startTime, String endTime, HttpServletRequest request) {
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
            Packages packages = packageService.getPackageByNameAndRid(packageName, rid);
            packages.setNum(packageNum);
            packages.setCost(packageCost);
            packages.setType(packageType);
            packages.setDiscount((double) packageDiscount);
            packages.setStartTime(DateUtil.stringToTimestamp(startTime));
            packages.setEndTime(DateUtil.stringToTimestamp(endTime));
            packageService.updatePackage(packages);
            for (Singles singles : packages.getSinglesCollection()) {
                packageSingleService.deletePackageSingle(packages.getPid(), singles.getSid());
            }
            int pid = packageService.getPackageByNameAndRid(packageName, rid).getPid();
            Map<String, Integer> singleMap = new HashMap<>();
            for (int i = 0; i < singleName.length; i++) {
                if (!singleMap.containsKey(singleName[i])) {
                    singleMap.put(singleName[i], singleNum[i]);
                } else {
                    singleMap.put(singleName[i], singleMap.get(singleName[i]) + singleNum[i]);
                }
            }
            for (String s : singleMap.keySet()) {
                PackageSingle packageSingle = new PackageSingle();
                packageSingle.setNum(singleMap.get(s));
                packageSingle.setPid(pid);
                packageSingle.setSid(singleService.getSingleByNameAndRid(s, rid).getSid());
                packageSingleService.addPackageSingle(packageSingle);
            }
            Collection<Packages> packagesCollection = restaurantService.getRestaurantById(rid).getPackagesByRid();
            for (Packages p : packagesCollection) {
                p.setStartTimeString(DateUtil.timestampToString(p.getStartTime()));
                p.setEndTimeString(DateUtil.timestampToString(p.getEndTime()));
            }
            res.put("packages", packagesCollection);
        } else {
            res.put("message", "error");
        }
        return res;
    }
}
