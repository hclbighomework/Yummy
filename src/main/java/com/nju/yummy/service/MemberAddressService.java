package com.nju.yummy.service;

import com.nju.yummy.model.MemberAddress;

public interface MemberAddressService {

    public void setAddressDefault(int mid, int aid);

    public void addMemberAddress(MemberAddress memberAddress);

    public void updateMemberAddress(MemberAddress memberAddress);

    public void deleteMemberAddress(MemberAddress memberAddress);

    public MemberAddress getMemberAddress(int mid, int aid);
}
