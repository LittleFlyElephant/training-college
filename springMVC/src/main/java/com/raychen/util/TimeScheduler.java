package com.raychen.util;

import com.raychen.dao.StudentDAO;
import com.raychen.model.TbStudentModel;
import com.raychen.model.TbStudyModel;
import com.raychen.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by raychen on 2017/3/15.
 */

@Component
public class TimeScheduler {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StudentService studentService;

    @Scheduled(cron = "0 0 12 * * ?")
    public void checkUserState(){
        Collection<TbStudentModel> stds = studentDAO.findAll();
        for (TbStudentModel std: stds) {
            Collection<TbStudyModel> studies = std.getStudies();
            for (TbStudyModel st: studies) {
                if (st.getPeriods().size()>0){
                    studentService.checkCard(std);
                }
            }
        }
    }
}
