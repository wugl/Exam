package com.wgl.exam.controller;


import com.wgl.exam.Repository.ExamRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.domain.Exam;
import com.wgl.exam.uti.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("exam")
public class ExamController {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("")
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "试卷管理");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("exams", examRepository.findAll());

        return "exam";

    }

    @GetMapping("new")
    public String add(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));

        map.put("title", "新增试卷");
        map.put("type", "1");//1 new , 2 edit
        return "editexam";

    }
    @PostMapping("add")
    public ReturnWithData addPost(@RequestParam("name") String name, @RequestParam("date") String date, @RequestParam("time") Integer time,@RequestParam("totalscore") Float totalscore,@RequestParam("passscore") Float passscore){
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        System.out.println(date);
        Exam exam = null;
        try {
            exam = new Exam(name,totalscore,passscore,time,sdf.parse(date));
            Exam e =  examRepository.save(exam);
            return new ReturnWithData("成功", "100",e );
        } catch (ParseException e) {
            e.printStackTrace();
            return new ReturnWithData("日期格式不对", "101",null );
        }


    }
    @GetMapping("edit")
    public String edit (Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id){


        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("title", "新增试卷");
        map.put("type", "2");//1 new , 2 edit
        return "editexam";

    }
    @PostMapping("update")
    public ReturnWithData editPost(){

        return null;

    }

}
