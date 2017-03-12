package com.raychen.service;

import com.raychen.model.TbCourseModel;
import com.raychen.model.TbStdChargeModel;
import com.raychen.model.TbStudentModel;
import com.raychen.model.TbStudyModel;
import com.raychen.util.StaticValues;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by raychen on 2017/3/7.
 */
@Service
public class StudentService {

    //充值
    public int charge(TbStudentModel studentModel, double money){
        double remain = studentModel.getCardBalance();
        if (money<0){
            if (remain+money<0){
                return -2;
            }
            studentModel.setCardBalance(remain+money);
            return -1;
        }
        else {
            studentModel.setCardBalance(remain+money);
            judgeCardState(studentModel);
            return 1;
        }
    }

    //判断会员卡状态
    public int judgeCardState(TbStudentModel studentModel){
        if (studentModel.getCardBalance()<StaticValues.remainMin){
            if (studentModel.getCardState() == 1){
                studentModel.setCardState((byte) -1);
                return 1;
            } else if (studentModel.getCardState() == -1){
                studentModel.setCardState((byte) -2);
                return 1;
            }
        } else if (studentModel.getCardBalance()<StaticValues.activeMin){
            if (studentModel.getCardState() == -1){
                studentModel.setCardState((byte) 1);
                return 1;
            }
        } else {
            if (studentModel.getCardState()!=1){
                studentModel.setCardState((byte) 1);
                return 1;
            }
        }
        return 0;
    }

    //预定课程
    public int bookCourse(TbStudentModel std, TbCourseModel course){
        double remain = std.getCardBalance();
        if (remain<course.getPrice()) return 0;
        std.setCardBalance(remain-course.getPrice());
        consume(std, course.getPrice());
        return 1;
    }

    //消费换积分
    public void consume(TbStudentModel std, double money){
        int score = std.getCardScore();
        std.setCardScore(score+(int)money);
    }

    //兑换积分
    public int useScore(TbStudentModel std, int score){
        if (score>std.getCardScore()) return 0;
        int remain = std.getCardScore();
        double remainBalance = std.getCardBalance();
        std.setCardScore(remain-score);
        std.setCardBalance(remainBalance+score/10);
        judgeCardState(std);
        return 1;
    }

    //退订
    public int unBookCourse(TbStudentModel student, TbStudyModel study, double money){
        if (study == null || study.getState()<0) return 0;
        study.setState((byte) -1);
        double remain = student.getCardBalance();
        student.setCardBalance(remain+money);
        return 1;
    }

    //退课
    public int quitCourse(TbStudyModel study){
        if (study == null || study.getState()<0) return 0;
        study.setState((byte) -2);
        return 1;
    }

    public TbStdChargeModel buildCharge(TbStudentModel std, byte asd, double money, String op, int opType){
        TbStdChargeModel charge = new TbStdChargeModel();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        charge.setStd(std);
        charge.setAsd(asd);
        charge.setMoney(money);
        charge.setOp(op);
        charge.setOpType(opType);
        charge.setTime(format.format(new Date()));
        return charge;
    }
}
