package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Reduction {
    private int id;
    private String rid;
    private double fullCost;
    private double reduceCost;
    @JsonIgnore
    private Restaurants restaurantsByRid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rid", nullable = false, length = 7)
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "full_cost", nullable = false, precision = 0)
    public double getFullCost() {
        return fullCost;
    }

    public void setFullCost(double fullCost) {
        this.fullCost = fullCost;
    }

    @Basic
    @Column(name = "reduce_cost", nullable = false, precision = 0)
    public double getReduceCost() {
        return reduceCost;
    }

    public void setReduceCost(double reduceCost) {
        this.reduceCost = reduceCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reduction reduction = (Reduction) o;
        return id == reduction.id &&
                Double.compare(reduction.fullCost, fullCost) == 0 &&
                Double.compare(reduction.reduceCost, reduceCost) == 0 &&
                Objects.equals(rid, reduction.rid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rid, fullCost, reduceCost);
    }

    @ManyToOne
    @JoinColumn(name = "rid", referencedColumnName = "rid", nullable = false, insertable = false, updatable = false)
    public Restaurants getRestaurantsByRid() {
        return restaurantsByRid;
    }

    public void setRestaurantsByRid(Restaurants restaurantsByRid) {
        this.restaurantsByRid = restaurantsByRid;
    }
}
