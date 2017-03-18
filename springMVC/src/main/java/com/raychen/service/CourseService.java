package com.raychen.service;

import com.raychen.dao.StudyDAO;
import com.raychen.model.TbStdChargeModel;
import com.raychen.model.TbStudyModel;
import com.raychen.model.TbStudyPeriodModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by raychen on 2017/3/12.
 */
@Service
public class CourseService {

    @Autowired
    private StudyDAO studyDAO;

    public TbStudyPeriodModel getPeriod(TbStudyModel study, int p){
        TbStudyPeriodModel period = new TbStudyPeriodModel();
        period.setState((byte) 0);
        period.setStudy(study);
        period.setPeriod(p);
        return period;
    }

    public double getReturnMoney(TbStudyModel study, double money){
        int sum = 0;
        for (TbStudyPeriodModel period: study.getPeriods()) {
            if (period.getState() != 0){
                sum ++;
            }
        }
        return (1.0-(double) sum/study.getPeriods().size())*money;
    }
}
