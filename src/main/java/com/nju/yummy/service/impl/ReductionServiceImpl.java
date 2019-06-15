package com.nju.yummy.service.impl;

import com.nju.yummy.dao.ReductionDAO;
import com.nju.yummy.model.Reduction;
import com.nju.yummy.service.ReductionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class ReductionServiceImpl implements ReductionService {

    @Resource
    private ReductionDAO reductionDAO;

    @Override
    public void addReduction(Reduction reduction) {
        reductionDAO.save(reduction);
    }

    @Override
    public void updateReduction(Reduction reduction) {
        reductionDAO.save(reduction);
    }

    @Override
    public Reduction getReduction(String rid, double fullCost) {
        return reductionDAO.getByRidAndFullCost(rid, fullCost);
    }

    @Override
    public void deleteReduction(Reduction reduction) {
        reductionDAO.delete(reduction);
    }

    @Override
    public ArrayList<Reduction> getSortedReductionByRid(String rid) {
        return reductionDAO.getAllByRidOrderByFullCostAsc(rid);
    }
}
