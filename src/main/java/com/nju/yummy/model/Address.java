package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Address {
    private int aid;
    private String description;
    @JsonIgnore
    private Collection<Restaurants> restaurantsByAid;
    @JsonIgnore
    private Collection<Members> membersCollection;
    private int defaultState;

    @Id
    @Column(name = "aid", nullable = false)
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return aid == address.aid &&
                Objects.equals(description, address.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aid, description);
    }

    @OneToMany(mappedBy = "addressByAid")
    public Collection<Restaurants> getRestaurantsByAid() {
        return restaurantsByAid;
    }

    public void setRestaurantsByAid(Collection<Restaurants> restaurantsByAid) {
        this.restaurantsByAid = restaurantsByAid;
    }

    @ManyToMany(mappedBy = "addressCollection", cascade = CascadeType.ALL)
    public Collection<Members> getMembersCollection() {
        return membersCollection;
    }

    public void setMembersCollection(Collection<Members> membersCollection) {
        this.membersCollection = membersCollection;
    }

    @Transient
    public int getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(int defaultState) {
        this.defaultState = defaultState;
    }
}
