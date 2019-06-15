package com.nju.yummy.dao;

import com.nju.yummy.model.Singles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface SingleDAO extends CrudRepository<Singles, Integer> {

    @Query("from Singles where name like :name")
    public ArrayList<Singles> findAllByNameKey(String name);

    public Singles findByNameAndRid(String name, String rid);

    public ArrayList<Singles> findAllByRidAndStartTimeBeforeAndEndTimeAfter(String rid, Timestamp s, Timestamp e);

    public ArrayList<Singles> findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostAsc(String rid, Timestamp s, Timestamp e);

    public ArrayList<Singles> findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostDesc(String rid, Timestamp s, Timestamp e);

}
