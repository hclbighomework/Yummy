package com.nju.yummy.dao;

import com.nju.yummy.model.MemberAddress;
import org.springframework.data.repository.CrudRepository;

public interface MemberAddressDAO extends CrudRepository<MemberAddress, Integer> {

    public MemberAddress getMemberAddressByAidAndMid(int aid, int mid);

    public MemberAddress getMemberAddressByMidAndDefaultState(int mid, byte defaultState);
}
