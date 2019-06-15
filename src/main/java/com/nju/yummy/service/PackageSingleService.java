package com.nju.yummy.service;

import com.nju.yummy.model.PackageSingle;

public interface PackageSingleService {

    public void addPackageSingle(PackageSingle packageSingle);

    public void updatePackageSingle(PackageSingle packageSingle);

    public PackageSingle getPackageSingle(int pid, int sid);

    public void deletePackageSingle(int pid, int sid);
}
