package com.raychen.dao;

import com.raychen.model.TbStudyPeriodModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by raychen on 2017/3/12.
 */
@Repository
public interface PeriodDAO extends JpaRepository<TbStudyPeriodModel, Integer> {

    @Query("select tp from TbStudyPeriodModel tp where tp.study.id=:stid and tp.period=:p")
    public TbStudyPeriodModel findPeriodByStudyAndPnum(@Param("stid") Integer stid,
                                                       @Param("p") Integer p);

    @Query("delete TbStudyPeriodModel tp where tp.study.id=:stid")
    @Transactional
    public void deletePeriodsByStid(@Param("stid") Integer stid);
}
