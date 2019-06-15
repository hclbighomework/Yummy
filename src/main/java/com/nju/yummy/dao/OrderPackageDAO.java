package com.nju.yummy.dao;

import com.nju.yummy.model.OrderPackage;
import org.springframework.data.repository.CrudRepository;

public interface OrderPackageDAO extends CrudRepository<OrderPackage, Integer> {

    public OrderPackage getByOidAndPid(String oid, int pid);
}
