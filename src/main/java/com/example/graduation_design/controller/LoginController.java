package com.example.graduation_design.controller;

import com.example.graduation_design.bean.ShoppingOrder;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.*;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @Resource
    private AuthorService authorService;
    @Resource
    private OrderService orderService;
    //登入页面
    @RequestMapping(value = "/login")
    public String login(){
        System.out.println("进入登入页面aaa");
        return "login.html";
    }
    @RequestMapping(value = "/backLogin")
    public String backLogin(){
        return "/backLogin.html";
    }
    @RequestMapping(value = "/backlogined")
    public String backlogined(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession session) {
        System.out.println("正在处理登入请求");

        //如果登入成功返回个人页面,否则回到首页
        if (userService.logining(username, password))
        {
            User user=userService.findUserByUsername(username);
            session.setAttribute("USER", user);
            session.setAttribute("BUSINESS",user.getBusiness());
            System.out.println("测试session"+user.getBusiness().getStoreName());
//            session.setAttribute("ROLE",user);
            System.out.println("已登录成功");
            return "redirect:/business/addBook";
        }else{
            return "redirect:/login";
        }

    }
    @RequestMapping(value = "/logined")
    public String logined(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession session) {
        System.out.println("正在处理登入请求");

        //如果登入成功返回个人页面,否则回到首页
        if (userService.logining(username, password))
        {
            User user= userService.findUserByUsername(username);
            if (user.getBusiness()!=null){
                session.setAttribute("BUSINESS",user.getBusiness());
            }
            session.setAttribute("USER",user);
            System.out.println("已登录成功");
            return "redirect:/user/index";
        }else{
            return "redirect:/login";
        }

    }
    //首页
    @RequestMapping(value = "/user/index")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/index.html");//转发到index.html
        modelAndView.addObject("slideImg",slideService.findAllSlideImg());//封装数据，用于前端页面，这里返回的是list
        modelAndView.addObject("books",bookService.findAllBookByBookType());//显示所有书籍
        modelAndView.addObject("newBooks",bookService.findFourNewBook());//最新书籍
        return modelAndView;
    }
    @RequestMapping(value = "test")
    public String test(){
        //return "/Background/showNewOrderDetail.html";
        authorService.test();
        //orderService.findSaleByMonth(1,1,2021);
        return "/Background/showAuthorDetail.html";
    }


}
