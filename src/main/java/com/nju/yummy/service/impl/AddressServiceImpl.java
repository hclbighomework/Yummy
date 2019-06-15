package com.nju.yummy.service.impl;

import com.nju.yummy.dao.AddressDAO;
import com.nju.yummy.model.Address;
import com.nju.yummy.service.AddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDAO addressDAO;

    @Override
    public Address getAddressByDescription(String description) {
        return addressDAO.findAddressByDescription(description);
    }

    @Override
    public Iterable<String> getAllDescription() {
        return addressDAO.findAllDescription();
    }
}
