package com.wgl.exam.controller;


import com.google.gson.Gson;
import com.wgl.exam.Repository.*;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.*;
import com.wgl.exam.uti.Common;
import com.wgl.exam.uti.UserType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
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

    @Autowired
    TagRepository tagRepository;

    @GetMapping("exam")
    public String index(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "试卷管理");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("exams", examRepository.findAll());

        return "exam";

    }


    @GetMapping("exam/getExcel")
    public void getExcel(HttpServletResponse response, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) throws IOException {
//        if (type != UserType.TEACHER.getIndex())
//
//            return "redirect:/";
        List<StudentAnswer> studentAnswers;

        if (type != UserType.TEACHER.getIndex())
            studentAnswers = studentAnswerRepository.findByStudentIdAndIsDelete(id, 0);
        else
            studentAnswers = studentAnswerRepository.findAll();

        List<Exam> exams = getExam(studentAnswers);


        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学生成绩表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生成绩");
        Common.createTitle(workbook, sheet, new String[]{"序号", "名称", "考试日期", "考试时长", "总分数", "及格分数", "答题人", "答题日期", "得分", "是否及格"});

        List<QuestionType> questionTypes = questionTypeRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int rowNum = 1;
        for (Exam entity : exams) {
            HSSFCellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            HSSFRow row = sheet.createRow(rowNum);
            HSSFCell cell;
            cell = row.createCell(0);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getId());

            cell = row.createCell(1);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getName());

            cell = row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(sdf.format(entity.getExamDate()));

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getTotalTime());

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getTotalScore());

            cell = row.createCell(5);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getPassScore());

            cell = row.createCell(6);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getAnswerName());

            cell = row.createCell(7);
            cell.setCellStyle(style);
            cell.setCellValue(sdf.format(entity.getAnswerDate()));

            cell = row.createCell(8);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getStudentScore());

            cell = row.createCell(9);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getStudentScore() < entity.getPassScore() ? "否" : "是");

            rowNum++;
        }
        workbook.write(response.getOutputStream());

        //  return "exam";

    }

    @GetMapping("myexam")
    public String studentExam(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.STUDENT.getIndex())

            return "redirect:/";

        map.put("title", "试卷列表");
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        List<Exam> exams = examRepository.findAll();
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndIsDelete(id, 0);
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

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndIsDelete(id, 0);
        for (StudentAnswer sa : studentAnswers) {
            if (sa.getExamId().equals(examId)) {
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
        if (studentAnswerRepository.findByStudentIdAndExamIdAndIsDelete(id, examId, 0).size() > 0) {
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

    @GetMapping("/exam/statics")
    @ResponseBody
    public ReturnWithData getExamStatics(@RequestParam("examId") Long examId) {

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByExamIdAndIsDelete(examId, 0);

        List<Exam> exams = getExam(studentAnswers);

        Map<String, Object> result = new HashMap<>();
        result.put("totalNum", exams.size());
        int failNum = 0;
        for (Exam e : exams) {
            if (e.getPassScore() > e.getStudentScore())
                failNum++;
        }
        result.put("failNum", failNum);

        return new ReturnWithData("成功", "100", result);
    }

    @GetMapping("/exam/studentstatics")
    @ResponseBody
    public ReturnWithData getExamStudentStatics(@RequestParam("examId") Long examId, @RequestParam("studentId") Long studentId) {

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndExamIdAndIsDelete(studentId, examId, 0);

        Exam exam = getExam(studentAnswers).get(0);

        Map<String, Object> result = new HashMap<>();
        List<Tag> tags = tagRepository.findAll();
        String[] ts = new String[tags.size()];
        Float[] ss = new Float[tags.size()];
        Tag item;
        for (int i=0;i<tags.size();i++) {
            item = tags.get(i);
            ts[i] = item.getName();

            float score=0f;
            for (Question q : exam.getQuestions()) {
                if (q.getTag().equals(item.getName()) && q.getAnswer().equals(q.getStudentAnswer()))
                    score+=q.getScore();
            }
            ss[i] = score;

        }
        //result.put("failNum", failNum);
        result.put("tags", ts);
        result.put("scores",ss);

        return new ReturnWithData("成功", "100", result);
    }

    @GetMapping("myexamhistory")
    public String studentExamHistory(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.STUDENT.getIndex())

            return "redirect:/";

        map.put("title", "考试记录");
        map.put("type", 1);
        User user = userRepository.findUserByIdAndIsDelete(id, 0);
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        System.out.println(new Gson().toJson(user));
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentIdAndIsDelete(id, 0);
        List<Exam> exams = getExam(studentAnswers);


        map.put("exams", exams);
        map.put("examsJson", new Gson().toJson(exams));


        return "myexamhistory";

    }

    @GetMapping("examhistory")
    public String examHistory(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type == UserType.STUDENT.getIndex())

            return "redirect:/";

        map.put("title", "考试记录");
        map.put("type", 2);
        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));

        List<StudentAnswer> studentAnswers = studentAnswerRepository.findAll();
        List<Exam> exams = getExam(studentAnswers);


        map.put("exams", exams);
        map.put("examsJson", new Gson().toJson(exams));

        return "myexamhistory";

    }

    private List<Exam> getExam(List<StudentAnswer> studentAnswers) {
        Exam exam;
        Question question;
        List<Exam> exams = new ArrayList<>();
        for (StudentAnswer e : studentAnswers) {
            boolean shouldAdd = true;
            question = questionRepository.findQuestionByIdAndIsDelete(e.getQuestionId(), 0);

            //System.out.println("----------------"+new Gson().toJson(e.getExam()));
            for (Exam item : exams) {
                if (item.getId().equals(e.getExamId()) && e.getStudentId().equals(item.getAnswerId())) {
                    shouldAdd = false;
                    exam = item;
                    if (e.getAnswer().equals(question.getAnswer())) {
                        exam.setStudentScore(exam.getStudentScore() + question.getScore());
                    }

                    for (Question q : exam.getQuestions()) {
                        if (q.getId().equals(e.getQuestionId()))
                            q.setStudentAnswer(e.getAnswer());
                    }
                    break;
                }

            }
            if (shouldAdd) {
                Exam temp = examRepository.findExamByIdAndIsDelete(e.getExamId(), 0);
                exam = new Exam(temp.getName(), temp.getTotalScore(), temp.getPassScore(), temp.getTotalTime(), temp.getExamDate());
                exam.setId(temp.getId());
                exam.setQuestions(temp.getQuestions());
                System.out.println("------" + exam.toString());
                exam.setAnswerDate(e.getAnswerDate());
                exam.setAnswerName(userRepository.findUserByIdAndIsDelete(e.getStudentId(), 0).getName());
                exam.setAnswerId(e.getStudentId());

                if (e.getAnswer().equals(question.getAnswer())) {
                    exam.setStudentScore(question.getScore());

                } else {
                    exam.setStudentScore(0F);
                }
                for (Question q : exam.getQuestions()) {
                    q.setType(questionTypeRepository.findByIdAndIsDelete(q.getTypeId(), 0).getName());
                    q.setTag(tagRepository.findByIdAndIsDelete(q.getTagId(), 0).getName());
                }

                for (Question q : exam.getQuestions()) {
                    if (q.getId().equals(e.getQuestionId()))
                        q.setStudentAnswer(e.getAnswer());
                }
                exams.add(exam);

            }


        }
        //System.out.println(new Gson().toJson(exams));
        return exams;
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
            q.setTag(tagRepository.findByIdAndIsDelete(q.getTagId(), 0).getName());
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
