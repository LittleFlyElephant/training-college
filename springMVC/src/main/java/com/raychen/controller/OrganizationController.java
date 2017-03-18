package com.raychen.controller;

import com.raychen.dao.*;
import com.raychen.model.*;
import com.raychen.service.OrganizationService;
import com.raychen.service.StudentService;
import com.raychen.util.CommonUtil;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collection;
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
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private OrgFinantialDAO finantialDAO;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private StdChargeDAO stdChargeDAO;
    @Autowired
    private UserDAO userDAO;

    //我发布的课程
    @RequestMapping(value = "my-course/{type}", method = RequestMethod.GET)
    public String getMyCourses(@PathVariable Integer type, ModelMap modelMap,
                               HttpSession session,
                               RedirectAttributes attr){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        Collection<TbCourseModel> courses;
        if (type == 1){
            courses = organizationModel.getCourses();
        } else if (type == 2){
            courses = courseDAO.findUncheckedCourses(organizationModel.getId());
        } else {
            courses = courseDAO.findFailedCourses(organizationModel.getId());
        }
        addLayoutData(organizationModel.getId(), modelMap);
        modelMap.addAttribute("items", courses);
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("pageTitle", "机构-课程");
        modelMap.addAttribute("type", type);
        return "common/courses";
    }

    //增加课程
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String getAddCourse(HttpSession session, RedirectAttributes attr, ModelMap modelMap){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        addLayoutData(organizationModel.getId(), modelMap);
        return "organization/course-add";
    }

    //deal 增加课程
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String handleAddCourse(@ModelAttribute("course") TbCourseModel Model,
                                  RedirectAttributes attr,
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
        attr.addFlashAttribute("msgType", "info");
        attr.addFlashAttribute("msg", "课程已申请...");
        return "redirect:my-course/2";
    }

    //修改课程
    @RequestMapping(value = "course/{id}/modify", method = RequestMethod.GET)
    public String getModifyCourse(@PathVariable Integer id, ModelMap modelMap,
                                  HttpSession session,
                                  RedirectAttributes attr){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        modelMap.addAttribute("course", courseDAO.findOne(id));
        addLayoutData(organizationModel.getId(), modelMap);
        return "organization/course-modify";
    }

    //deal 修改课程
    @RequestMapping(value = "course/{id}/modify", method = RequestMethod.POST)
    public String handleModifyCourse(@PathVariable Integer id,
                                     @ModelAttribute("course") TbCourseModel course,
                                     RedirectAttributes attr,
                                     HttpSession session){
        TbOrganizationModel orgModel = (TbOrganizationModel) session.getAttribute("org");
        TbCourseModel course_old = courseDAO.findOne(id);
        course_old.setTitle(course.getTitle());
        course_old.setContent(course.getContent());
        course_old.setPrice(course.getPrice());
        course_old.setTeacher(course.getTeacher());
        course_old.setPeriod(course.getPeriod());
        course_old.setTime(course.getTime());
        course_old.setState((byte) 0);
        courseDAO.saveAndFlush(course_old);
        attr.addFlashAttribute("msgType", "info");
        attr.addFlashAttribute("msg", "课程已修改，正在申请中...");
        return "redirect:/organization/course/"+id+"/1";
    }

    //课程详情
    @RequestMapping(value = "course/{id}/{tab}", method = RequestMethod.GET)
    public String getCourseDetail(@PathVariable Integer id,
                                  @PathVariable Integer tab,
                                  ModelMap modelMap, HttpSession session,
                                  RedirectAttributes attr){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        TbCourseModel courseModel = courseDAO.findOne(id);
        modelMap.addAttribute("course", courseModel);
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("tab", tab);
        modelMap.addAttribute("pageTitle", "机构-课程详情");
        List<TbStudyModel> stds = studyDAO.findStudyByCourse(id);
        modelMap.addAttribute("stds", stds);
        //非会员
        Collection<TbUserModel> users = userDAO.findUsersByCid(id);
        modelMap.addAttribute("users", users);
        addLayoutData(organizationModel.getId(), modelMap);
        return "common/course-detail";
    }

    //机构基本信息
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String getInfo(ModelMap modelMap, HttpSession session, RedirectAttributes attr){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        modelMap.addAttribute("user", organizationModel);
        modelMap.addAttribute("page", "org");
        modelMap.addAttribute("pageTitle", "机构-基本信息");
        addLayoutData(organizationModel.getId(), modelMap);
        return "common/info";
    }

    //deal 机构基本信息
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public String handleInfo(@RequestParam("username") String username,
                             @RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             RedirectAttributes attr,
                             HttpSession session){
        TbOrganizationModel org = (TbOrganizationModel) session.getAttribute("org");
        org.setOrgName(username);
        org.setName(name);
        org.setPhone(phone);
        organizationDAO.saveAndFlush(org);
        session.setAttribute("org", org);
        session.setAttribute("username", org.getOrgName());
        attr.addFlashAttribute("msgType", "success");
        attr.addFlashAttribute("msg", "机构信息修改成功！");
        return "redirect:info";
    }

    //deal 登记上课
    @RequestMapping(value = "course/{id}/std/{sid}/period/{pid}/add", method = RequestMethod.POST)
    public String handlePeriodAdd(@PathVariable Integer id,
                                  @PathVariable Integer sid,
                                  @PathVariable Integer pid,
                                  RedirectAttributes attr){
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        byte state = studyModel.getState();
        if (state == 0){
            studyModel.setState((byte) 1);
            studyDAO.saveAndFlush(studyModel);
        }
        if (state == 0 || state == 1){
            TbStudyPeriodModel period = periodDAO.findOne(pid);
            period.setState((byte) 1);
            periodDAO.saveAndFlush(period);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "登记上课成功！");
        } else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "登记失败，课程状态有误...");
        }
        return "redirect:/organization/course/"+id+"/1";
    }

    //deal 登记旷课
    @RequestMapping(value = "course/{id}/std/{sid}/period/{pid}/del", method = RequestMethod.POST)
    public String handlePeriodDel(@PathVariable Integer id,
                                  @PathVariable Integer sid,
                                  @PathVariable Integer pid,
                                  RedirectAttributes attr){
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        byte state = studyModel.getState();
        if (studyModel.getState() == 0){
            studyModel.setState((byte) 1);
            studyDAO.saveAndFlush(studyModel);
        }
        if (state == 0 || state == 1){
            TbStudyPeriodModel period = periodDAO.findOne(pid);
            period.setState((byte) -1);
            periodDAO.saveAndFlush(period);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "登记旷课成功！");
        } else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "登记失败，课程状态有误...");
        }
        return "redirect:/organization/course/"+id+"/1";
    }

    //deal 登记成绩
    @RequestMapping(value = "course/{id}/std/{sid}/score", method = RequestMethod.POST)
    public String handleScore(@PathVariable Integer id,
                              @PathVariable Integer sid,
                              @RequestParam("score") Integer score,
                              RedirectAttributes attr,
                              HttpSession session){
        TbOrganizationModel org = (TbOrganizationModel) session.getAttribute("org");
        TbStudyModel studyModel = studyDAO.findStudy(sid, id);
        TbCourseModel course = studyModel.getCourse();
        TbStudentModel std = studyModel.getStd();
        if (studyModel.getState() == 1){
            double income = CommonUtil.getDiscountValue(course.getPrice(), std.getCardLevel());
            //增加积分和消费
            studentService.consume(std, income);
            studentDAO.saveAndFlush(std);
            TbStdChargeModel charge = studentService.buildCharge(std, (byte) -1, income, "课程消费", -1, (byte) 1);
            stdChargeDAO.saveAndFlush(charge);
            //学习记录
            studyModel.setState((byte) 2);
            studyModel.setScore(score);
            studyDAO.saveAndFlush(studyModel);
            //机构财务
            TbOrgFinancialModel orgFinancial = organizationService.buildCharge(org, (byte) 1, income, "结算课程", 1, (byte) 0);
            finantialDAO.saveAndFlush(orgFinancial);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "登记成功！");

        } else {
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "登记失败，课程状态有误...");
        }
        return "redirect:/organization/course/"+id+"/1";
    }

    //新增非会员
    @RequestMapping(value = "user/add/{cid}", method = RequestMethod.GET)
    public String getUserAdd(@PathVariable Integer cid, ModelMap modelMap,
                             HttpSession session,
                             RedirectAttributes attr){
        TbOrganizationModel organizationModel = (TbOrganizationModel) session.getAttribute("org");
        if (organizationModel == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        modelMap.addAttribute("cid", cid);
        addLayoutData(organizationModel.getId(), modelMap);
        return "organization/user-add";
    }

    //deal 新增非会员
    @RequestMapping(value = "user/add/{cid}", method = RequestMethod.POST)
    public String handleUserAdd(@RequestParam("username") String username,
                                @PathVariable Integer cid,
                                RedirectAttributes attr){
        TbCourseModel courseModel = courseDAO.findOne(cid);
        TbUserModel user_old = userDAO.findByUserName(username);
        if (user_old != null) {
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "登记失败，用户已存在...");
            return "redirect:/organization/user/add/"+cid;
        }else if (courseModel.getState() != 1){
            attr.addFlashAttribute("msgType", "danger");
            attr.addFlashAttribute("msg", "登记失败，课程尚未通过审核...");
            return "redirect:/organization/course/"+cid+"/2";
        } else {
            TbUserModel user = new TbUserModel();
            user.setCid(courseModel);
            user.setUserName(username);
            user.setState((byte) 1);
            user.setScore(-1);
            userDAO.saveAndFlush(user);
            attr.addFlashAttribute("msgType", "success");
            attr.addFlashAttribute("msg", "新增非会员成功！");
        }
        return "redirect:/organization/course/"+cid+"/2";
    }

    //deal 非会员登记成绩
    @RequestMapping(value = "{cid}/user/{id}/score", method = RequestMethod.POST)
    public String handleUserScore(@PathVariable Integer cid,
                                  @PathVariable Integer id,
                                  @RequestParam("score") Integer score,
                                  RedirectAttributes attr){
        TbUserModel user = userDAO.findOne(id);
        user.setScore(score);
        user.setState((byte) 2);
        userDAO.saveAndFlush(user);
        attr.addFlashAttribute("msgType", "success");
        attr.addFlashAttribute("msg", "登记成绩成功！");
        return "redirect:/organization/course/"+cid+"/2";
    }

    //deal 非会员退课
    @RequestMapping(value = "{cid}/user/{id}/del", method = RequestMethod.POST)
    public String handleUserDel(@PathVariable Integer cid,
                                @PathVariable Integer id,
                                RedirectAttributes attr){
        TbUserModel user = userDAO.findOne(id);
        user.setState((byte) -1);
        userDAO.saveAndFlush(user);
        attr.addFlashAttribute("msgType", "info");
        attr.addFlashAttribute("msg", "非会员退课成功！");
        return "redirect:/organization/course/"+cid+"/2";
    }

    //统计
    @RequestMapping(value = "stat", method = RequestMethod.GET)
    public String getStat(HttpSession session, ModelMap modelMap, RedirectAttributes attr){
        TbOrganizationModel org = organizationDAO.findByOrgName((String) session.getAttribute("username"));
        if (org == null){
            attr.addFlashAttribute("msgType", "warning");
            attr.addFlashAttribute("msg", "请先登录！");
            return "redirect:/login";
        }
        int booked = 0, unbooked = 0, quited = 0, finished = 0;
        Collection<TbCourseModel> courses = org.getCourses();
        JSONArray course_arr = new JSONArray();
        JSONArray score_arr = new JSONArray();
        for (TbCourseModel course: courses) {
            course_arr.add(course.getTitle());
            int num = 0;
            long scores = 0;
            Collection<TbStudyModel> studies = course.getStudies();
            for (TbStudyModel study: studies) {
                if (study.getState()>=0) booked++;
                if (study.getState()==-1) unbooked++;
                if (study.getState()==-2) quited++;
                if (study.getState()==2) {
                    finished++;
                    num++;
                    scores += study.getScore();
                }
            }
            score_arr.add(String.format("%.2f", (double) scores/num));
        }
        double per = (double) finished/(booked+unbooked+quited);
        modelMap.addAttribute("booked", booked);
        modelMap.addAttribute("unbooked", unbooked);
        modelMap.addAttribute("quited", quited);
        modelMap.addAttribute("finished", finished);
        modelMap.addAttribute("nums", courses.size());
        modelMap.addAttribute("courses", course_arr);
        modelMap.addAttribute("scores", score_arr);
        modelMap.addAttribute("per", String.format("%.2f", per*100));
        Collection<TbOrgFinancialModel> charges = org.getFinancials();
        JSONArray dates = new JSONArray();
        JSONArray moneys = new JSONArray();
        double sum = 0;
        for (TbOrgFinancialModel f: charges) {
            if (f.getAsd() == 1 && f.getState() == 1){
                dates.add(f.getTime());
                sum += f.getMoney()*0.9;
                moneys.add(sum);
            }
        }
        modelMap.addAttribute("sum", sum);
        modelMap.addAttribute("charge_dates", dates);
        modelMap.addAttribute("charge_moneys", moneys);
        addLayoutData(org.getId(), modelMap);
        return "organization/stat";
    }

    private void addLayoutData(Integer oid, ModelMap modelMap){
        int published=0, pending=0, unchecked=0;
        Collection<TbCourseModel> courses = courseDAO.findCoursesByOid(oid);
        for (TbCourseModel course: courses) {
            if (course.getState() == -1) unchecked++;
            else if (course.getState() == 0) pending++;
        }
        modelMap.addAttribute("published", courses.size());
        modelMap.addAttribute("pending", pending);
        modelMap.addAttribute("unchecked", unchecked);
    }

}
