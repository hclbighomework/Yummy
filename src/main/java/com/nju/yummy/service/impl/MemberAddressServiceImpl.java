package com.nju.yummy.service.impl;

import com.nju.yummy.dao.MemberAddressDAO;
import com.nju.yummy.model.MemberAddress;
import com.nju.yummy.service.MemberAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MemberAddressServiceImpl implements MemberAddressService {

    @Resource
    private MemberAddressDAO memberAddressDAO;


    @Transactional
    @Override
    public void setAddressDefault(int mid, int aid) {
        MemberAddress memberAddressDefault = memberAddressDAO.getMemberAddressByMidAndDefaultState(mid, (byte) 1);
        if (memberAddressDefault != null) {
            memberAddressDefault.setDefaultState((byte) 0);
            memberAddressDAO.save(memberAddressDefault);
        }

        MemberAddress memberAddress = getMemberAddress(mid, aid);
        memberAddress.setDefaultState((byte) 1);
        memberAddressDAO.save(memberAddress);
    }

    @Transactional
    @Override
    public void addMemberAddress(MemberAddress memberAddress) {
        memberAddressDAO.save(memberAddress);
    }

    @Transactional
    @Override
    public void updateMemberAddress(MemberAddress memberAddress) {
        memberAddressDAO.save(memberAddress);
    }

    @Override
    public void deleteMemberAddress(MemberAddress memberAddress) {
        memberAddressDAO.delete(memberAddress);
    }

    @Override
    public MemberAddress getMemberAddress(int mid, int aid) {
        return memberAddressDAO.getMemberAddressByAidAndMid(aid, mid);
    }
}
