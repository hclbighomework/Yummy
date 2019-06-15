package com.nju.yummy.service.impl;

import com.nju.yummy.dao.RestaurantDAO;
import com.nju.yummy.model.Restaurants;
import com.nju.yummy.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Resource
    private RestaurantDAO restaurantDAO;

    @Override
    public Restaurants getRestaurantById(String id) {
        Optional<Restaurants> restaurants = restaurantDAO.findById(id);
        return restaurants.orElse(null);
    }

    @Override
    public Restaurants getRestaurantByName(String name) {
        return restaurantDAO.findByName(name);
    }

    @Transactional
    @Override
    public String register(Restaurants restaurants) {
        if (getRestaurantByName(restaurants.getName()) != null) {
            return "error";
        }
        restaurantDAO.save(restaurants);
        return "success";
    }

    @Override
    public Restaurants login(String id, String password) {
        Restaurants restaurants = getRestaurantById(id);
        if (restaurants == null || !restaurants.getPassword().equals(password)) {
            return null;
        }
        return restaurants;
    }

    @Transactional
    @Override
    public void updateRestaurant(Restaurants restaurants) {
        restaurantDAO.save(restaurants);
    }

    @Override
    public Iterable<Restaurants> getRestaurantByType(String type) {
        return restaurantDAO.findAllByType(type);
    }

    @Override
    public Iterable<Restaurants> getUnApprovedRestaurants() {
        return restaurantDAO.findAllByState(0);
    }

    @Override
    public Iterable<Restaurants> getApprovedRestaurants() {
        return restaurantDAO.findAllByState(1);
    }

    @Override
    public Iterable<Restaurants> findAll() {
        return restaurantDAO.findAll();
    }

    @Override
    public ArrayList<String> getAllRid() {
        return restaurantDAO.getAllRid();
    }

    @Override
    public ArrayList<Restaurants> getByNameKey(String name) {
        return restaurantDAO.getByNameKey("%" + name + "%");
    }
}
