package com.wgl.exam.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.ueditor.ActionEnter;

@WebServlet(name = "UEditorServlet", urlPatterns = "/UEditor")
public class UEditorController extends HttpServlet {

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        PrintWriter out = response.getWriter();
        ServletContext application=this.getServletContext();
        String rootPath = application.getRealPath( "/" );
        //System.out.println("-----------------------------------------");
        //System.out.println(rootPath);

        String action = request.getParameter("action");
        //System.out.println(action);
        String result = new ActionEnter( request, rootPath).exec();
        //System.out.println(result);
        //,UEditorController.class.getClassLoader().getResource("config.json").getPath()
        if( action!=null &&
                (action.equals("listfile") || action.equals("listimage") ) ){
            rootPath = rootPath.replace("\\", "/");
            //rootPath=request.getContextPath()+rootPath;
            //System.out.println(rootPath);
            result = result.replaceAll(rootPath, request.getContextPath()+"/");
        }
        out.write( result );
    }

} 