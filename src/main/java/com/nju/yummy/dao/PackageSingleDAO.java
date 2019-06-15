package com.nju.yummy.dao;

import com.nju.yummy.model.PackageSingle;
import org.springframework.data.repository.CrudRepository;

public interface PackageSingleDAO extends CrudRepository<PackageSingle, Integer> {

    public PackageSingle findByPidAndSid(int pid, int sid);
}
