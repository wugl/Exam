package com.wgl.exam.controller;


import com.wgl.exam.Repository.QuestionTypeRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import com.wgl.exam.domain.QuestionType;
import com.wgl.exam.domain.User;
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
    public String getAll(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
        map.put("title", "题目类型");
        List<QuestionType> questionTypes = questionTypeRepository.findAll();

        System.out.println(questionTypes.size());

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
        System.out.println("------------------row:" + id + ":" + name);
        System.out.println("------------------row:" + row);
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

    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(0, 12 * 256);
        sheet.setColumnWidth(1, 40 * 256);

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        HSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("序号");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("名称");
        cell.setCellStyle(style);


    }

    @RequestMapping("getExcel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//http://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("题目类型表");
        createTitle(workbook, sheet);

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

    @PostMapping(value = "/del")
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
