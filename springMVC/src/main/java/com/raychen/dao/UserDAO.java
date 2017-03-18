package com.raychen.dao;

import com.raychen.model.TbUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by raychen on 2017/3/14.
 */
@Repository
public interface UserDAO extends JpaRepository<TbUserModel, Integer> {

    @Query("select tu from TbUserModel tu where tu.cid.id=:cid")
    Collection<TbUserModel> findUsersByCid(@Param("cid") Integer cid);

    TbUserModel findByUserName(String username);
}
