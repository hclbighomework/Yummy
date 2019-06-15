package com.nju.yummy.dao;

import com.nju.yummy.model.OrderSingle;
import org.springframework.data.repository.CrudRepository;

public interface OrderSingleDAO extends CrudRepository<OrderSingle, Integer> {

    public OrderSingle findByOidAndSid(String oid, int sid);
}
