package com.raychen.dao;

import com.raychen.model.TbOrganizationModel;
import com.raychen.model.TbOrganizationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by raychen on 2017/3/1.
 */
@Repository
public interface OrganizationDAO extends JpaRepository<TbOrganizationModel, Integer> {

    public TbOrganizationModel findByOrgName(String orgName);
}
