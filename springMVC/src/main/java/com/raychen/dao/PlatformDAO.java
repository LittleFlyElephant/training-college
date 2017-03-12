package com.raychen.dao;

import com.raychen.model.TbPlatformModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by raychen on 2017/3/1.
 */
@Repository
public interface PlatformDAO extends JpaRepository<TbPlatformModel, Integer> {

}
