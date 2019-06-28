package com.nju.yummy.service;

import com.nju.yummy.model.Singles;

import java.util.ArrayList;

public interface SingleService {

    public void addSingle(Singles singles);

    public void updateSingle(Singles singles);

    public Singles getSingleBySid(int sid);

    public ArrayList<Singles> getSinglesByNameKey(String name);

    public Singles getSingleByNameAndRid(String name, String rid);

    public void delete(String name, String rid);

    public ArrayList<Singles> getValidSinglesForRid(String rid);

    public ArrayList<Singles> getSortedValidSinglesForRid(String rid, String sortType);
}
