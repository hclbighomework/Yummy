package com.nju.yummy.service;

import com.nju.yummy.model.Address;

public interface AddressService {

    public Address getAddressByDescription(String description);

    public Iterable<String> getAllDescription();
}
