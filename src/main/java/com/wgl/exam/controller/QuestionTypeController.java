package com.wgl.exam.controller;


import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
import com.wgl.exam.uti.Common;
import com.wgl.exam.uti.UserType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RequestMapping("questionType")
@Controller
public class QuestionTypeController {

    @Autowired
    QuestionTypeRepository questionTypeRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "")
    public String getAll(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "题型管理");
        List<QuestionType> questionTypes = questionTypeRepository.findAll();

        //System.out.println(questionTypes.size());

        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("questionTypes", questionTypes);
        return "questiontype";
    }

    @PostMapping(value = "add")
    @ResponseBody
    public ReturnWithData add(@RequestParam("name") String name) {


        if (questionTypeRepository.findByName(name) != null)

            return new ReturnWithData(name + "已存在", "101", null);

        QuestionType questionType = new QuestionType(name);


        return new ReturnWithData("成功", "100", questionTypeRepository.save(questionType));
    }

    @PostMapping(value = "update")
    @ResponseBody
    @Transactional
    public ReturnWithoutData update(@RequestParam("id") Long id, @RequestParam("name") String name) {

        QuestionType questionType = questionTypeRepository.findByName(name);
        if (questionType != null)

            return new ReturnWithoutData(name + "已存在", "101");


        int row = questionTypeRepository.update(id, name);
        //System.out.println("------------------row:" + id + ":" + name);
        //System.out.println("------------------row:" + row);
        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }


    }

    @PostMapping(value = "id")
    @ResponseBody
    public ReturnWithData getById(@RequestParam("id") Long id) {

        QuestionType questionType = questionTypeRepository.findOne(id);

        if (questionType != null) {

            return new ReturnWithData("成功", "100", questionTypeRepository.save(questionType));
        } else {

            return new ReturnWithData("失败", "101", null);
        }
    }

    @PostMapping(value = "name")
    @ResponseBody
    public ReturnWithData getByName(@RequestParam("name") String name) {

        QuestionType questionType = questionTypeRepository.findByNameAndIsDelete(name, 0);

        if (questionType != null) {

            return new ReturnWithData("成功", "100", questionTypeRepository.save(questionType));
        } else {

            return new ReturnWithData("失败", "101", null);
        }
    }


    @RequestMapping("getExcel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//http://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("题目类型表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("题目类型");
        Common.createTitle(workbook, sheet,new String[]{"序号","名称"});

        List<QuestionType> questionTypes = questionTypeRepository.findAll();

        int rowNum = 1;
        for (QuestionType entity : questionTypes) {
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

            rowNum++;
        }
        workbook.write(response.getOutputStream());
    }

    @PostMapping(value = "del")
    @ResponseBody
    @Transactional
    public ReturnWithoutData del(@RequestParam("id") Long id) {

        int row = questionTypeRepository.del(id);

        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }
    }
}
