package com.raychen.dao;

import com.raychen.model.TbStdChargeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by raychen on 2017/3/11.
 */
@Repository
public interface StdChargeDAO extends JpaRepository<TbStdChargeModel,Integer>{

    Collection<TbStdChargeModel> findByState(byte state);

    @Query("select ts from TbStdChargeModel ts where opType=:opType order by id desc")
    Collection<TbStdChargeModel> findByOpType(@Param("opType") Integer opType);
}
