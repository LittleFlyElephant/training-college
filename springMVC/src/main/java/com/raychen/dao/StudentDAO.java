package com.raychen.dao;

import com.raychen.model.TbCourseModel;
import com.raychen.model.TbStudentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by raychen on 2017/2/27.
 */
@Repository
public interface StudentDAO extends JpaRepository<TbStudentModel, Integer>{

    TbStudentModel findByUserName(String username);

    @Query("select tc from TbCourseModel tc join TbStudyModel ts on tc.id=ts.course.id where ts.std.id=:sid and tc.state=1")
    List<TbCourseModel> findCourses(@Param("sid") Integer sid);

    @Query("select distinct ts from TbStudentModel ts join TbStudyModel tsm on ts.id=tsm.std.id join TbCourseModel tc on tsm.course.id=tc.id where tc.org.id=:oid")
    Collection<TbStudentModel> findStudentsByOrg(@Param("oid") Integer oid);

}
