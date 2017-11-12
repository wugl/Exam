package com.wgl.exam.controller;


import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.User;
import com.wgl.exam.uti.Common;
import com.wgl.exam.uti.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "login")
    public String getLogin(Map<String, Object> map, HttpSession session) {
        Object id = session.getAttribute(WebSecurityConfig.SESSION_KEY_USER_ID);
        if (id == null) {
            map.put("title", "login");
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "userdetail")
    public String userDetail(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME) String name, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
        map.put("title", "用户简介");
        User user = userRepository.findUserByIdAndIsDelete(id, 0);
        map.put("user", user);
        return "userdetail";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public ReturnWithData login(@RequestParam String name, @RequestParam String password, @RequestParam Integer userType,
                                HttpSession session) {
        UserType type;
        switch (userType) {
            case 1:
                type = UserType.STUDENT;
                break;
            case 2:
                type = UserType.TEACHER;
                break;
            default:
                type = UserType.MANAGER;
                break;
        }
        logger.error(name + ":" + password + ":" + userType);

            User user = userRepository.findUserByNameAndPasswordAndTypeAndIsDelete(name, password, type, 0);


//        logger.error(user.getType().getName());
            if (user != null) {
                session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_ID, user.getId());
                session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME, user.getName());
                session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE, userType);

                Map result = new HashMap<String, Object>();
                result.put("name", user.getName());
                result.put("type", userType);
                result.put("phone", user.getPhone());
                result.put("email", user.getEmail());
                result.put("id", user.getId());
                result.put("time", new Date().getTime());
                result.put("regTime", user.getRegTime());
                return new ReturnWithData("登陆成功", "100", result);
            } else {
                user = userRepository.findUserByNameAndTypeAndIsDelete(name, type, 0);
                if (user == null)
                    return new ReturnWithData("用户名不存在", "101", null);
                else
                    return new ReturnWithData("密码不正确", "101", null);
            }


    }

    @PostMapping("logout")
    @ResponseBody
    public ReturnWithoutData logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_ID);
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME);
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE);
        return new ReturnWithoutData("注销成功", "100");
    }
}
