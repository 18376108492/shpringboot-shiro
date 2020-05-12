package com.itdan.shiro.demo.controller;

import com.itdan.shiro.demo.entry.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @RequestMapping("/")
    public String toIndex(){
        return "index";
    }

    //不支持直接跳转，所以要写跳转方法

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(final  String username,
                              final String password){
        //使用shiro编写认证操作
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username ,password);
        //3.执行登入方法
        ModelAndView view=new ModelAndView();
        try {
           subject.login(token);
           //登入成功
            view.setViewName("index");
            return view;
        }catch (UnknownAccountException uae){
            view.addObject("用户名不存在");
            view.setViewName("login");
            return view;
        }catch (IncorrectCredentialsException e){
           view.addObject("密码错误");
            view.setViewName("login");
            return view;
        }


          }
}
