package com.example.graduation_design.controller;

import com.example.graduation_design.service.ShoppingCartService;
import com.example.graduation_design.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private UserService userService;
    @RequestMapping(value = "shopping")
    public String shopping(@RequestParam("bookNumber")int bookNumber ,@RequestParam("bookId")int bookId){
        System.out.println(bookNumber+"zzzzz"+bookId);
        shoppingCartService.addShoppingCartItem(bookNumber,bookId);
        return "redirect:/index";
    }
    //显示用户的购物车,需要用户的id
    @RequestMapping(value = "showShoppingCart")
    public ModelAndView showShoppingCart(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/shoppingCart.html");
        //获取id为1的购物车
        modelAndView.addObject("shoppingCart",shoppingCartService.findUserShoppingCart(1));
        return modelAndView;
    }
}
