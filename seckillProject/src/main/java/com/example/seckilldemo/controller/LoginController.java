package com.example.seckilldemo.controller;

import com.example.seckilldemo.imp.TUserServiceImpl;
import com.example.seckilldemo.utils.MD5util;
import com.example.seckilldemo.vo.RespBean;
import com.example.seckilldemo.vo.logintemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    /**
     * 返回一个登录页面
     *
     */
    @Autowired
    TUserServiceImpl loginService;


    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login111";
    }
    //生成用户cookie
    @RequestMapping("/toLogintest")
    public String toLogintest(){
        return "login";
    }

    @RequestMapping("/doLogintest")
    @ResponseBody
    //生成用户使用
    public RespBean doLogintest(logintemp temp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        temp.setPassword(MD5util.inputPassToFromPass(temp.getPassword()));
        log.warn("{}",temp);
        return loginService.doLogintest(temp,request,response);
    }

    @RequestMapping({"/doLogin"})
    @ResponseBody
    public String doLogin(logintemp temp, HttpServletRequest request, HttpServletResponse response) {
        temp.setPassword(MD5util.inputPassToFromPass(temp.getPassword()));
        log.warn("{}", temp);
        return this.loginService.doLogin(temp, request, response);
    }
}
