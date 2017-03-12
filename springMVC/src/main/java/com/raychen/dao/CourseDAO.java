package com.raychen.dao;

import com.raychen.model.TbCourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by raychen on 2017/3/2.
 */
@Repository
public interface CourseDAO extends JpaRepository<TbCourseModel, Integer> {

}
