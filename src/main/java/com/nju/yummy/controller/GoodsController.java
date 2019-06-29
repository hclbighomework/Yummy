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
import com.nju.yummy.util.ImageUtil;
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

    @RequestMapping(value = "/showSingleList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showSingleList(HttpServletRequest request) {
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
            for (Singles singles : singlesCollection) {
                singles.setStartTimeString(DateUtil.timestampToString(singles.getStartTime()));
                singles.setEndTimeString(DateUtil.timestampToString(singles.getEndTime()));
                singles.setImgData(ImageUtil.imageToBase64(singles.getPicPath()));
            }
            res.put("singleList", restaurant.getSinglesByRid());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/showPackageList", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showPackageList(HttpServletRequest request) {
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
            Collection<Packages> packagesCollection = restaurant.getPackagesByRid();
            for (Packages packages : packagesCollection) {
                packages.setStartTimeString(DateUtil.timestampToString(packages.getStartTime()));
                packages.setEndTimeString(DateUtil.timestampToString(packages.getEndTime()));
                packages.setImgData(ImageUtil.imageToBase64(packages.getPicPath()));
            }
            res.put("packageList", restaurant.getPackagesByRid());
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/getSingleInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSingleInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        int sid = Integer.parseInt(map.get("sid").toString());
        Singles singles = singleService.getSingleBySid(sid);
        String rid = session.getAttribute("id").toString();
        if (singles == null || !singles.getRid().equals(rid)) {
            res.put("message", "error");
        } else {
            singles.setImgData(ImageUtil.imageToBase64(singles.getPicPath()));
            singles.setStartTimeString(DateUtil.timestampToString(singles.getStartTime()));
            singles.setEndTimeString(DateUtil.timestampToString(singles.getEndTime()));
            res.put("single", singles);
            res.put("message", "success");
            session.setAttribute("sid", sid);
        }
        return res;
    }

    @RequestMapping(value = "/getPInfo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPackageInfo(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant")) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        int pid = Integer.parseInt(map.get("pid").toString());
        Packages packages = packageService.getPackageByID(pid);
        if (packages == null || !packages.getRid().equals(rid)) {
            res.put("message", "error");
        } else {
            packages.setImgData(ImageUtil.imageToBase64(packages.getPicPath()));
            packages.setStartTimeString(DateUtil.timestampToString(packages.getStartTime()));
            packages.setEndTimeString(DateUtil.timestampToString(packages.getEndTime()));
            for (Singles singles : packages.getSinglesCollection()) {
                singles.setPackageNum(packageSingleService.getPackageSingle(packages.getPid(), singles.getSid()).getNum());
            }
            session.setAttribute("pid", pid);
            res.put("message", "success");
            res.put("singles", packages.getSinglesCollection());
            res.put("packages", packages);
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
                single = singleService.getSingleByNameAndRid(name, rid);
                String imgData = map.get("imgData").toString();
                if (imgData.contains(".")) {
                    single.setPicPath("src/main/resources/static/image/single_icon/single_default.png");
                } else {
                    String path = "src/main/resources/static/image/single_icon/single_" + single.getSid() + ".png";
                    ImageUtil.base64ToImage(imgData, path);
                    single.setPicPath(path);
                }
                singleService.updateSingle(single);
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
        if (session == null || !session.getAttribute("type").equals("restaurant") || session.getAttribute("sid") == null) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        int sid = Integer.parseInt(session.getAttribute("sid").toString());
        Singles singles = singleService.getSingleBySid(sid);
        if (singles == null || !singles.getRid().equals(rid)) {
            res.put("message", "error");
        } else {
            String name = map.get("singleName").toString();
            Singles s = singleService.getSingleByNameAndRid(name, rid);
            if (s != null && s.getSid() != sid) {
                res.put("message", "single");
            } else {
                res.put("message", "success");
                String path = "src/main/resources/static/image/single_icon/single_" + sid + ".png";
                String imgData = map.get("imgData").toString();
                //System.out.println(imgData);
                ImageUtil.base64ToImage(imgData, path);
                singles.setName(name);
                singles.setPicPath(path);
                singles.setCost(Double.parseDouble(map.get("singleCost").toString()));
                singles.setNum(Integer.parseInt(map.get("singleNum").toString()));
                singles.setType(map.get("singleType").toString());
                singles.setDiscount(Double.valueOf(map.get("singleDiscount").toString()));
                singles.setStartTime(DateUtil.stringToTimestamp(map.get("startTime").toString()));
                singles.setEndTime(DateUtil.stringToTimestamp(map.get("endTime").toString()));
                singleService.addSingle(singles);
            }
        }
        return res;
    }

    @RequestMapping(value = "/addPackage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addPackage(String[] singleName, int[] singleNum, String packageName, double packageCost, int packageNum, String packageType,
                                          int packageDiscount, String startTime, String endTime, String imgData, HttpServletRequest request) {
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
            packages = packageService.getPackageByNameAndRid(packageName, rid);
            int pid = packages.getPid();
            if (imgData.contains(".")) {
                packages.setPicPath("src/main/resources/static/image/package_icon/package_default.png");
            } else {
                String path = "src/main/resources/static/image/package_icon/package_" + pid + ".png";
                ImageUtil.base64ToImage(imgData, path);
                packages.setPicPath(path);
            }
            packageService.updatePackage(packages);
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
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updatePackage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePackage(String[] singleName, int[] singleNum, String packageName, double packageCost, int packageNum, String packageType,
                                          int packageDiscount, String startTime, String endTime, String imgData, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("restaurant") || session.getAttribute("pid") == null) {
            res.put("message", "error");
            return res;
        }
        String rid = session.getAttribute("id").toString();
        int pid = Integer.parseInt(session.getAttribute("pid").toString());
        Packages packages = packageService.getPackageByID(pid);
        if (packages == null || !packages.getRid().equals(rid)) {
            res.put("message", "error");
        } else {
            Packages p = packageService.getPackageByNameAndRid(packageName, rid);
            if (p != null && p.getPid() != pid) {
                res.put("message", "package");
            } else {
                res.put("message", "success");
                String path = "src/main/resources/static/image/package_icon/package_" + pid + ".png";
                //System.out.println(imgData);
                ImageUtil.base64ToImage(imgData, path);
                packages.setPicPath(path);
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
                for (Packages pack : packagesCollection) {
                    pack.setStartTimeString(DateUtil.timestampToString(pack.getStartTime()));
                    pack.setEndTimeString(DateUtil.timestampToString(pack.getEndTime()));
                }
                res.put("packages", packagesCollection);
            }
        }
        return res;
    }
}
