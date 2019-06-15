package com.nju.yummy.dao;

import com.nju.yummy.model.Restaurants;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RestaurantDAO extends CrudRepository<Restaurants, String> {

    public Restaurants findByName(String name);

    public Iterable<Restaurants> findAllByType(String type);

    public Iterable<Restaurants> findAllByState(int state);

    @Query("select rid from Restaurants")
    public ArrayList<String> getAllRid();

    @Query("from Restaurants where name like :name")
    public ArrayList<Restaurants> getByNameKey(String name);
}
