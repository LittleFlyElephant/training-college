package com.raychen.dao;

import com.raychen.model.TbStudyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by raychen on 2017/3/8.
 */
@Repository
public interface StudyDAO extends JpaRepository<TbStudyModel, Integer> {

    @Query("select ts from TbStudyModel ts where ts.std.id=:stdId and ts.course.id=:cid")
    public TbStudyModel findStudy(@Param("stdId") Integer stdId, @Param("cid") Integer cid);

    @Query("select ts from TbStudyModel ts where ts.course.id=:cid")
    public List<TbStudyModel> findStudyByCourse(@Param("cid") Integer cid);
}
