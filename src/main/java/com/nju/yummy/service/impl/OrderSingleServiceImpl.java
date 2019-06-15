package com.nju.yummy.service.impl;

import com.nju.yummy.dao.OrderSingleDAO;
import com.nju.yummy.model.OrderSingle;
import com.nju.yummy.service.OrderSingleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderSingleServiceImpl implements OrderSingleService {

    @Resource
    private OrderSingleDAO orderSingleDAO;

    @Transactional
    @Override
    public void addOrderSingle(OrderSingle orderSingle) {
        orderSingleDAO.save(orderSingle);
    }

    @Override
    public OrderSingle getOrderSingle(String oid, int sid) {
        return orderSingleDAO.findByOidAndSid(oid, sid);
    }
}
