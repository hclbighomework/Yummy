package com.nju.yummy.service.impl;

import com.nju.yummy.dao.PackageSingleDAO;
import com.nju.yummy.model.PackageSingle;
import com.nju.yummy.service.PackageSingleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class PackageSingleServiceImpl implements PackageSingleService {

    @Resource
    private PackageSingleDAO packageSingleDAO;

    @Transactional
    @Override
    public void addPackageSingle(PackageSingle packageSingle) {
        packageSingleDAO.save(packageSingle);
    }

    @Transactional
    @Override
    public void updatePackageSingle(PackageSingle packageSingle) {
        packageSingleDAO.save(packageSingle);
    }

    @Override
    public PackageSingle getPackageSingle(int pid, int sid) {
        return packageSingleDAO.findByPidAndSid(pid, sid);
    }

    @Transactional
    @Override
    public void deletePackageSingle(int pid, int sid) {
        packageSingleDAO.delete(packageSingleDAO.findByPidAndSid(pid, sid));
    }
}
