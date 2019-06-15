package com.nju.yummy.dao;

import com.nju.yummy.model.Members;
import org.springframework.data.repository.CrudRepository;

public interface MembersDAO extends CrudRepository<Members, Integer> {

    public Members findMembersByEmail(String email);

    public Members findMembersByCode(String code);
}
