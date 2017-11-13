package com.wgl.exam.controller;


import com.google.gson.Gson;
import com.wgl.exam.Repository.QuestionRepository;
import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.Question;
import com.wgl.exam.domain.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionTypeRepository questionTypeRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("")
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
        map.put("title", "题目管理");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("questionTypes", questionTypeRepository.findAll());
        map.put("questions", questionRepository.findAll());
        if (questionRepository.findAll().size() > 0)
            System.out.println(new Gson().toJson(questionRepository.findAll().get(0)));
        return "question";

    }

    @GetMapping(value = "getById")
    @ResponseBody
    public ReturnWithData getById(@RequestParam("id") Long id) {

        Question question = questionRepository.findQuestionByIdAndIsDelete(id, 0);


        return new ReturnWithData("成功", "100", question);


    }


    @PostMapping(value = "add")
    @ResponseBody
    public ReturnWithData add(@RequestParam("name") String name, @RequestParam("type") Long type, @RequestParam("title") String title, @RequestParam("comment") String comment) {

        Question question = new Question(title, name, comment, type, "");
        return new ReturnWithData("成功", "100", questionRepository.save(question));
    }

    @PostMapping(value = "del")
    @ResponseBody
    @Transactional
    public ReturnWithoutData del(@RequestParam("id") Long id) {

        int row = questionRepository.del(id);

        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }
    }

    @PostMapping(value = "update")
    @ResponseBody
    @Transactional
    public ReturnWithoutData update(@RequestParam("id") Long id, @RequestParam("type") Long typeId, @RequestParam("name") String name, @RequestParam("title") String title, @RequestParam("comment") String comment) {

        int row = questionRepository.update(id, typeId, name, title, comment);
        System.out.println("------------------row:" + id + ":" + name);
        System.out.println("------------------row:" + row);
        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }


    }

}
