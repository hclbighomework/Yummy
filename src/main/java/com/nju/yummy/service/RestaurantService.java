package com.nju.yummy.service;

import com.nju.yummy.model.Restaurants;

import java.util.ArrayList;

public interface RestaurantService {

    public Restaurants getRestaurantById(String id);

    public Restaurants getRestaurantByName(String name);

    public String register(Restaurants restaurants);

    public Restaurants login(String id, String password);

    public void updateRestaurant(Restaurants restaurants);

    public Iterable<Restaurants> getRestaurantByType(String type);

    public Iterable<Restaurants> getUnApprovedRestaurants();

    Iterable<Restaurants> getApprovedRestaurants();

    public Iterable<Restaurants> findAll();

    public ArrayList<String> getAllRid();

    public ArrayList<Restaurants> getByNameKey(String name);

}
