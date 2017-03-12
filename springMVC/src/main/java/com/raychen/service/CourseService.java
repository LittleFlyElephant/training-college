package com.raychen.service;

import com.raychen.model.TbStudyModel;
import com.raychen.model.TbStudyPeriodModel;
import org.springframework.stereotype.Service;

/**
 * Created by raychen on 2017/3/12.
 */
@Service
public class CourseService {

    public TbStudyPeriodModel getPeriod(TbStudyModel study, int p){
        TbStudyPeriodModel period = new TbStudyPeriodModel();
        period.setState((byte) 0);
        period.setStudy(study);
        period.setPeriod(p);
        return period;
    }
}
