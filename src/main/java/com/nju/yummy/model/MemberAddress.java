package com.nju.yummy.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "member_address", schema = "yummy")
public class MemberAddress {
    private int id;
    private int aid;
    private int mid;
    private Byte defaultState;

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
    @Column(name = "aid", nullable = false)
    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
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
    @Column(name = "default_state", nullable = true)
    public Byte getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(Byte defaultState) {
        this.defaultState = defaultState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberAddress that = (MemberAddress) o;
        return id == that.id &&
                aid == that.aid &&
                mid == that.mid &&
                Objects.equals(defaultState, that.defaultState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, aid, mid, defaultState);
    }
}
