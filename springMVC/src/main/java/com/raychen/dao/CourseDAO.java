package com.raychen.dao;

import com.raychen.model.TbCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by raychen on 2017/3/2.
 */
@Repository
public interface CourseDAO extends JpaRepository<TbCourseModel, Integer> {

    @Query("select tc from TbCourseModel tc where tc.state=:state")
    List<TbCourseModel> findCoursesByState(@Param("state") byte state);

    @Query("select tc from TbCourseModel tc where tc.state=0 and tc.org.id=:oid")
    Collection<TbCourseModel> findUncheckedCourses(@Param("oid") Integer oid);

    @Query("select tc from TbCourseModel tc where tc.state=-1 and tc.org.id=:oid")
    Collection<TbCourseModel> findFailedCourses(@Param("oid") Integer oid);

    @Query("select tc from TbCourseModel tc where tc.state=0")
    Collection<TbCourseModel> findAllUncheckedCourses();

    @Query("select tc from TbCourseModel tc where tc.org.id=:oid")
    Collection<TbCourseModel> findCoursesByOid(@Param("oid") Integer oid);
}
