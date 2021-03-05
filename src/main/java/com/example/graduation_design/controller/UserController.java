package com.example.graduation_design.controller;

import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.BusinessService;
import com.example.graduation_design.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.jar.Attributes;

@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private BookService bookService;
    @Resource
    private BusinessService businessService;
    //用户注册
    @RequestMapping(value = "/registration")
    public String registration(){
        return "Front/registration.html";
    }
    //用户注册
    @RequestMapping("/registered")
    public String registered(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println(username+password);
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return "redirect:/login";
    }
    //返回后台添加管理员页面
    @RequestMapping(value = "/business/addAdministrator")
    public String addAdministrator(){
        return "/Background/addAdministrator.html";
    }
    //后台：添加管理员
    @RequestMapping(value = "/business/addingAdministrator")
    public String addingAdministrator(@RequestParam("realname")String realname,@RequestParam("aAge")String aAge,
                                      @RequestParam("phone")String phone,@RequestParam("username")String username){
        userService.addingAdministrator(realname,aAge,phone,username);
        return "/errorOrsuccess/BackgroundSuccess.html";
    }
    //后台：显示所有的管理员
    @RequestMapping(value = "/business/showAllAdministrator")
    public ModelAndView showAllAdministrator(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAdministrators.html");
        modelAndView.addObject("administrators",userService.findAllUserByRole(2));
        return modelAndView;
    }
    //前台：个人资料
    @RequestMapping(value = "/user/personalData")
    public ModelAndView showPersonalData(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/personalData.html");
        modelAndView.addObject("user",userService.findUserByUsername(user.getUsername()));
        return modelAndView;
    }
    //前台：修改登录密码的页面
    @RequestMapping(value = "/user/updateUserPassword")
    public ModelAndView updateUserPassword(@RequestParam("tip") String tip, HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/updateUserPassword.html");
        modelAndView.addObject("user",user);
        modelAndView.addObject("tip",tip);
        return modelAndView;
    }
    //前台：显示用户的所有收货地址
    @RequestMapping(value = "/user/showAllReceiptAddress")
    public ModelAndView showAllReceiptAddress(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/showReceiptAddress.html");
        modelAndView.addObject("allAddress",userService.getAllReceiptsAddressesByUser(user.getUserId()));
        return modelAndView;
    }
    //前台：更新用户信息
    @RequestMapping(value = "/user/updatingUser")
    public String updatingUser(@RequestParam("imgAddress") MultipartFile[] imgAddress, @RequestParam("account")String account, @RequestParam("realname")String realname,
                               @RequestParam("sex")int sex, @RequestParam("address")String address,HttpSession session){
        System.out.println("进入修改用户信息。。。。");
        User user= (User) session.getAttribute("USER");
        userService.updatingUser(imgAddress,account,realname,sex,address,user.getUserId());
        return "forward:/personalData";
    }
    //修改密码的处理
    @RequestMapping(value = "/user/updatePasswording")
    public String updatePasswording(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword,
                                    HttpSession session,RedirectAttributes attributes){
        attributes.addAttribute("tip","修改成功");
        User user=(User)session.getAttribute("USER");
        if (oldPassword==user.getPassword()||oldPassword.equals(user.getPassword())){
            userService.upateUserPassword(user.getUserId(),newPassword);
        }
        return "redirect:/user/updateUserPassword";
    }
    //删除收货地址
    @RequestMapping(value = "/user/deleteReceiptAddress",method = RequestMethod.GET)
    public String deleteReceiptAddress(@RequestParam("raId")int raId){
        userService.deleteReceiptAddress(raId,0);
        return "redirect:/user/showAllReceiptAddress";
    }
    //取消管理员权限
    @RequestMapping(value = "/business/cancelAdministrator",method = RequestMethod.GET)
    public String cancelAdministrator(@RequestParam("userId")int userId){
        userService.cancelAdministrator(userId);
        return "redirect:/business/showAllAdministrator";
    }
    //主页的搜索
    @RequestMapping(value = "/user/research")
    public ModelAndView indexResearch(@RequestParam("content")String content){
        System.out.println("进入搜索查询页面");
        ModelAndView modelAndView=new ModelAndView("/Front/researchBookOrStore.html");
        modelAndView.addObject("books",bookService.findBookByBookNameLike(content));
        modelAndView.addObject("stores",businessService.findBusinessByStoreNameLike(content));
        return modelAndView;
    }
}
