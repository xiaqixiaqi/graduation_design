package com.example.graduation_design.controller;

import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    //用户注册
    @RequestMapping(value = "/registration")
    public String registration(){
        return "/Front/registration.html";
    }
    //用户注册
    @RequestMapping("/registered")
    public String registered(@RequestParam("username")String username,@RequestParam("password")String password){
        return "/Front/registration.html";
    }
    //返回后台添加管理员页面
    @RequestMapping(value = "/addAdministrator")
    public String addAdministrator(){
        return "/Background/addAdministrator.html";
    }
    //后台：添加管理员
    @RequestMapping(value = "/addingAdministrator")
    public String addingAdministrator(@RequestParam("realname")String realname,@RequestParam("aAge")String aAge,
                                      @RequestParam("phone")String phone,@RequestParam("username")String username){
        userService.addingAdministrator(realname,aAge,phone,username);
        return "/errorOrsuccess/BackgroundSuccess.html";
    }
    //后台：显示所有的管理员
    @RequestMapping(value = "/showAllAdministrator")
    public ModelAndView showAllAdministrator(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAdministrators.html");
        modelAndView.addObject("administrators",userService.findAllUserByRole(2));
        return modelAndView;
    }

}
