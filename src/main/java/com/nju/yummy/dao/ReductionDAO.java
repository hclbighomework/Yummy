package com.nju.yummy.dao;

import com.nju.yummy.model.Reduction;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ReductionDAO extends CrudRepository<Reduction, Integer> {

    public Reduction getByRidAndFullCost(String rid, double fullCost);

    public ArrayList<Reduction> getAllByRidOrderByFullCostAsc(String rid);
}
