package com.nju.yummy.service;

import com.nju.yummy.model.OrderSingle;

public interface OrderSingleService {

    public void addOrderSingle(OrderSingle orderSingle);

    public OrderSingle getOrderSingle(String oid, int sid);
}
