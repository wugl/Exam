package com.wgl.exam.controller;

import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    UserRepository userRepository;

    @Value("${demo.userName}")
    private String userName;
    @GetMapping("")
    public String manager(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME) String name, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
        User user = userRepository.findUserByIdAndIsDelete(id,0);
        map.put("user",user);
        map.put("hello", name);
        map.put("title", "首页");
        return "hello";
    }
//    @GetMapping("/teacher")
//    public String teacher(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME) String name, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
//        User user = userRepository.findOne(id);
//        map.put("user",user);
//        map.put("hello", name);
//        map.put("title", "teacher | index");
//        return "hello";
//    }
//    @GetMapping("/student")
//    public String student(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME) String name, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
//        User user = userRepository.findOne(id);
//        map.put("user",user);
//        map.put("hello", name);
//        map.put("title", "student | index");
//        return "hello";
//    }
}
