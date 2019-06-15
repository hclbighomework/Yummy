package com.nju.yummy.service.impl;

import com.nju.yummy.dao.MembersDAO;
import com.nju.yummy.model.Members;
import com.nju.yummy.service.MembersService;
import com.nju.yummy.util.IDUtil;
import com.nju.yummy.util.MailUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MembersServiceImpl implements MembersService {

    @Resource
    private MembersDAO membersDAO;

    @Override
    public Members getMemberById(int mid) {
        if (membersDAO.findById(mid).isPresent()) {
            return membersDAO.findById(mid).get();
        }
        return null;
    }

    @Override
    public Members login(String email, String password) {
        Members members = membersDAO.findMembersByEmail(email);
        if (members == null || !members.getPassword().equals(password)) {
            return null;
        }
        return members;
    }

    @Transactional
    @Override
    public String register(String email, String password, String name, String phone) {
        if (membersDAO.findMembersByEmail(email) != null) {
            return "error";
        }
        String code = IDUtil.getUUID();
        Members members = new Members();
        members.setEmail(email);
        members.setName(name);
        members.setPassword(password);
        members.setPhone(phone);
        members.setCode(code);
        members.setLevel(1);
        members.setExperience(0);
        members.setBalance(0.00);
        members.setTotalCost(0.00);
        membersDAO.save(members);
        new Thread(new MailUtil(email, code)).start();
        return "success";
    }

    @Transactional
    @Override
    public void updateMember(Members member) {
        membersDAO.save(member);
    }

    @Transactional
    @Override
    public void deleteMember(int mid) {
        Members members = getMemberById(mid);
        if (members != null) {
            members.setState(-1);
            membersDAO.save(members);
        }
    }

    //支付后就将订单消费计入用户总消费
    @Transactional
    @Override
    public void payForOrder(int mid, double cost) {
        Members members = getMemberById(mid);
        members.setBalance(members.getBalance() - cost);
        members.setTotalCost(members.getTotalCost() + cost);
        System.out.println(members.getBalance());
        membersDAO.save(members);
    }

    @Transactional
    @Override
    public void updateLevel(int mid, double cost) {
        Members members = getMemberById(mid);
        int experience = (int) (cost / 5) + members.getExperience();
        int level = members.getLevel();
        if (level == 1 && experience >= 100) {
            members.setLevel(2);
            members.setExperience(experience - 100);
        } else if (level == 2 && experience >= 200) {
            members.setLevel(3);
            members.setExperience(experience - 200);
        } else if (level == 3 && experience >= 300) {
            members.setLevel(4);
            members.setExperience(experience - 300);
        } else if (level == 4 && experience >= 400) {
            members.setLevel(5);
            members.setExperience(experience - 400);
        } else if (level == 5 && experience >= 999) {
            members.setExperience(999);
        } else {
            members.setExperience(experience);
        }
        System.out.println("experience" + members.getExperience());
        membersDAO.save(members);
    }

    @Override
    public Iterable<Members> getAllMember() {
        return membersDAO.findAll();
    }

    @Transactional
    @Override
    public void makeActive(String code) {
        Members members = membersDAO.findMembersByCode(code);
        members.setState(1);
        membersDAO.save(members);
    }
}
