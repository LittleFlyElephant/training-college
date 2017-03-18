package com.raychen.dao;

import com.raychen.model.TbOrgFinancialModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by raychen on 2017/3/12.
 */
@Repository
public interface OrgFinantialDAO extends JpaRepository<TbOrgFinancialModel, Integer> {

    Collection<TbOrgFinancialModel> findByState(byte state);

    @Query("select to from TbOrgFinancialModel to order by id desc")
    Collection<TbOrgFinancialModel> findDesc();
}
