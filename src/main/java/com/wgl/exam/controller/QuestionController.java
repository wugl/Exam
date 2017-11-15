package com.wgl.exam.controller;


import com.google.gson.Gson;
import com.wgl.exam.Repository.OptionRepository;
import com.wgl.exam.Repository.QuestionRepository;
import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.Option;
import com.wgl.exam.domain.Question;

import com.wgl.exam.uti.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    OptionRepository optionRepository;

    @RequestMapping("")
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "题目管理");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("questionTypes", questionTypeRepository.findAll());
        map.put("questions", questionRepository.findAll());
        if (questionRepository.findAll().size() > 0)
            System.out.println(new Gson().toJson(questionRepository.findAll().get(0)));
        return "question";

    }

    @GetMapping(value = "getAll")
    @ResponseBody
    public Map<String, Object> getAll() {

        List<Question> questions = questionRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        List<Object> data = new ArrayList<>();
        Map<String,Object> row;

        for (Question q : questions) {
            q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(),0).getName());
//            row = new HashMap<>();
//            row.put("id",q.getId() );
//
//            row.put("type",questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());
//            row.put("typeId",q.getTypeId());
//            row.put("title",q.getTitle());
//            row.put("options",q.getOptions());
//            row.put("name",q.getName());
//            row.put("answer",q.getAnswer());
//            row.put("comment",q.getComment());
//            row.put("score",q.getScore());
//            data.add(row);
            data.add(q);

        }

//        map.put("draw", 1);
//        map.put("recordsTotal", questions.size());
//        map.put("recordsFiltered", questions.size());
        map.put("data", data);


        return map;


    }

    @GetMapping(value = "getById")
    @ResponseBody
    public ReturnWithData getById(@RequestParam("id") Long id) {

        Question question = questionRepository.findQuestionByIdAndIsDelete(id, 0);

        List<Option> options = question.getOptions();
        Map<String, Object> data = new HashMap<>();
        question.setType(questionTypeRepository.findByIdAndIsDelete(question.getTypeId(),0).getName());
        data.put("question", question);
        data.put("options", options);


        return new ReturnWithData("成功", "100", data);


    }


    @PostMapping(value = "add")
    @ResponseBody
    public ReturnWithData add(@RequestParam("name") String name, @RequestParam("type") Long type, @RequestParam("score") Float score, @RequestParam("title") String title, @RequestParam("answer") String answer, @RequestParam("comment") String comment, @RequestParam(name = "optionsContent", required = false) String optionsContent) {


        Question question = new Question(title, name, score, comment, type, answer);
        //System.out.println(comment);
        Question q = questionRepository.save(question);
        //System.out.println(q.getId());
        //System.out.println(optionsContent);

        String[] options = optionsContent.split("\\|\\|\\|");
        Option option;
        for (String item : options) {
            //System.out.println(item);
            option = new Option(item, q.getId());
            optionRepository.save(option);
        }

        return new ReturnWithData("成功", "100", q);
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
    public ReturnWithoutData update(@RequestParam("id") Long id, @RequestParam("type") Long typeId, @RequestParam("score") Float score, @RequestParam("name") String name, @RequestParam("answer") String answer, @RequestParam("title") String title, @RequestParam("comment") String comment, @RequestParam(name = "optionsContent", required = false) String optionsContent) {

        int row = questionRepository.update(id, typeId, name, answer, score, title, comment);
        System.out.println("------------------row:" + id + ":" + name);
        System.out.println("------------------row:" + row);
        if (row == 1) {

            optionRepository.del(id);
            String[] options = optionsContent.split("\\|\\|\\|");
            Option option;
            for (String item : options) {
                //System.out.println(item);
                option = new Option(item, id);
                optionRepository.save(option);
            }
            return new ReturnWithoutData("成功", "100");
        } else {
            return new ReturnWithoutData("失败", "101");
        }
    }

}
