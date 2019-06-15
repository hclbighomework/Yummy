package com.nju.yummy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "package_single", schema = "yummy", catalog = "")
public class PackageSingle {
    private int id;
    private int pid;
    private int sid;
    private int num;
    private Packages packagesByPid;
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
    @Column(name = "pid", nullable = false)
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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
        PackageSingle that = (PackageSingle) o;
        return id == that.id &&
                pid == that.pid &&
                sid == that.sid &&
                num == that.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pid, sid, num);
    }

    @ManyToOne
    @JoinColumn(name = "pid", referencedColumnName = "pid", nullable = false, insertable = false, updatable = false)
    public Packages getPackagesByPid() {
        return packagesByPid;
    }

    public void setPackagesByPid(Packages packagesByPid) {
        this.packagesByPid = packagesByPid;
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
