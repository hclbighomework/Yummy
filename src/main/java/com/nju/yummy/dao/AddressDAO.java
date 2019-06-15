package com.nju.yummy.dao;

import com.nju.yummy.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AddressDAO extends CrudRepository<Address, Integer> {

    public Address findAddressByDescription(String description);

    @Query("select description from Address")
    public Iterable<String> findAllDescription();
}
