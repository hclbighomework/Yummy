package com.nju.yummy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_package", schema = "yummy", catalog = "")
public class OrderPackage {
    private int id;
    private String oid;
    private int pid;
    private int num;
    private Orders ordersByOid;
    private Packages packagesByPid;

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
    @Column(name = "pid", nullable = false)
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
        OrderPackage that = (OrderPackage) o;
        return id == that.id &&
                pid == that.pid &&
                num == that.num &&
                Objects.equals(oid, that.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, oid, pid, num);
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
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false, insertable = false, updatable = false)
    public Packages getPackagesByPid() {
        return packagesByPid;
    }

    public void setPackagesByPid(Packages packagesByPid) {
        this.packagesByPid = packagesByPid;
    }
}
