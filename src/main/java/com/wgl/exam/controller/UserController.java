package com.wgl.exam.controller;


import com.google.gson.Gson;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/user/add")
    @ResponseBody
    public ReturnWithData add(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("type") Integer type) {

        //System.out.println(name);

        if (userRepository.findUserByNameAndIsDelete(name, 0) != null)

            return new ReturnWithData(name + "已存在", "101", null);

        User user = new User(UserType.getByIndex(type), name, password, email, phone);


        return new ReturnWithData("成功", "100", userRepository.save(user));
    }

    @PostMapping(value = "/reg")
    @ResponseBody
    public ReturnWithData reg( HttpSession session,@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("type") Integer type) {

        if(!type.equals(1)){
            return new ReturnWithData("只允许学生注册", "101", null);
        }

        if (userRepository.findUserByNameAndIsDelete(name, 0) != null)

            return new ReturnWithData(name + "已存在", "101", null);

        User user = new User(UserType.getByIndex(type), name, password, email, phone);
        User u =  userRepository.save(user);

        if (u != null) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_ID, user.getId());
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME, user.getName());
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE, 1);

            Map result = new HashMap<String, Object>();
            result.put("name", user.getName());
            result.put("type", 1);
            result.put("phone", user.getPhone());
            result.put("email", user.getEmail());
            result.put("id", user.getId());
            result.put("time", new Date().getTime());
            result.put("regTime", user.getRegTime());
            return new ReturnWithData("成功", "100",result);
        }


        return new ReturnWithData("失败", "101", null);
    }
    @PostMapping(value = "user/del")
    @ResponseBody
    @Transactional
    public ReturnWithoutData del(@RequestParam("id") Long id) {
        int row = userRepository.del(id);
        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }
    }

    @PostMapping(value = "user/update")
    @ResponseBody
    @Transactional
    public ReturnWithoutData update(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phone") String phone, @RequestParam("password") String password, @RequestParam("type") Integer type) {

        User user = userRepository.findUserByNameAndIsDelete(name, 0);
        if (user != null && userRepository.findUserByIdAndNameAndIsDelete(id, name, 0) == null)

            return new ReturnWithoutData(name + "已存在", "101");


        int row = userRepository.update(id, name, password, UserType.getByIndex(type), email, phone);
        //System.out.println("------------------row:" + id + ":" + name);
        //System.out.println("------------------row:" + row);
        if (row == 1)
            return new ReturnWithoutData("成功", "100");
        else {
            return new ReturnWithoutData("失败", "101");
        }


    }
    @GetMapping(value = "login")
    public String getLogin(Map<String, Object> map, HttpSession session) {
        Object id = session.getAttribute(WebSecurityConfig.SESSION_KEY_USER_ID);
        if (id == null) {
            map.put("title", "login");
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping(value = "userdetail")
    public String userDetail(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME) String name, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id) {
        map.put("title", "用户简介");
        User user = userRepository.findUserByIdAndIsDelete(id, 0);
        map.put("user", user);
        map.put("userJson", new Gson().toJson(user));

        return "userdetail";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public ReturnWithData login(@RequestParam String name, @RequestParam String password, @RequestParam Integer userType,
                                HttpSession session) {
        UserType type;
        switch (userType) {
            case 1:
                type = UserType.STUDENT;
                break;
            case 2:
                type = UserType.TEACHER;
                break;
            default:
                type = UserType.MANAGER;
                break;
        }
        //logger.error(name + ":" + password + ":" + userType);

        User user = userRepository.findUserByNameAndPasswordAndTypeAndIsDelete(name, password, type, 0);

//        logger.error(user.getType().getName());
        if (user != null) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_ID, user.getId());
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME, user.getName());
            session.setAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE, userType);

            Map result = new HashMap<String, Object>();
            result.put("name", user.getName());
            result.put("type", userType);
            result.put("phone", user.getPhone());
            result.put("email", user.getEmail());
            result.put("id", user.getId());
            result.put("time", new Date().getTime());
            result.put("regTime", user.getRegTime());
            return new ReturnWithData("登陆成功", "100", result);
        } else {
            user = userRepository.findUserByNameAndTypeAndIsDelete(name, type, 0);
            if (user == null)
                return new ReturnWithData("用户名不存在", "101", null);
            else
                return new ReturnWithData("密码不正确", "101", null);
        }


    }

    @PostMapping("logout")
    @ResponseBody
    public ReturnWithoutData logout(HttpSession session) {
        // 移除session
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_ID);
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_NAME);
        session.removeAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE);
        return new ReturnWithoutData("注销成功", "100");
    }

    @GetMapping("usermanager")
    public String userManger(Map<String, Object> map, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_ID) Long id, @SessionAttribute(WebSecurityConfig.SESSION_KEY_USER_TYPE) Integer type) {
        //System.out.println(type + "-------------manager:" + UserType.MANAGER.getIndex());
        if (type != UserType.MANAGER.getIndex())

            return "redirect:/";

        User user = userRepository.findUserByIdAndIsDelete(id, 0);
        map.put("title", "用户管理");
        map.put("user", user);
        map.put("allUsers", userRepository.findAll());

        return "usermanager";

    }


    @RequestMapping("user/getExcel")
    public void getExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
//http://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户数据表", "UTF-8") + ".xls");
        //编码
        response.setCharacterEncoding("UTF-8");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("用户数据");
        Common.createTitle(workbook, sheet,new String[]{"序号","名称","类型","邮箱","手机"});
        //createTitle(workbook, sheet);

        List<User> users = userRepository.findAll();

        int rowNum = 1;
        for (User entity : users) {
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
            cell.setCellValue(entity.getType().getName());

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getEmail());

            cell = row.createCell(4);
            cell.setCellStyle(style);
            cell.setCellValue(entity.getPhone());

            rowNum++;
        }
        workbook.write(response.getOutputStream());
    }
}
