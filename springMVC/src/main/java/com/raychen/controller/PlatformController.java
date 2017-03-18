package com.raychen.controller;

import com.raychen.dao.*;
import com.raychen.model.*;
import com.raychen.service.CourseService;
import com.raychen.service.OrganizationService;
import com.raychen.service.StudentService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by raychen on 2017/2/27.
 */
@Controller
@RequestMapping(value = "/platform/")
public class PlatformController {

    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private StdChargeDAO stdChargeDAO;
    @Autowired
    private OrgFinantialDAO orgFinantialDAO;
    @Autowired
    private OrganizationDAO organizationDAO;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private PlatformDAO platformDAO;
    @Autowired
    private StudyDAO studyDAO;
    @Autowired
    private CourseService courseService;
    @Autowired
    private OrganizationService organizationService;

    //平台-课程
    @RequestMapping(value = "courses/{type}", method = RequestMethod.GET)
    public String getCourses(@PathVariable Integer type, ModelMap modelMap,
                             HttpSession session,
                             RedirectAttributes attr){
        TbPlatformModel platform = (TbPlatformModel) session.getAttribute("plat");
        if (platform == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        Collection<TbCourseModel> courses;
        if (type == 1){
            courses = courseDAO.findAll();
        } else {
            courses = courseDAO.findAllUncheckedCourses();
        }
        modelMap.addAttribute("items", courses);
        modelMap.addAttribute("page", "plat");
        modelMap.addAttribute("pageTitle", type==1?"平台-所有课程":"平台-待审核的课程");
        modelMap.addAttribute("type", type);
        addLayoutData(modelMap);
        return "common/courses";
    }

    //课程详情
    @RequestMapping(value = "course/{id}", method = RequestMethod.GET)
    public String getCourseDetail(@PathVariable Integer id,
                                  ModelMap modelMap,
                                  HttpSession session,
                                  RedirectAttributes attr){
        TbPlatformModel platform = (TbPlatformModel) session.getAttribute("plat");
        if (platform == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        TbCourseModel courseModel = courseDAO.findOne(id);
        modelMap.addAttribute("course", courseModel);
        modelMap.addAttribute("page", "plat");
        modelMap.addAttribute("pageTitle", "平台-课程详情");
        addLayoutData(modelMap);
        return "common/course-detail";
    }

    //deal 审核课程
    @RequestMapping(value = "course/{id}/check", method = RequestMethod.POST)
    public String handleCheck(@PathVariable Integer id,
                              @RequestParam("res") Integer res,
                              RedirectAttributes attr){
        TbCourseModel course = courseDAO.findOne(id);
        int state = res;
        course.setState((byte) state);
        courseDAO.saveAndFlush(course);
        attr.addFlashAttribute("msgType", "success");
        attr.addFlashAttribute("msg", "课程审核成功！");
        return "redirect:/platform/courses/1";
    }

    //结算
    @RequestMapping(value = "money/{tab}", method = RequestMethod.GET)
    public String getMoney(@PathVariable Integer tab, ModelMap modelMap,
                           HttpSession session, RedirectAttributes attr){
        TbPlatformModel platform = (TbPlatformModel) session.getAttribute("plat");
        if (platform == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        Collection<TbStdChargeModel> charges = stdChargeDAO.findByOpType(4);
        ArrayList<TbStdChargeModel> charge_arr = new ArrayList<>(charges);
        Collection<TbOrgFinancialModel> orgs = orgFinantialDAO.findDesc();
        ArrayList<String> pay = new ArrayList<>();
        for (TbStdChargeModel charge: charges) {
            String[] tmp = charge.getOp().split("\\:");
            Integer cid = Integer.valueOf(tmp[1]);
            TbStudyModel study = studyDAO.findStudy(charge.getStd().getId(), cid);
            pay.add(String.format("%.2f", courseService.getReturnMoney(study, charge.getMoney())));
        }

        modelMap.addAttribute("orgs", orgs);
        modelMap.addAttribute("stds", charge_arr);
        modelMap.addAttribute("pay", pay);
        modelMap.addAttribute("page", "plat");
        modelMap.addAttribute("pageTitle", "平台-结算");
        modelMap.addAttribute("type", 3);
        modelMap.addAttribute("tab", tab);
        modelMap.addAttribute("balance", platform.getBalance());
        addLayoutData(modelMap);
        return "platform/financial-check";
    }

    //deal 结算
    @RequestMapping(value = "money/{type}/{id}", method = RequestMethod.POST)
    public String handleMoney(@PathVariable Integer type, @PathVariable Integer id,
                              @RequestParam("money") Double money,
                              HttpSession session,
                              RedirectAttributes attr){
        TbPlatformModel platform = (TbPlatformModel) session.getAttribute("plat");
        if (type == 1){
            //机构
            TbOrgFinancialModel f = orgFinantialDAO.findOne(id);
            f.setState((byte) 1);
            orgFinantialDAO.saveAndFlush(f);
            //机构财务增加
            TbOrganizationModel org = f.getOrg();
            double remain = org.getCardBalance();
            org.setCardBalance(remain+f.getMoney()*0.9);
            organizationDAO.saveAndFlush(org);
            //平台财务减少
            remain = platform.getBalance();
            platform.setBalance(remain-f.getMoney()*0.9);
            platformDAO.saveAndFlush(platform);
            session.setAttribute("plat", platform);
        } else {
            TbStdChargeModel s = stdChargeDAO.findOne(id);
            //机构财务增加
            String[] tmp = s.getOp().split("\\:");
            Integer cid = Integer.valueOf(tmp[1]);
            TbCourseModel course = courseDAO.findOne(cid);
            if (course != null){
                TbOrganizationModel org = course.getOrg();
                double remain = org.getCardBalance();
                org.setCardBalance(remain+(s.getMoney()-money)*0.9);
                organizationDAO.saveAndFlush(org);
                TbOrgFinancialModel orgFinancial = organizationService.buildCharge(org, (byte) 1, s.getMoney()-money, "结算课程", 1, (byte) 1);
                orgFinantialDAO.saveAndFlush(orgFinancial);
            }
            //平台财务增减少
            double remain = platform.getBalance();
            platform.setBalance(remain-(s.getMoney()-money)*0.9-money);
            platformDAO.saveAndFlush(platform);
            session.setAttribute("plat", platform);
            //学生财务增加
            TbStudentModel std = s.getStd();
            remain = std.getCardBalance();
            std.setCardBalance(remain+money);
            studentService.consume(std, s.getMoney()-money);
            studentDAO.saveAndFlush(std);
            //学生部分消费,部分退还
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) -1, s.getMoney()-money, "课程消费", -1, (byte) 1);
            stdChargeDAO.saveAndFlush(charge);
            //退课
            s.setState((byte) 1);
            s.setMoney(money);
            stdChargeDAO.saveAndFlush(s);
        }
        attr.addFlashAttribute("msgType", "success");
        attr.addFlashAttribute("msg", "结算成功！");
        return "redirect:/platform/money/"+type;
    }

    //统计
    @RequestMapping(value = "stat", method = RequestMethod.GET)
    public String getStat(HttpSession session, ModelMap modelMap, RedirectAttributes attr){
        TbPlatformModel platform = (TbPlatformModel) session.getAttribute("plat");
        if (platform == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        Collection<TbOrganizationModel> orgs = organizationDAO.findAll();
        modelMap.addAttribute("num", orgs.size());
        JSONArray org_arr = new JSONArray();
        JSONArray num_arr = new JSONArray();
        for (TbOrganizationModel org: orgs) {
            org_arr.add(org.getName());
            Collection<TbStudentModel> students = studentDAO.findStudentsByOrg(org.getId());
            num_arr.add(students.size());
        }
        modelMap.addAttribute("orgs", org_arr);
        modelMap.addAttribute("nums", num_arr);
        //学习统计
        Collection<TbCourseModel> courses = courseDAO.findAll();
        int all = 0, finished = 0;
        JSONArray course_arr = new JSONArray();
        JSONArray score_arr = new JSONArray();
        for (TbCourseModel course: courses) {
            Collection<TbStudyModel> studies = course.getStudies();
            all += studies.size();
            course_arr.add(course.getTitle());
            long scores = 0;
            int num = 0;
            for (TbStudyModel study: studies) {
                if (study.getState() == 2){
                    finished ++;
                    num++;
                    scores += study.getScore();
                }
            }
            score_arr.add((double) scores / num);
        }
        modelMap.addAttribute("per", String.format("%.2f",(double)finished/all*100));
        modelMap.addAttribute("courses", course_arr);
        modelMap.addAttribute("scores", score_arr);
        //收入统计
        Collection<TbOrgFinancialModel> fs = orgFinantialDAO.findAll();
        JSONArray date_arr = new JSONArray();
        JSONArray money_arr = new JSONArray();
        double sum = 0;
        for (TbOrgFinancialModel of: fs) {
            date_arr.add(of.getTime());
            sum += of.getMoney()/10.0;
            money_arr.add(sum);
        }
        modelMap.addAttribute("sum", sum);
        modelMap.addAttribute("charge_dates", date_arr);
        modelMap.addAttribute("charge_moneys", money_arr);
        addLayoutData(modelMap);
        return "platform/stat";
    }

    private void addLayoutData(ModelMap modelMap){
        Collection<TbCourseModel> courses = courseDAO.findAll();
        int unchecked = 0;
        for (TbCourseModel course: courses) {
            if (course.getState() == 0) unchecked++;
        }
        modelMap.addAttribute("all", courses.size());
        modelMap.addAttribute("unchecked", unchecked);
    }

}
