package com.raychen.controller;

import com.raychen.util.TimeScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by raychen on 2017/2/25.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        TimeScheduler s = new TimeScheduler();
        return "index";
    }
}
