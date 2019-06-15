package com.nju.yummy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_single", schema = "yummy", catalog = "")
public class OrderSingle {
    private int id;
    private String oid;
    private int sid;
    private int num;
    private Orders ordersByOid;
    private Singles singlesBySid;

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
    @Column(name = "oid", nullable = false, length = 20)
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "sid", nullable = false)
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "num", nullable = false)
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderSingle that = (OrderSingle) o;
        return id == that.id &&
                sid == that.sid &&
                num == that.num &&
                Objects.equals(oid, that.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oid, sid, num);
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oid", referencedColumnName = "oid", nullable = false, insertable = false, updatable = false)
    public Orders getOrdersByOid() {
        return ordersByOid;
    }

    public void setOrdersByOid(Orders ordersByOid) {
        this.ordersByOid = ordersByOid;
    }

    @ManyToOne
    @JoinColumn(name = "sid", referencedColumnName = "sid", nullable = false, insertable = false, updatable = false)
    public Singles getSinglesBySid() {
        return singlesBySid;
    }

    public void setSinglesBySid(Singles singlesBySid) {
        this.singlesBySid = singlesBySid;
    }
}
