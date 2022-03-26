package com.example.seckilldemo.controller;


import com.example.seckilldemo.pojo.TUser;
import com.example.seckilldemo.rabbitmq.mqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class test {
    @Autowired
    mqSender sender;
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","tony");
        return "hello";
    }
//    @RequestMapping("/rabbitmq")
//    @ResponseBody
//    public String rbtest(TUser user){
//        if (user==null)
//            sender.sender("login in please");
//        else
//            sender.sender("welcome"+user.getNickname());
//        return null;
//    }
}
