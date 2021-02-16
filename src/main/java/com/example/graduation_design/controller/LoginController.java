package com.example.graduation_design.controller;

import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.SlideService;
import com.example.graduation_design.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private BookService bookService;
    @Resource
    private SlideService slideService;
    //登入页面
    @RequestMapping(value = "/login")
    public String login(){
        System.out.println("进入登入页面aaa");
        return "login.html";
    }

    @RequestMapping(value = "/logined")
    public String logined(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession session) {
        System.out.println("正在处理登入请求");

        //如果登入成功返回个人页面,否则回到首页
        if (userService.logining(username, password))
        {
            session.setAttribute("USER", userService.findUserByUsername(username));
            System.out.println("已登录成功");
            return "redirect:/addBook";
        }else{
            return "redirect:/login";
        }

    }
    //首页
    @RequestMapping(value = "/index")
    public ModelAndView addBook(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/index.html");//转发到index.html
        modelAndView.addObject("slideImg",slideService.findAllSlideImg());//封装数据，用于前端页面，这里返回的是list
        modelAndView.addObject("books",bookService.findAllBookByBookType());//显示所有书籍
        return modelAndView;
    }

}
