package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Orders {
    private String oid;
    private int mid;
    private String rid;
    @JsonIgnore
    private Timestamp orderTime;
    @JsonIgnore
    private Timestamp arriveTime;
    private double cost;
    private double resMoney;
    private double adminMoney;
    private double memberMoney;
    private String note;
    private String state;
    private String address;
    private Timestamp deliveryTime;
    @JsonIgnore
    private Collection<OrderPackage> orderPackagesByOid;
    @JsonIgnore
    private Collection<OrderSingle> orderSinglesByOid;
    private Collection<Singles> singlesCollection;
    private Collection<Packages> packagesCollection;
    @JsonIgnore
    private Members membersByMid;
    @JsonIgnore
    private Restaurants restaurantsByRid;
    private String orderTimeString;
    private String arriveTimeString;
    private String content;
    private String restaurantName;
    private String memberName;

    @Id
    @Column(name = "oid", nullable = false, length = 20)
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "mid", nullable = false)
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
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
    @Column(name = "order_time", nullable = false)
    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Basic
    @Column(name = "arrive_time", nullable = true)
    public Timestamp getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Timestamp arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Basic
    @Column(name = "cost", nullable = false, precision = 2)
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Basic
    @Column(name = "note", nullable = true, length = 100)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "delivery_time")
    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Basic
    @Column(name = "res_money")
    public double getResMoney() {
        return resMoney;
    }

    public void setResMoney(double resMoney) {
        this.resMoney = resMoney;
    }

    @Basic
    @Column(name = "admin_money")
    public double getAdminMoney() {
        return adminMoney;
    }

    public void setAdminMoney(double adminMoney) {
        this.adminMoney = adminMoney;
    }

    @Basic
    @Column(name = "member_money")
    public double getMemberMoney() {
        return memberMoney;
    }

    public void setMemberMoney(double memberMoney) {
        this.memberMoney = memberMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return mid == orders.mid &&
                Double.compare(orders.cost, cost) == 0 &&
                Objects.equals(oid, orders.oid) &&
                Objects.equals(rid, orders.rid) &&
                Objects.equals(orderTime, orders.orderTime) &&
                Objects.equals(arriveTime, orders.arriveTime) &&
                Objects.equals(note, orders.note) &&
                Objects.equals(state, orders.state) &&
                Objects.equals(address, orders.address) &&
                Objects.equals(deliveryTime, orders.deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oid, mid, rid, orderTime, arriveTime, cost, note, state, address, deliveryTime);
    }

    @OneToMany(mappedBy = "ordersByOid", cascade = CascadeType.ALL)
    public Collection<OrderPackage> getOrderPackagesByOid() {
        return orderPackagesByOid;
    }

    public void setOrderPackagesByOid(Collection<OrderPackage> orderPackagesByOid) {
        this.orderPackagesByOid = orderPackagesByOid;
    }

    @OneToMany(mappedBy = "ordersByOid", cascade = CascadeType.ALL)
    public Collection<OrderSingle> getOrderSinglesByOid() {
        return orderSinglesByOid;
    }

    public void setOrderSinglesByOid(Collection<OrderSingle> orderSinglesByOid) {
        this.orderSinglesByOid = orderSinglesByOid;
    }


    @ManyToOne
    @JoinColumn(name = "mid", referencedColumnName = "mid", nullable = false, insertable = false, updatable = false)
    public Members getMembersByMid() {
        return membersByMid;
    }

    public void setMembersByMid(Members membersByMid) {
        this.membersByMid = membersByMid;
    }

    @ManyToOne
    @JoinColumn(name = "rid", referencedColumnName = "rid", nullable = false, insertable = false, updatable = false)
    public Restaurants getRestaurantsByRid() {
        return restaurantsByRid;
    }

    public void setRestaurantsByRid(Restaurants restaurantsByRid) {
        this.restaurantsByRid = restaurantsByRid;
    }

    @ManyToMany
    @JoinTable(name = "order_single", joinColumns = {@JoinColumn(name = "oid", referencedColumnName = "oid")},
            inverseJoinColumns = {@JoinColumn(name = "sid", referencedColumnName = "sid")})
    public Collection<Singles> getSinglesCollection() {
        return singlesCollection;
    }

    public void setSinglesCollection(Collection<Singles> singlesCollection) {
        this.singlesCollection = singlesCollection;
    }

    @ManyToMany
    @JoinTable(name = "order_package", joinColumns = {@JoinColumn(name = "oid", referencedColumnName = "oid")},
            inverseJoinColumns = {@JoinColumn(name = "pid", referencedColumnName = "pid")})
    public Collection<Packages> getPackagesCollection() {
        return packagesCollection;
    }

    public void setPackagesCollection(Collection<Packages> packagesCollection) {
        this.packagesCollection = packagesCollection;
    }

    @Transient
    public String getOrderTimeString() {
        return orderTimeString;
    }

    public void setOrderTimeString(String orderTimeString) {
        this.orderTimeString = orderTimeString;
    }

    @Transient
    public String getArriveTimeString() {
        return arriveTimeString;
    }

    public void setArriveTimeString(String arriveTimeString) {
        this.arriveTimeString = arriveTimeString;
    }

    @Transient
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Transient
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Transient
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
