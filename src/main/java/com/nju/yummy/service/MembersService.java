package com.nju.yummy.service;

import com.nju.yummy.model.Members;


public interface MembersService {

    public Members getMemberById(int mid);

    public Members login(String email, String password);

    public String register(String email, String password, String name, String phone);

    public void updateMember(Members member);

    public void deleteMember(int mid);

    public void payForOrder(int mid, double cost);

    public void updateLevel(int mid, double cost);

    public Iterable<Members> getAllMember();

    public void makeActive(String code);
}
