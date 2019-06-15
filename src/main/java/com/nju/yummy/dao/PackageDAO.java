package com.nju.yummy.dao;

import com.nju.yummy.model.Packages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface PackageDAO extends CrudRepository<Packages, Integer> {

    @Query("from Packages where name like :name")
    public ArrayList<Packages> findAllByNameKey(String name);

    public Packages findByNameAndRid(String name, String rid);

    public ArrayList<Packages> findAllByRidAndStartTimeBeforeAndEndTimeAfter(String rid, Timestamp s, Timestamp e);

    public ArrayList<Packages> findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostAsc(String rid, Timestamp s, Timestamp e);

    public ArrayList<Packages> findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostDesc(String rid, Timestamp s, Timestamp e);
}
