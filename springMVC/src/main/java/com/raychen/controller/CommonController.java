package com.raychen.controller;

import com.raychen.dao.OrganizationDAO;
import com.raychen.dao.PlatformDAO;
import com.raychen.dao.StudentDAO;
import com.raychen.model.TbOrganizationModel;
import com.raychen.model.TbPlatformModel;
import com.raychen.model.TbStudentModel;
import com.raychen.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                              RedirectAttributes attr,
                              HttpSession session){
        switch (type){
            case 1:{
                TbStudentModel studentModel = studentDAO.findByUserName(username);
                if (studentModel == null){
                    //用户不存在
                    attr.addFlashAttribute("msgType", "warning");
                    attr.addFlashAttribute("msg", "用户不存在！");
                }else if (studentModel.getPassword().equals(password)){
                    //登录成功
                    session.setAttribute("std", studentModel);
                    session.setAttribute("username", studentModel.getUserName());
                    return "redirect:student/courses/0";
                }else {
                    //密码错误
                    attr.addFlashAttribute("msgType", "danger");
                    attr.addFlashAttribute("msg", "密码错误！");
                }
                break;
            }
            case 2:{
                TbOrganizationModel organizationModel = organizationDAO.findByOrgName(username);
                if (organizationModel == null){
                    //用户不存在
                    attr.addFlashAttribute("msgType", "warning");
                    attr.addFlashAttribute("msg", "用户不存在！");
                }else if (organizationModel.getPassword().equals(password)){
                    session.setAttribute("org", organizationModel);
                    session.setAttribute("username", organizationModel.getOrgName());
                    return "redirect:organization/my-course/1";
                }else {
                    //密码错误
                    attr.addFlashAttribute("msgType", "danger");
                    attr.addFlashAttribute("msg", "密码错误！");
                }
                break;
            }
            case 3:{
                TbPlatformModel platformModel = platformDAO.findByAdminName(username);
                if (platformModel == null){
                    //用户不存在
                    attr.addFlashAttribute("msgType", "warning");
                    attr.addFlashAttribute("msg", "用户不存在！");
                }else if (platformModel.getPassword().equals(password)){
                    session.setAttribute("plat", platformModel);
                    session.setAttribute("username", platformModel.getAdminName());
                    return "redirect:platform/courses/2";
                }else {
                    //密码错误
                    attr.addFlashAttribute("msgType", "danger");
                    attr.addFlashAttribute("msg", "密码错误！");
                }
                break;
            }
            default: break;
        }
        return "redirect:/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister(){
        return "common/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("account") String account,
                                 @RequestParam("type") Integer type,
                                 RedirectAttributes attr){
        switch (type){
            case 1:{
                TbStudentModel std = studentDAO.findByUserName(username);
                if (std != null){
                    attr.addFlashAttribute("msgType", "warning");
                    attr.addFlashAttribute("msg", "注册失败，用户已存在...");
                    return "redirect:/register";
                }
                TbStudentModel student = new TbStudentModel();
                int card_id = CommonUtil.generateCode(7);
                student.setUserName(username);
                student.setPassword(password);
                student.setPhone(phone);
                student.setEmail(account);
                student.setCardId(card_id);
                student.setCardBalance(0.0);
                student.setCardLevel(0);
                student.setCardScore(0);
                student.setCardState((byte) 0);
                student.setCardConsume(0.0);
                studentDAO.saveAndFlush(student);
                attr.addFlashAttribute("msgType", "success");
                attr.addFlashAttribute("msg", "注册成功！");
                return "redirect:/login";
            }
            case 2:{
                TbOrganizationModel org_old = organizationDAO.findByOrgName(username);
                if (org_old != null){
                    attr.addFlashAttribute("msgType", "warning");
                    attr.addFlashAttribute("msg", "注册失败，机构已存在...");
                    return "redirect:/register";
                }
                TbOrganizationModel org = new TbOrganizationModel();
                int card_id = CommonUtil.generateCode(7);
                org.setOrgName(username);
                org.setPassword(password);
                org.setPhone(phone);
                org.setCardId(card_id);
                org.setCardBalance(0.0);
                organizationDAO.saveAndFlush(org);
                attr.addFlashAttribute("msgType", "success");
                attr.addFlashAttribute("msg", "注册成功！");
                return "redirect:/login";
            }
            default:
                attr.addFlashAttribute("msgType", "warning");
                attr.addFlashAttribute("msg", "注册失败...");
                return "redirect:/register";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:login";
    }
}
