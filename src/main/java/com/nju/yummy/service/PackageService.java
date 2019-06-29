package com.nju.yummy.service;

import com.nju.yummy.model.Packages;

import java.util.ArrayList;

public interface PackageService {

    public void addPackage(Packages packages);

    public void updatePackage(Packages packages);

    public Packages getPackageByID(int pid);

    public ArrayList<Packages> getPackagesByNameKey(String name);

    public Packages getPackageByNameAndRid(String name, String rid);

    public void delete(String name, String rid);

    public ArrayList<Packages> getValidPackagesForRid(String rid);

    public ArrayList<Packages> getSortedValidPackagesForRid(String rid, String sortType);
}
