package com.raychen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by raychen on 2017/2/27.
 */
@Controller
public class PlatformController {

    @RequestMapping(value = "/platform/users", method = RequestMethod.GET)
    public String getUsers(){
        return "platform/users";
    }
}
