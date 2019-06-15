package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Restaurants {
    private String rid;
    @JsonIgnore
    private String password;
    private String name;
    private String type;
    private int aid;
    private String phone;
    private String description;
    private int state;
    private double minCost;
    private double humanCost;
    private double totalMoney;
    @JsonIgnore
    private Collection<Orders> ordersByRid;
    //@JsonIgnore
    private Collection<Packages> packagesByRid;
    //@JsonIgnore
    private Collection<Reduction> reductionsByRid;
    private Address addressByAid;
    //@JsonIgnore
    private Collection<Singles> singlesByRid;

    public Restaurants() {

    }

    public Restaurants(String rid, String name, String password, String type, String phone, String description, int aid) {
        this.rid = rid;
        this.name = name;
        this.password = password;
        this.type = type;
        this.phone = phone;
        this.description = description;
        this.aid = aid;
        this.state = 0;
        this.minCost = 0.00;
        this.humanCost = 0.00;
        this.totalMoney = 0.00;
    }

    @Id
    @Column(name = "rid", nullable = false, length = 7)
    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "aid", nullable = false)
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 11)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "state", nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "min_cost", nullable = false)
    public double getMinCost() {
        return minCost;
    }

    public void setMinCost(double minCost) {
        this.minCost = minCost;
    }

    @Basic
    @Column(name = "human_cost", nullable = false)
    public double getHumanCost() {
        return humanCost;
    }

    public void setHumanCost(double humanCost) {
        this.humanCost = humanCost;
    }

    @Basic
    @Column(name = "total_money")
    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurants that = (Restaurants) o;
        return aid == that.aid && minCost == that.minCost && humanCost == that.humanCost && totalMoney == that.totalMoney &&
                Objects.equals(rid, that.rid) &&
                Objects.equals(password, that.password) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rid, password, name, type, aid, phone, description, minCost, humanCost);
    }

    @OneToMany(mappedBy = "restaurantsByRid")
    public Collection<Orders> getOrdersByRid() {
        return ordersByRid;
    }

    public void setOrdersByRid(Collection<Orders> ordersByRid) {
        this.ordersByRid = ordersByRid;
    }

    @OneToMany(mappedBy = "restaurantsByRid")
    public Collection<Packages> getPackagesByRid() {
        return packagesByRid;
    }

    public void setPackagesByRid(Collection<Packages> packagesByRid) {
        this.packagesByRid = packagesByRid;
    }

    @OneToMany(mappedBy = "restaurantsByRid")
    public Collection<Reduction> getReductionsByRid() {
        return reductionsByRid;
    }

    public void setReductionsByRid(Collection<Reduction> reductionsByRid) {
        this.reductionsByRid = reductionsByRid;
    }

    @ManyToOne
    @JoinColumn(name = "aid", referencedColumnName = "aid", nullable = false, insertable = false, updatable = false)
    public Address getAddressByAid() {
        return addressByAid;
    }

    public void setAddressByAid(Address addressByAid) {
        this.addressByAid = addressByAid;
    }

    @OneToMany(mappedBy = "restaurantsByRid")
    public Collection<Singles> getSinglesByRid() {
        return singlesByRid;
    }

    public void setSinglesByRid(Collection<Singles> singlesByRid) {
        this.singlesByRid = singlesByRid;
    }

}
