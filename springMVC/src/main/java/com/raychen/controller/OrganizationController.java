package com.raychen.controller;

import com.raychen.dao.CourseDAO;
import com.raychen.dao.OrganizationDAO;
import com.raychen.dao.PeriodDAO;
import com.raychen.dao.StudyDAO;
import com.raychen.model.TbCourseModel;
import com.raychen.model.TbOrganizationModel;
import com.raychen.model.TbStudyModel;
import com.raychen.model.TbStudyPeriodModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by raychen on 2017/3/1.
 */
@Controller
@RequestMapping(value = "/organization/")
public class OrganizationController {

    @Autowired
    private OrganizationDAO organizationDAO;
    @Autowired
    private CourseDAO courseDAO;
    @Autowired
    private StudyDAO studyDAO;
    @Autowired
    private PeriodDAO periodDAO;

    //我发布的课程
    @RequestMapping(value = "my-course", method = RequestMethod.GET)
    public String getMyCourses(ModelMap modelMap, HttpSession session){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        modelMap.addAttribute("items", organizationModel.getCourses());
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("pageTitle", "机构-我发布的课程");
        modelMap.addAttribute("type", 1);
        return "common/courses";
    }

    //增加课程
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String getAddCourse(){
        return "organization/course-add";
    }

    //deal 增加课程
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String handleAddCourse(@ModelAttribute("course") TbCourseModel Model,
                                  HttpSession session){
        TbCourseModel newModel = new TbCourseModel();
        TbOrganizationModel orgModel = (TbOrganizationModel) session.getAttribute("org");
        newModel.setTitle(Model.getTitle());
        newModel.setContent(Model.getContent());
        newModel.setPrice(Model.getPrice());
        newModel.setTeacher(Model.getTeacher());
        newModel.setPeriod(Model.getPeriod());
        newModel.setTime(Model.getTime());
        newModel.setState((byte) 0);
        newModel.setOrg(orgModel);
        courseDAO.saveAndFlush(newModel);
        session.setAttribute("org", organizationDAO.findOne(orgModel.getId()));
        return "redirect:my-course";
    }

    //课程详情
    @RequestMapping(value = "course/{id}", method = RequestMethod.GET)
    public String getCourseDetail(@PathVariable Integer id,
                                  ModelMap modelMap, HttpSession session){
        TbCourseModel courseModel = courseDAO.findOne(id);
        modelMap.addAttribute("course", courseModel);
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("pageTitle", "机构-课程详情");
        List<TbStudyModel> stds = studyDAO.findStudyByCourse(id);
        modelMap.addAttribute("stds", stds);
        return "common/course-detail";
    }

    //机构基本信息
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String getInfo(ModelMap modelMap, HttpSession session){
        TbOrganizationModel org = (TbOrganizationModel) session.getAttribute("org");
        modelMap.addAttribute("user", org);
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("pageTitle", "机构-基本信息");
        return "common/info";
    }

    //登记上课
    @RequestMapping(value = "course/{id}/std/{sid}/period/{pid}/add", method = RequestMethod.POST)
    public String handlePeriodAdd(@PathVariable Integer id,
                                  @PathVariable Integer sid,
                                  @PathVariable Integer pid){
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        if (studyModel.getState() == 0){
            studyModel.setState((byte) 1);
            studyDAO.saveAndFlush(studyModel);
        }
        TbStudyPeriodModel period = periodDAO.findOne(pid);
        period.setState((byte) 1);
        periodDAO.saveAndFlush(period);
        return "redirect:/organization/course/"+id;
    }

    //登记旷课
    @RequestMapping(value = "course/{id}/std/{sid}/period/{pid}/del", method = RequestMethod.POST)
    public String handlePeriodDel(@PathVariable Integer id,
                                  @PathVariable Integer sid,
                                  @PathVariable Integer pid){
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        if (studyModel.getState() == 0){
            studyModel.setState((byte) 1);
            studyDAO.saveAndFlush(studyModel);
        }
        TbStudyPeriodModel period = periodDAO.findOne(pid);
        period.setState((byte) -1);
        periodDAO.saveAndFlush(period);
        return "redirect:/organization/course/"+id;
    }

    //登记成绩
    @RequestMapping(value = "course/{id}/std/{sid}/score", method = RequestMethod.POST)
    public String handleScore(@PathVariable Integer id,
                              @PathVariable Integer sid,
                              @RequestParam("score") Integer score){
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        if (studyModel.getState() == 1){
            studyModel.setState((byte) 2);
            studyModel.setScore(score);
            studyDAO.saveAndFlush(studyModel);
        }
        return "redirect:/organization/course/"+id;
    }

}
