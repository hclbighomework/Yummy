package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Packages {
    private int pid;
    private String rid;
    private String name;
    private double cost;
    private String type;
    private Timestamp startTime;
    private Timestamp endTime;
    private int num;
    private Double discount;
    private int orderNum;
    @JsonIgnore
    private String picPath;
    @JsonIgnore
    private Collection<OrderPackage> orderPackagesByPid;
    @JsonIgnore
    private Restaurants restaurantsByRid;
    private Collection<Singles> singlesCollection;
    private String startTimeString;
    private String endTimeString;
    private String imgData;

    @Id
    @Column(name = "pid", nullable = false)
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "start_time", nullable = false)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time", nullable = false)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "num", nullable = false)
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "pic_path")
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Packages packages = (Packages) o;
        return pid == packages.pid &&
                Double.compare(packages.cost, cost) == 0 &&
                num == packages.num &&
                Objects.equals(rid, packages.rid) &&
                Objects.equals(name, packages.name) &&
                Objects.equals(type, packages.type) &&
                Objects.equals(startTime, packages.startTime) &&
                Objects.equals(endTime, packages.endTime) &&
                Objects.equals(discount, packages.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, rid, name, cost, type, startTime, endTime, num, discount);
    }

    @OneToMany(mappedBy = "packagesByPid")
    public Collection<OrderPackage> getOrderPackagesByPid() {
        return orderPackagesByPid;
    }

    public void setOrderPackagesByPid(Collection<OrderPackage> orderPackagesByPid) {
        this.orderPackagesByPid = orderPackagesByPid;
    }

    @ManyToOne
    @JoinColumn(name = "rid", referencedColumnName = "rid", nullable = false, insertable = false, updatable = false)
    public Restaurants getRestaurantsByRid() {
        return restaurantsByRid;
    }

    public void setRestaurantsByRid(Restaurants restaurantsByRid) {
        this.restaurantsByRid = restaurantsByRid;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "package_single", joinColumns = {@JoinColumn(name = "pid", referencedColumnName = "pid")},
    inverseJoinColumns = {@JoinColumn(name = "sid", referencedColumnName = "sid")})
    public Collection<Singles> getSinglesCollection() {
        return singlesCollection;
    }

    public void setSinglesCollection(Collection<Singles> singlesCollection) {
        this.singlesCollection = singlesCollection;
    }

    @Transient
    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    @Transient
    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    @Transient
    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    @Transient
    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }
}
