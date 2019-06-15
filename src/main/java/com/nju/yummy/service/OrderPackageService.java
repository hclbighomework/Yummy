package com.nju.yummy.service;

import com.nju.yummy.model.OrderPackage;

public interface OrderPackageService {

    public void addOrderPackage(OrderPackage orderPackage);

    public OrderPackage getOrderPackage(String oid, int pid);
}
