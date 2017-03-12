package com.raychen.dao;

import com.raychen.model.TbStdChargeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by raychen on 2017/3/11.
 */
@Repository
public interface StdChargeDAO extends JpaRepository<TbStdChargeModel,Integer>{
}
