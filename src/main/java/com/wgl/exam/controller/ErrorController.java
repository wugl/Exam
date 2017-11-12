package com.wgl.exam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wgl on 6/14/17.
 */

@Controller
public class ErrorController {
    @RequestMapping("/404")
    public String hander404(ModelMap map){

       // return new ModelAndView("404", HttpStatus.ACCEPTED);
        map.put("title", "404");
        return "404";

    }
    @RequestMapping("/500")
    public String hander500(ModelMap map){

        // return new ModelAndView("404", HttpStatus.ACCEPTED);
        map.put("title", "500");
        return "500";

    }
}
