package com.nju.yummy.service;

import com.nju.yummy.model.Reduction;

import java.util.ArrayList;

public interface ReductionService {

    public void addReduction(Reduction reduction);

    public void updateReduction(Reduction reduction);

    public Reduction getReduction(String rid, double fullCost);

    public void deleteReduction(Reduction reduction);

    public ArrayList<Reduction> getSortedReductionByRid(String rid);
}
