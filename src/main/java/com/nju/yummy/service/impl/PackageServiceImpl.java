package com.nju.yummy.service.impl;

import com.nju.yummy.dao.PackageDAO;
import com.nju.yummy.model.Packages;
import com.nju.yummy.service.PackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class PackageServiceImpl implements PackageService {

    @Resource
    private PackageDAO packageDAO;

    @Transactional
    @Override
    public void addPackage(Packages packages) {
        packageDAO.save(packages);
    }

    @Transactional
    @Override
    public void updatePackage(Packages packages) {
        packageDAO.save(packages);
    }

    @Override
    public ArrayList<Packages> getPackagesByNameKey(String name) {
        return packageDAO.findAllByNameKey("%" + name + "%");
    }

    @Override
    public Packages getPackageByNameAndRid(String name, String rid) {
        return packageDAO.findByNameAndRid(name, rid);
    }

    @Transactional
    @Override
    public void delete(String name, String rid) {
        Packages packages = getPackageByNameAndRid(name, rid);
        packageDAO.delete(packages);
    }

    @Override
    public ArrayList<Packages> getValidPackagesForRid(String rid) {
        return packageDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfter(rid, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public ArrayList<Packages> getSortedValidPackagesForRid(String rid, String sortType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        switch (sortType) {
            case "asc":
                return packageDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostAsc(rid, timestamp, timestamp);
            case "desc":
                return packageDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostDesc(rid, timestamp, timestamp);
            default:
                return packageDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfter(rid, timestamp, timestamp);
        }
    }
}
