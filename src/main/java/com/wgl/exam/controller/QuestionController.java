package com.wgl.exam.controller;


import com.wgl.exam.Repository.QuestionRepository;
import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.domain.Question;
import com.wgl.exam.domain.QuestionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

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
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id){
        map.put("title", "题目");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("questionTypes",  questionTypeRepository.findAll());
        map.put("questions",  questionRepository.findAll());
        return "question";

    }


}
