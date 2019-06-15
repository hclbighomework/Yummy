package com.nju.yummy.service.impl;

import com.nju.yummy.dao.OrderPackageDAO;
import com.nju.yummy.model.OrderPackage;
import com.nju.yummy.service.OrderPackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OrderPackageServiceImpl implements OrderPackageService {

    @Resource
    private OrderPackageDAO orderPackageDAO;

    @Transactional
    @Override
    public void addOrderPackage(OrderPackage orderPackage) {
        orderPackageDAO.save(orderPackage);
    }

    @Override
    public OrderPackage getOrderPackage(String oid, int pid) {
        return orderPackageDAO.getByOidAndPid(oid, pid);
    }
}
