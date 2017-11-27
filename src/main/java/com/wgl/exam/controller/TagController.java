package com.wgl.exam.controller;



import com.wgl.exam.Repository.TagRepository;
import com.wgl.exam.Repository.UserRepository;
import com.wgl.exam.WebSecurityConfig;
import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;

import com.wgl.exam.domain.Tag;
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

@RequestMapping("tag")
@Controller
public class TagController {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "")
    public String getAll(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) int type) {
        if (type != UserType.TEACHER.getIndex())

            return "redirect:/";

        map.put("title", "维度管理");
        List<Tag> tags = tagRepository.findAll();

        //System.out.println(questionTypes.size());

        map.put("user", userRepository.findUserByIdAndIsDelete(id, 0));
        map.put("tags", tags);
        return "tag";
    }

    @PostMapping(value = "add")
    @ResponseBody
    public ReturnWithData add(@RequestParam("name") String name) {

        if (tagRepository.findByName(name) != null)

            return new ReturnWithData(name + "已存在", "101", null);

        Tag tag = new Tag(name);

        return new ReturnWithData("成功", "100", tagRepository.save(tag));
    }

    @PostMapping(value = "update")
    @ResponseBody
    @Transactional
    public ReturnWithoutData update(@RequestParam("id") Long id, @RequestParam("name") String name) {

        Tag tag = tagRepository.findByName(name);
        if (tag != null)

            return new ReturnWithoutData(name + "已存在", "101");


        int row = tagRepository.update(id, name);
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

        Tag tag = tagRepository.findOne(id);

        if (tag != null) {

            return new ReturnWithData("成功", "100", tagRepository.save(tag));
        } else {

            return new ReturnWithData("失败", "101", null);
        }
    }

    @PostMapping(value = "name")
    @ResponseBody
    public ReturnWithData getByName(@RequestParam("name") String name) {

        Tag tag = tagRepository.findByNameAndIsDelete(name, 0);

        if (tag != null) {

            return new ReturnWithData("成功", "100", tagRepository.save(tag));
        } else {

            return new ReturnWithData("失败", "101", null);
        }
    }


    @RequestMapping("getExcel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//http://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("题目维度表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("题目维度");
        Common.createTitle(workbook, sheet,new String[]{"序号","名称"});

        List<Tag> tags = tagRepository.findAll();

        int rowNum = 1;
        for (Tag entity : tags) {
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

        int row = tagRepository.del(id);

        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }
    }
}
