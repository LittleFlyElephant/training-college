package com.raychen.controller;

import com.raychen.dao.*;
import com.raychen.model.*;
import com.raychen.service.CourseService;
import com.raychen.service.StudentService;
import com.raychen.util.StaticValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by raychen on 2017/2/27.
 */
@Controller
@RequestMapping(value = "/student/")
public class StudentController {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private StudyDAO studyDAO;
    @Autowired
    private StdChargeDAO stdChargeDAO;
    @Autowired
    private CourseService courseService;
    @Autowired
    private PeriodDAO periodDAO;

    //所有课程，我的课程
    @RequestMapping(value = "courses/{type}", method = RequestMethod.GET)
    public String getCourses(@PathVariable Integer type, ModelMap modelMap, HttpSession session){
        List<TbCourseModel> courses = courseDAO.findAll();
        TbStudentModel studentModel = (TbStudentModel) session.getAttribute("std");
        if (type == 1){
            courses = studentDAO.findCourses(studentModel.getId());
        }
        modelMap.addAttribute("items", courses);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("pageTitle", "学生-所有课程");
        return "common/courses";
    }

    //我的会员卡
    @RequestMapping(value = "card", method = RequestMethod.GET)
    public String getCard(ModelMap modelMap, HttpSession session){
        TbStudentModel studentModel = (TbStudentModel) session.getAttribute("std");
        Collection<TbStdChargeModel> charges = studentModel.getCharges();
        //判断卡余额状况
        int changed = studentService.judgeCardState(studentModel);
        if (changed==1){
            studentDAO.saveAndFlush(studentModel);
            session.setAttribute("std", studentModel);
        }
        modelMap.addAttribute("user", studentModel);
        return "student/my-card";
    }

    //会员卡充值
    @RequestMapping(value = "charge", method = RequestMethod.GET)
    public String getCharge(ModelMap modelMap, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        modelMap.addAttribute("user", std);
        return "student/charge";
    }

    //deal 充值
    @RequestMapping(value = "handle_charge", method = RequestMethod.POST)
    public String handleCharge(@RequestParam("money") Double money,
                               HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        int ret = studentService.charge(std, money);
        if (ret>0){
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) 1, money, "充值会员卡", 1);
            stdChargeDAO.saveAndFlush(charge);
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
        }
        return "redirect:card";
    }

    //deal 注销会员卡
    @RequestMapping(value = "card/invalid", method = RequestMethod.POST)
    public String handleInvalidCard(HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std.getCardState() == 1){
            std.setCardState((byte) -3);
        } else
            std.setCardState((byte) 1);
        studentDAO.saveAndFlush(std);
        session.setAttribute("std", std);
        return "redirect:/student/card";
    }

    //兑换积分
    @RequestMapping(value = "card/change", method = RequestMethod.POST)
    public String handleChange(@RequestParam("score") Integer score, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (studentService.useScore(std, score) == 1){
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
        }
        return "redirect:/student/card";
    }

    //个人信息
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String getInfo(ModelMap modelMap, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        modelMap.addAttribute("user", std);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("pageTitle", "学生-基本信息");
        return "common/info";
    }

    //deal 个人信息
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public String handleInfo(@RequestParam("username") String username,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             @RequestParam("email") String email,
                             HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        std.setUserName(username);
        std.setName(name);
        std.setPhone(phone);
        std.setEmail(email);
        studentDAO.saveAndFlush(std);
        return "redirect:info";
    }

    //课程详情
    @RequestMapping(value = "course/{id}", method = RequestMethod.GET)
    public String getCourse(@PathVariable Integer id, ModelMap modelMap, HttpSession session){
        TbCourseModel course = courseDAO.findOne(id);
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbStudyModel study = studyDAO.findStudy(std.getId(), course.getId());
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("study", study);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("pageTitle", "学生-课程详情");
        return "common/course-detail";
    }

    //deal 预订课程
    @RequestMapping(value = "course/{id}/add", method = RequestMethod.POST)
    public String handleAddCourse(@PathVariable Integer id, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbCourseModel course = courseDAO.findOne(id);
        if (studentService.bookCourse(std, course) == 1){
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) -1, course.getPrice(), "预订课程", 2);
            stdChargeDAO.saveAndFlush(charge);
            TbStudyModel studyModel = new TbStudyModel();
            studyModel.setState((byte) 0);
            studyModel.setCourse(course);
            studyModel.setStd(std);
            studyModel.setScore(-1);
            studyDAO.saveAndFlush(studyModel);
            TbStudyModel tmp = studyDAO.findStudy(std.getId(), id);
            for (int i = 0; i < course.getPeriod(); i++) {
                TbStudyPeriodModel period = courseService.getPeriod(tmp, i+1);
                periodDAO.saveAndFlush(period);
            }
        }
        return "redirect:/student/courses/1";
    }

    //deal 退订
    @RequestMapping(value = "course/{id}/delete", method = RequestMethod.POST)
    public String handleDeleteCourse(@PathVariable Integer id, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbCourseModel course = courseDAO.findOne(id);
        TbStudyModel study = studyDAO.findStudy(std.getId(), id);
        if (studentService.unBookCourse(std, study, course.getPrice()) == 1){
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) 1, course.getPrice(), "退订课程", 3);
            stdChargeDAO.saveAndFlush(charge);
            studentDAO.saveAndFlush(std);
            studyDAO.saveAndFlush(study);
            periodDAO.deletePeriodsByStid(study.getId());
            session.setAttribute("std", std);
        }
        return "redirect:/student/courses/1";
    }

    //deal 退课
    @RequestMapping(value = "course/{id}/quit", method = RequestMethod.POST)
    public String handleQuitCourse(@PathVariable Integer id, HttpSession session){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbStudyModel study = studyDAO.findStudy(std.getId(), id);
        if (studentService.quitCourse(study) == 1){
            studyDAO.saveAndFlush(study);
        }
        return "redirect:/student/courses/1";
    }
}
