package com.nju.yummy.service.impl;

import com.nju.yummy.dao.SingleDAO;
import com.nju.yummy.model.Singles;
import com.nju.yummy.service.SingleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class SingleServiceImpl implements SingleService {

    @Resource
    private SingleDAO singleDAO;

    @Transactional
    @Override
    public void addSingle(Singles singles) {
        singleDAO.save(singles);
    }

    @Transactional
    @Override
    public void updateSingle(Singles singles) {
        singleDAO.save(singles);
    }

    @Override
    public Singles getSingleBySid(int sid) {
        if (singleDAO.findById(sid).isPresent()) {
            return singleDAO.findById(sid).get();
        }
        return null;
    }

    @Override
    public ArrayList<Singles> getSinglesByNameKey(String name) {
        return singleDAO.findAllByNameKey("%" + name + "%");
    }

    @Override
    public Singles getSingleByNameAndRid(String name, String rid) {
        return singleDAO.findByNameAndRid(name, rid);
    }

    @Transactional
    @Override
    public void delete(String name, String rid) {
        Singles singles = getSingleByNameAndRid(name, rid);
        singleDAO.delete(singles);
    }

    @Override
    public ArrayList<Singles> getValidSinglesForRid(String rid) {
        return singleDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfter(rid, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public ArrayList<Singles> getSortedValidSinglesForRid(String rid, String sortType) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (sortType.equals("asc")) {
            return singleDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostAsc(rid, timestamp, timestamp);
        } else if (sortType.equals("desc")){
            return singleDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfterOrderByCostDesc(rid, timestamp, timestamp);
        } else {
            return singleDAO.findAllByRidAndStartTimeBeforeAndEndTimeAfter(rid, timestamp, timestamp);
        }
    }


}
