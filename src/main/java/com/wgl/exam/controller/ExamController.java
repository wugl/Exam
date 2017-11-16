package com.wgl.exam.controller;


import com.google.gson.Gson;
import com.wgl.exam.Repository.*;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.*;
import com.wgl.exam.uti.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

@Controller
@RequestMapping("")
public class ExamController {

    @Autowired
    ExamRepository examRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionTypeRepository questionTypeRepository;

    @Autowired
    ExamQuestionRepository examQuestionRepository;

    @Autowired
    StudentAnswerRepository studentAnswerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("exam")
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "试卷管理");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("exams", examRepository.findAll());

        return "exam";

    }

    @GetMapping("myexam")
    public String studentExam(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.STUDENT.getIndex())

            return "redirect:/";

        map.put("title", "试卷列表");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        List<Exam> exams = examRepository.findAll();
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndAndIsDelete(id, 0);
        List<Exam> es = new ArrayList<>();
        for (Exam item : exams) {
            boolean isvalidate = true;
            if (item.getExamDate().before(new Date())) {
                isvalidate = false;
            }
            for (StudentAnswer e : studentAnswers) {
                if (e.getExamId().equals(item.getId())) isvalidate = false;
            }
            if (isvalidate)
                es.add(item);
        }
        map.put("exams", es);
        return "myexam";

    }

    @GetMapping("startexam")
    public String studentStartExam(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type, @RequestParam("examId") Long examId) {
        if (type != UserType.STUDENT.getIndex())

            return "redirect:/";

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndAndIsDelete(id,0);
        for (StudentAnswer sa: studentAnswers){
            if(sa.getExamId().equals(examId)){
                return "redirect:/";
            }
        }

        map.put("title", "开始测试");
        //map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        Exam exam = examRepository.findExamByIdAndIsDelete(examId, 0);

        for (Question item : exam.getQuestions()) {
            item.setAnswer("");
            item.setComment("");
            item.setType(questionTypeRepository.findByIdAndIsDelete(item.getTypeId(), 0).getName());
        }

        map.put("exam", new Gson().toJson(exam));

        return "startexam";

    }

    @PostMapping("examsubmit")
    @ResponseBody
    public String studentSubmit(@SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type, @RequestParam("answer") String answer, @RequestParam("examId") Long examId) {
        if (type != UserType.STUDENT.getIndex())

            return new Gson().toJson(new ReturnWithoutData("不是学生用户", "101"));
        System.out.println(answer);
        Map<String, String> result = new Gson().fromJson(answer, Map.class);
        if(studentAnswerRepository.findByExamIdAndAndIsDelete(examId,0).size()>0){
            return new Gson().toJson(new ReturnWithoutData("您已提交，不要再次提交！", "101"));
        }

        //System.out.println(result.size());

        for (Map.Entry<String, String> entry : result.entrySet()) {

            System.out.println(entry.getKey().getClass().toString());
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            studentAnswerRepository.save(new StudentAnswer(examId, id, Long.parseLong(entry.getKey()), entry.getValue()));

        }

        return new Gson().toJson(new ReturnWithoutData("提交成功！", "100"));
    }

    @GetMapping("myexamhistory")
    public String studentExamHistory(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.STUDENT.getIndex())

            return "redirect:/";

        map.put("title", "考试记录");
        User user = userRepository.findUserByIdAndIsDelete(id, 0);
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        System.out.println(new Gson().toJson(user));
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndAndIsDelete(id, 0);
        List<Exam> exams = new ArrayList<>();
        Exam exam = null;
        Question question;

        for (StudentAnswer e : studentAnswers) {
            boolean shouldAdd = true;
            question = questionRepository.findQuestionByIdAndIsDelete(e.getQuestionId(), 0);
            for (Exam item : exams) {
                if (item.getId().equals(e.getExamId())) {
                    shouldAdd = false;
                    exam = item;
                    if (e.getAnswer().equals(question.getAnswer())) {
                        item.setStudentScore(item.getStudentScore() + question.getScore());
                    }
                }
            }
            if (shouldAdd) {
                exam = examRepository.findExamByIdAndIsDelete(e.getExamId(), 0);
                exam.setAnswerDate(e.getAnswerDate());
                exam.setAnswerName(userRepository.findUserByIdAndIsDelete(e.getStudentId(),0).getName());

                if (e.getAnswer().equals(question.getAnswer())) {
                    exam.setStudentScore(question.getScore());

                }
                exams.add(exam);

                //System.out.println(new Gson().toJson(examRepository.findExamByIdAndIsDelete(e.getExamId(), 0)));
            }
            for (Question q : exam.getQuestions()) {
                q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());

                if (q.getId().equals(e.getQuestionId()))
                    q.setStudentAnswer(e.getAnswer());
            }
        }
        map.put("exams", exams);
        map.put("examsJson", new Gson().toJson(exams));


        return "myexamhistory";

    }

    @GetMapping("examhistory")
    public String examHistory(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "考试记录");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findAll();
        List<Exam> exams = new ArrayList<>();
        Exam exam = null;
        Question question;

        for (StudentAnswer e : studentAnswers) {
            boolean shouldAdd = true;
            question = questionRepository.findQuestionByIdAndIsDelete(e.getQuestionId(), 0);
            for (Exam item : exams) {
                if (item.getId().equals(e.getExamId())) {
                    shouldAdd = false;
                    exam = item;
                    if (e.getAnswer().equals(question.getAnswer())) {
                        item.setStudentScore(item.getStudentScore() + question.getScore());
                    }
                }
            }
            if (shouldAdd) {
                exam = examRepository.findExamByIdAndIsDelete(e.getExamId(), 0);
                exam.setAnswerDate(e.getAnswerDate());
                exam.setAnswerName(userRepository.findUserByIdAndIsDelete(e.getStudentId(),0).getName());

                if (e.getAnswer().equals(question.getAnswer())) {
                    exam.setStudentScore(question.getScore());

                }


                exams.add(exam);

                //System.out.println(new Gson().toJson(examRepository.findExamByIdAndIsDelete(e.getExamId(), 0)));
            }
            for (Question q : exam.getQuestions()) {
                q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());
                if (q.getId().equals(e.getQuestionId()))
                    q.setStudentAnswer(e.getAnswer());
            }
        }
        map.put("exams", exams);
        map.put("examsJson", new Gson().toJson(exams));

        return "myexamhistory";

    }

    @GetMapping("exam/getById")
    @ResponseBody
    public ReturnWithData getById(@RequestParam("id") Long id) {

        Exam exam = examRepository.findExamByIdAndIsDelete(id, 0);
        for (Question q : exam.getQuestions()) {
            q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());
        }


        return new ReturnWithData("成功", "100", exam);

    }

    @GetMapping("exam/new")
    public String add(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));

        map.put("title", "新增试卷");
        map.put("type", "1");//1 new , 2 edit
        return "editexam";

    }

    @PostMapping("exam/add")
    @ResponseBody
    public ReturnWithData addPost(@RequestParam("name") String name, @RequestParam("questions") String questions, @RequestParam("examDate") String date, @RequestParam("totalTime") Integer time, @RequestParam("totalScore") Float totalScore, @RequestParam("passScore") Float passScore) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //System.out.println(date);

        try {
            Exam exam = new Exam(name, totalScore, passScore, time, sdf.parse(date));
            //System.out.println(sdf.parse(date).getTime());
            Exam e = examRepository.save(exam);


            for (String item : questions.split("\\|")) {
                //System.out.println(item);
                examQuestionRepository.save(new ExamQuestion(e.getId(), Long.parseLong(item)));

            }
            return new ReturnWithData("成功", "100", e);
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnWithData("日期格式不对", "101", null);
        }


    }

    @GetMapping("exam/edit")
    public String edit(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @RequestParam("id") Long examId) {

        Exam exam = examRepository.findExamByIdAndIsDelete(examId, 0);
        map.put("exam", exam);
        List<Question> questions = exam.getQuestions();

        for (Question q : questions) {
            q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());
        }

        map.put("questions", new Gson().toJson(exam.getQuestions()));
        System.out.println(new Gson().toJson(exam.getQuestions()));
        System.out.println(examRepository.findExamByIdAndIsDelete(examId, 0).getName());
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("title", "编辑试卷");

        map.put("type", "2");//1 new , 2 edit
        return "editexam";

    }

    @PostMapping("exam/update")
    @ResponseBody
    @Transactional
    public ReturnWithoutData editPost(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("questions") String questions, @RequestParam("examDate") String date, @RequestParam("totalTime") Integer time, @RequestParam("totalScore") Float totalScore, @RequestParam("passScore") Float passScore) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //System.out.println(date);


        try {
            Date d = sdf.parse(date);
            //Exam exam = new Exam(name, totalScore, passScore, time, sdf.parse(date));
            //System.out.println(sdf.parse(date).getTime());
            int row = examRepository.update(id, d, name, time, totalScore, passScore);

            if (row > 0) {
                examQuestionRepository.delByExamId(id);
                for (String item : questions.split("\\|")) {
                    //System.out.println(item);

                    examQuestionRepository.save(new ExamQuestion(id, Long.parseLong(item)));

                }
                return new ReturnWithoutData("成功", "100");
            } else {
                return new ReturnWithoutData("更新失败", "101");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnWithoutData("日期格式不对", "101");
        }

    }

    @PostMapping(value = "exam/del")
    @ResponseBody
    @Transactional
    public ReturnWithoutData del(@RequestParam("id") Long id) {

        int row = examRepository.del(id);

        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }
    }
}
