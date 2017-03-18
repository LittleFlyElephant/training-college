package com.raychen.controller;

import com.raychen.dao.*;
import com.raychen.model.*;
import com.raychen.service.CourseService;
import com.raychen.service.StudentService;
import com.raychen.util.CommonUtil;
import com.raychen.util.StaticValues;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private PlatformDAO platformDAO;

    //所有课程，我的课程
    @RequestMapping(value = "courses/{type}", method = RequestMethod.GET)
    public String getCourses(@PathVariable Integer type, ModelMap modelMap, HttpSession session,
                             RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        List<TbCourseModel> courses = courseDAO.findCoursesByState((byte) 1);
        TbStudentModel studentModel = (TbStudentModel) session.getAttribute("std");
        if (type == 1){
            courses = studentDAO.findCourses(studentModel.getId());
        }
        modelMap.addAttribute("items", courses);
        modelMap.addAttribute("type", type);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("pageTitle", "学生-所有课程");
        addLayoutData(std.getId(), modelMap);
        return "common/courses";
    }

    //我的会员卡
    @RequestMapping(value = "card", method = RequestMethod.GET)
    public String getCard(ModelMap modelMap, HttpSession session, RedirectAttributes attr){
        TbStudentModel studentModel = (TbStudentModel) session.getAttribute("std");
        if (studentModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        //判断卡余额状况
        if (studentModel.getCardState() != -3){
            int changed = studentService.judgeCardState(studentModel);
            if (changed==1){
                studentDAO.saveAndFlush(studentModel);
                session.setAttribute("std", studentModel);
            }
        }
        modelMap.addAttribute("user", studentModel);
        addLayoutData(studentModel.getId(), modelMap);
        return "student/my-card";
    }

    //会员卡充值
    @RequestMapping(value = "charge", method = RequestMethod.GET)
    public String getCharge(ModelMap modelMap, HttpSession session, RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        modelMap.addAttribute("user", std);
        addLayoutData(std.getId(), modelMap);
        return "student/charge";
    }

    //deal 充值
    @RequestMapping(value = "handle_charge", method = RequestMethod.POST)
    public String handleCharge(@RequestParam("money") Double money,
                               HttpSession session,
                               RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        int ret = studentService.charge(std, money);
        if (ret>0){
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) 1, money, "充值会员卡", 1, (byte) 1);
            stdChargeDAO.saveAndFlush(charge);
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "充值成功！");
        }else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "充值失败！");
        }
        return "redirect:card";
    }

    //deal 注销会员卡
    @RequestMapping(value = "card/invalid", method = RequestMethod.POST)
    public String handleInvalidCard(HttpSession session, RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std.getCardState() == -3){
            std.setCardState((byte) 1);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "激活成功！");
        }else {
            std.setCardState((byte) -3);
            attr.addFlashAttribute("msgType", "info");
            attr.addFlashAttribute("msg", "注销成功...");
        }
        studentDAO.saveAndFlush(std);
        session.setAttribute("std", std);
        return "redirect:/student/card";
    }

    //兑换积分
    @RequestMapping(value = "card/change", method = RequestMethod.POST)
    public String handleChange(@RequestParam("score") Integer score, HttpSession session,
                               RedirectAttributes attr, ModelMap modelMap){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        if (studentService.useScore(std, score) == 1){
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "兑换成功！");
        }else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "兑换失败，剩余积分不足...");
        }
        addLayoutData(std.getId(), modelMap);
        return "redirect:/student/card";
    }

    //个人信息
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String getInfo(ModelMap modelMap, HttpSession session, RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        modelMap.addAttribute("user", std);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("pageTitle", "学生-基本信息");
        addLayoutData(std.getId(), modelMap);
        return "common/info";
    }

    //deal 个人信息
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public String handleInfo(@RequestParam("username") String username,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             @RequestParam("account") String email,
                             HttpSession session,
                             RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        std.setUserName(username);
        std.setName(name);
        std.setPhone(phone);
        std.setEmail(email);
        studentDAO.saveAndFlush(std);
        session.setAttribute("std", std);
        session.setAttribute("username", std.getUserName());
        attr.addFlashAttribute("msgType", "success");
        attr.addFlashAttribute("msg", "修改成功！");
        return "redirect:info";
    }

    //课程详情
    @RequestMapping(value = "course/{id}", method = RequestMethod.GET)
    public String getCourse(@PathVariable Integer id, ModelMap modelMap,
                            HttpSession session,
                            RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        TbCourseModel course = courseDAO.findOne(id);
        TbStudyModel study = studyDAO.findStudy(std.getId(), course.getId());
        if (study != null){
            double returnMoney = courseService.getReturnMoney(study, CommonUtil.getDiscountValue(course.getPrice(), std.getCardLevel()));
            modelMap.addAttribute("left", String.format("%.2f", returnMoney));
            if (study.getState() == -2){
                Collection<TbStdChargeModel> rets = stdChargeDAO.findByOpType(4);
                for (TbStdChargeModel ret: rets) {
                    String[] tmp = ret.getOp().split("\\:");
                    Integer cid = Integer.valueOf(tmp[1]);
                    if (ret.getStd().getId() == std.getId() && course.getId() == cid){
                        if (ret.getState() == 1){
                            modelMap.addAttribute("stat", 1);
                        } else {
                            modelMap.addAttribute("stat", 0);
                        }
                        break;
                    }
                }
            }
        }
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("study", study);
        modelMap.addAttribute("page", "std");
        modelMap.addAttribute("level", std.getCardLevel());
        modelMap.addAttribute("pageTitle", "学生-课程详情");
        addLayoutData(std.getId(), modelMap);
        return "common/course-detail";
    }

    //deal 预订课程
    @RequestMapping(value = "course/{id}/add", method = RequestMethod.POST)
    public String handleAddCourse(@PathVariable Integer id, HttpSession session,
                                  RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbCourseModel course = courseDAO.findOne(id);
        double income = CommonUtil.getDiscountValue(course.getPrice(), std.getCardLevel());
        int ret = studentService.bookCourse(std, income);
        if (ret == 1){
            //学生
            studentDAO.saveAndFlush(std);
            session.setAttribute("std", std);
            //学生财务
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) -1, income, "预订课程", 2, (byte) 1);
            stdChargeDAO.saveAndFlush(charge);
            //学习记录
            TbStudyModel studyModel = new TbStudyModel();
            studyModel.setState((byte) 0);
            studyModel.setCourse(course);
            studyModel.setStd(std);
            studyModel.setScore(-1);
            studyDAO.saveAndFlush(studyModel);
            //课程课时
            TbStudyModel tmp = studyDAO.findStudy(std.getId(), id);
            for (int i = 0; i < course.getPeriod(); i++) {
                TbStudyPeriodModel period = courseService.getPeriod(tmp, i+1);
                periodDAO.saveAndFlush(period);
            }
            //平台财务增加
            TbPlatformModel platformModel = platformDAO.findOne(StaticValues.platformID);
            double remain = platformModel.getBalance();
            platformModel.setBalance(remain+income);
            platformDAO.saveAndFlush(platformModel);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "订课成功！");
            return "redirect:/student/courses/1";
        }else if (ret == -1){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "订课失败，会员卡状态有误...");
        }else if (ret == -2){
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "订课失败，会员卡余额不足...");
        }else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "系统问题...");
        }
        return "redirect:/student/course/"+id;
    }

    //deal 退订
    @RequestMapping(value = "course/{id}/delete", method = RequestMethod.POST)
    public String handleDeleteCourse(@PathVariable Integer id, HttpSession session,
                                     RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbCourseModel course = courseDAO.findOne(id);
        TbStudyModel study = studyDAO.findStudy(std.getId(), id);
        double income = CommonUtil.getDiscountValue(course.getPrice(), std.getCardLevel());
        if (studentService.unBookCourse(std, study, income) == 1){
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) 1, income, "退订课程", 3, (byte) 1);
            stdChargeDAO.saveAndFlush(charge);
            studentDAO.saveAndFlush(std);
            studyDAO.saveAndFlush(study);
            periodDAO.deletePeriodsByStid(study.getId());
            //平台财务减少
            TbPlatformModel platformModel = platformDAO.findOne(StaticValues.platformID);
            double remain = platformModel.getBalance();
            platformModel.setBalance(remain-income);
            platformDAO.saveAndFlush(platformModel);
            session.setAttribute("std", std);
            attr.addFlashAttribute("msgType", "info");
            attr.addFlashAttribute("msg", "退订成功！");
        }else {
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "退订失败，会员卡状态有误...");
            return "redirect:/student/course/"+id;
        }
        return "redirect:/student/courses/1";
    }

    //deal 退课
    @RequestMapping(value = "course/{id}/quit", method = RequestMethod.POST)
    public String handleQuitCourse(@PathVariable Integer id, HttpSession session,
                                   RedirectAttributes attr){
        TbStudentModel std = (TbStudentModel) session.getAttribute("std");
        TbCourseModel course = courseDAO.findOne(id);
        TbStudyModel study = studyDAO.findStudy(std.getId(), id);
        if (studentService.quitCourse(study) == 1){
            studyDAO.saveAndFlush(study);
            //财务结算
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) 1, CommonUtil.getDiscountValue(course.getPrice(), std.getCardLevel()), "退课:"+id, 4, (byte) 0);
            stdChargeDAO.saveAndFlush(charge);
            attr.addFlashAttribute("msgType", "info");
            attr.addFlashAttribute("msg", "退课审核中...");
        }else {
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "退课失败，会员卡状态有误...");
            return "redirect:/student/course/"+id;
        }
        return "redirect:/student/courses/1";
    }

    //统计
    @RequestMapping(value = "stat", method = RequestMethod.GET)
    public String getStat(HttpSession session, ModelMap modelMap, RedirectAttributes attr){
        TbStudentModel std = studentDAO.findByUserName((String) session.getAttribute("username"));
        if (std == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        ArrayList<Integer> score_arr = new ArrayList<>();
        ArrayList<String> course_arr = new ArrayList<>();
        //课程情况
        int booked = 0, unbooked = 0, quited = 0, finished = 0;
        for (TbStudyModel study: std.getStudies()) {
            if (study.getState()>=0) booked++;
            if (study.getState()==-1) unbooked++;
            if (study.getState()==-2) quited++;
            if (study.getState()==2) {
                finished++;
                score_arr.add(study.getScore());
                course_arr.add(study.getCourse().getTitle());
            }
        }
        modelMap.addAttribute("booked", booked);
        modelMap.addAttribute("unbooked", unbooked);
        modelMap.addAttribute("quited", quited);
        modelMap.addAttribute("finished", finished);
        //学习情况
        JSONArray scores = new JSONArray();
        JSONArray courses = new JSONArray();
        double avg = 0;
        for (int i = 0; i < score_arr.size(); i++) {
            avg += score_arr.get(i);
            scores.add(score_arr.get(i));
            courses.add(course_arr.get(i));
        }
        avg /= score_arr.size();
        modelMap.addAttribute("avg", avg);
        modelMap.addAttribute("scores", scores);
        modelMap.addAttribute("courses", courses);
        //消费情况
        double sum = 0, charge_sum = 0;
        ArrayList<Double> money_arr = new ArrayList<>();
        ArrayList<String> date_arr = new ArrayList<>();
        JSONArray charge_dates = new JSONArray();
        JSONArray charge_moneys = new JSONArray();
        for (TbStdChargeModel charge: std.getCharges()) {
            if (charge.getOpType() == -1){
                money_arr.add(charge.getMoney());
                date_arr.add(charge.getTime());
            } else if (charge.getOpType() == 1){
                charge_dates.add(charge.getTime());
                charge_sum += charge.getMoney();
                charge_moneys.add(charge_sum);
            }
        }
        JSONArray moneys = new JSONArray();
        JSONArray dates = new JSONArray();
        for (int i = 0; i < money_arr.size(); i++) {
            dates.add(date_arr.get(i));
            sum += money_arr.get(i);
            moneys.add(sum);
        }
        modelMap.addAttribute("sum", sum);
        modelMap.addAttribute("moneys", moneys);
        modelMap.addAttribute("dates", dates);
        //充值情况
        modelMap.addAttribute("charge_sum", charge_sum);
        modelMap.addAttribute("charge_moneys", charge_moneys);
        modelMap.addAttribute("charge_dates", charge_dates);
        addLayoutData(std.getId(), modelMap);
        return "student/stat";
    }

    private void addLayoutData(Integer sid, ModelMap modelMap){
        Collection<TbCourseModel> courses = courseDAO.findAll();
        Collection<TbCourseModel> course_my = studentDAO.findCourses(sid);
        int all = 0;
        for (TbCourseModel course: courses) {
            if (course.getState() == 1) all++;
        }
        modelMap.addAttribute("my", course_my.size());
        modelMap.addAttribute("all", all);
    }
}
