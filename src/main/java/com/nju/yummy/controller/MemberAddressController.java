package com.nju.yummy.controller;

import com.nju.yummy.model.Address;
import com.nju.yummy.model.MemberAddress;
import com.nju.yummy.model.Members;
import com.nju.yummy.service.AddressService;
import com.nju.yummy.service.MemberAddressService;
import com.nju.yummy.service.MembersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberAddressController {

    @Resource
    private MembersService membersService;
    @Resource
    private MemberAddressService memberAddressService;
    @Resource
    private AddressService addressService;

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> setDefault(@RequestParam Map<String, Object> map, HttpServletRequest request) {
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
            String description = map.get("description").toString();
            memberAddressService.setAddressDefault(id, addressService.getAddressByDescription(description).getAid());
            Collection<Address> addressCollection = membersService.getMemberById(id).getAddressCollection();
            res.put("addressList", setAddressesDefaultState(addressCollection, id));
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addAddress(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            String newAddress = map.get("newAddress").toString();
            Address address = addressService.getAddressByDescription(newAddress);
            if (memberAddressService.getMemberAddress(id, address.getAid()) != null) {
                res.put("message", "address");
            } else {
                res.put("message", "success");
                MemberAddress memberAddress = new MemberAddress();
                memberAddress.setAid(address.getAid());
                memberAddress.setMid(id);
                memberAddress.setDefaultState((byte) 0);
                memberAddressService.addMemberAddress(memberAddress);
                Collection<Address> addressCollection = membersService.getMemberById(id).getAddressCollection();
                res.put("addressList", setAddressesDefaultState(addressCollection, id));
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAddress(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            String oldAddress = map.get("oldAddress").toString();
            String newAddress = map.get("newAddress").toString();
            Address oldA = addressService.getAddressByDescription(oldAddress);
            Address newA = addressService.getAddressByDescription(newAddress);
            if (memberAddressService.getMemberAddress(id, newA.getAid()) != null) {
                res.put("message", "address");
            } else {
                res.put("message", "success");
                MemberAddress memberAddress = memberAddressService.getMemberAddress(id, oldA.getAid());
                memberAddress.setAid(newA.getAid());
                memberAddressService.updateMemberAddress(memberAddress);
                Collection<Address> addressCollection = membersService.getMemberById(id).getAddressCollection();
                res.put("addressList", setAddressesDefaultState(addressCollection, id));
            }
        } else {
            res.put("message", "error");
        }
        return res;
    }

    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteAddress(@RequestParam Map<String, Object> map, HttpServletRequest request) {
        Map<String, Object> res = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session == null || !session.getAttribute("type").equals("member")) {
            res.put("message", "error");
            return res;
        }
        int id = Integer.parseInt(session.getAttribute("id").toString());
        Members members = membersService.getMemberById(id);
        if (members != null) {
            String description = map.get("description").toString();
            Address address = addressService.getAddressByDescription(description);
            memberAddressService.deleteMemberAddress(memberAddressService.getMemberAddress(id, address.getAid()));
            res.put("message", "success");
            Collection<Address> addressCollection = membersService.getMemberById(id).getAddressCollection();
            res.put("addressList", setAddressesDefaultState(addressCollection, id));
        } else {
            res.put("message", "error");
        }
        return res;
    }

    private Collection<Address> setAddressesDefaultState(Collection<Address> addressCollection, int mid) {
        for (Address address : addressCollection) {
            address.setDefaultState(memberAddressService.getMemberAddress(mid, address.getAid()).getDefaultState());
        }
        return addressCollection;
    }
}
