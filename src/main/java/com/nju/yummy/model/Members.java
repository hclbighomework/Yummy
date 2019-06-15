package com.nju.yummy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Members {
    private int mid;
    private String email;
    private String phone;
    private String name;
    private Integer level;
    private Double balance;
    @JsonIgnore
    private String password;
    private int state;
    private Integer experience;
    private String code;
    private double totalCost;
    @JsonIgnore
    private Collection<Orders> ordersByMid;
    private Collection<Address> addressCollection;

    @Id
    @Column(name = "mid", nullable = false)
    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "level", nullable = true)
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Basic
    @Column(name = "balance", nullable = true, precision = 2)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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
    @Column(name = "state", nullable = false)
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column(name = "experience", nullable = true)
    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 32)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "total_cost")
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Members members = (Members) o;
        return mid == members.mid &&
                state == members.state &&
                totalCost == members.totalCost &&
                Objects.equals(email, members.email) &&
                Objects.equals(phone, members.phone) &&
                Objects.equals(name, members.name) &&
                Objects.equals(level, members.level) &&
                Objects.equals(balance, members.balance) &&
                Objects.equals(password, members.password) &&
                Objects.equals(experience, members.experience) &&
                Objects.equals(code, members.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mid, email, phone, name, level, balance, password, state, experience, code, totalCost);
    }

    @OneToMany(mappedBy = "membersByMid")
    public Collection<Orders> getOrdersByMid() {
        return ordersByMid;
    }

    public void setOrdersByMid(Collection<Orders> ordersByMid) {
        this.ordersByMid = ordersByMid;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "member_address", joinColumns = {@JoinColumn(name = "mid", referencedColumnName = "mid")},
    inverseJoinColumns = {@JoinColumn(name = "aid", referencedColumnName = "aid")})
    public Collection<Address> getAddressCollection() {
        return addressCollection;
    }

    public void setAddressCollection(Collection<Address> addressCollection) {
        this.addressCollection = addressCollection;
    }


}
