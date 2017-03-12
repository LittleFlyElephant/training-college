package com.raychen.controller;

import com.raychen.dao.OrganizationDAO;
import com.raychen.dao.PlatformDAO;
import com.raychen.dao.StudentDAO;
import com.raychen.model.TbOrganizationModel;
import com.raychen.model.TbStudentModel;
import com.raychen.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by raychen on 2017/2/25.
 */
@Controller
public class CommonController {

    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private OrganizationDAO organizationDAO;
    @Autowired
    private PlatformDAO platformDAO;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(){
        return "common/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("type") Integer type,
                              HttpSession session){
        switch (type){
            case 1:{
                TbStudentModel studentModel = studentDAO.findByUserName(username);
                if (studentModel == null){
                    //用户不存在
                    System.err.println("用户不存在！");
                    return "common/login";
                }
                if (studentModel.getPassword().equals(password)){
                    //登录成功
                    session.setAttribute("std", studentModel);
                    session.setAttribute("username", studentModel.getUserName());
                    return "redirect:student/courses/1";
                }else {
                    //密码错误
                    System.err.println("密码错误！");
                }
                break;
            }
            case 2:{
                TbOrganizationModel organizationModel = organizationDAO.findByOrgName(username);
                if (organizationModel.getPassword().equals(password)){
                    session.setAttribute("org", organizationModel);
                    session.setAttribute("username", organizationModel.getOrgName());
                    return "redirect:organization/my-course";
                }
                break;
            }
            case 3:{
//                TbPlatformModel platformModel = platformDAO.find
            }
            default: break;
        }
        return "common/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(){
        return "common/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("type") Integer type){
        switch (type){
            case 1:{
                TbStudentModel student = new TbStudentModel();
                int card_id = CommonUtil.generateCode(7);
                student.setUserName(username);
                student.setPassword(password);
                student.setPhone(phone);
                student.setCardId(card_id);
                student.setCardBalance(0.0);
                student.setCardLevel(0);
                student.setCardScore(0);
                studentDAO.saveAndFlush(student);
                TbStudentModel std = studentDAO.findByUserName(username);
                return "redirect:student/"+std.getId()+"/courses";
            }
            case 2:{
                TbOrganizationModel org = new TbOrganizationModel();
                int card_id = CommonUtil.generateCode(7);
                org.setOrgName(username);
                org.setPassword(password);
                org.setPhone(phone);
                org.setCardId(card_id);
                org.setCardBalance(0.0);
                organizationDAO.saveAndFlush(org);
                org = organizationDAO.findByOrgName(username);
                return "redirect:organization/"+org.getId()+"/my-course";
            }
            default:
                System.err.println("Unknown Type in Register!!!");
                return "redirect:register";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:login";
    }
}
