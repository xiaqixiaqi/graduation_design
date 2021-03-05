package com.example.graduation_design.controller;

import com.example.graduation_design.bean.ReceiptsAddress;
import com.example.graduation_design.bean.ShoppingCartItem;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.ShoppingCartService;
import com.example.graduation_design.service.UserService;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ShoppingCartController {
    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private UserService userService;
    @Resource
    private BookService bookService;
    @RequestMapping(value = "/user/shopping")
    public String shopping(@RequestParam("bookNumber")int bookNumber , @RequestParam("bookId")int bookId, HttpSession session){
        User user= (User) session.getAttribute("USER");
        shoppingCartService.addShoppingCartItem(bookNumber,bookId,user.getUserId());
        return "redirect:/user/index";
    }
    //显示用户的购物车,需要用户的id
    @RequestMapping(value = "/user/showShoppingCart")
    public ModelAndView showShoppingCart(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/shoppingCart.html");
        //获取id为1的购物车
        modelAndView.addObject("shoppingCart",shoppingCartService.findUserShoppingCart(1));
        return modelAndView;
    }
    //前台：用户给添加新的地址信息
    @RequestMapping(value = "/user/addAddress")
    public String addAddress(){
        return "/Front/addAddress.html";
    }
    //前台：用户收货地址的修改
    @RequestMapping(value = "/user/updateReceiptAddress",method = RequestMethod.GET)
    public ModelAndView updateReceiptAddress(@RequestParam("raId")int raId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/addAddress.html");
        modelAndView.addObject("address",userService.findReceiptsAddresByRaId(raId));
        return modelAndView;
    }
    //前台：处理订单和个人信息的的添加新地址
    @RequestMapping(value = "/user/addAddressing")
    public String addAddressing(@RequestParam("address")String address,@RequestParam("addressDetail")String addressDetail,
                                @RequestParam("zipCode")String zipCode, @RequestParam("consignee")String consignee,
                                @RequestParam("phone")String phone,@RequestParam("isDefault")int isDefault,@RequestParam("isUpdate") int isUpdate,HttpSession session){
        User user= (User) session.getAttribute("USER");
        System.out.println(user.getUsername());
        System.out.println("isDefault"+isDefault);
        System.out.println("isUpdate"+isUpdate);
        if (isUpdate==0){
            userService.addAddressing(address,addressDetail,zipCode,consignee,phone,isDefault,userService.findUserByUserId(user.getUserId()));
        }else {
            userService.updateAddressing(address,addressDetail,zipCode,consignee,phone,isDefault,isUpdate);
        }
        return "";
    }
    //前台：添加订单
    @RequestMapping(value = "/user/addOrder")
    public ModelAndView addOrder(@RequestParam("selectShopping") int[] selectShopping, HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
       if (selectShopping==null){
           //未选择商品就添加的情况
           modelAndView.setViewName("/Front/shoppingCart.html");
           //获取id为1的购物车
           modelAndView.addObject("shoppingCart",shoppingCartService.findUserShoppingCart(1));
           modelAndView.addObject("message","还未选择商品");
       } else{
           modelAndView.setViewName("/Front/orderCommit.html");
           modelAndView.addObject("allReceiptsAddresses",userService.getAllReceiptsAddressesByUser(user.getUserId()));
           List<ShoppingCartItem> shoppingCartItems=shoppingCartService.findAllSelectShoppingCartItem(selectShopping);
           modelAndView.addObject("selectShoppings",shoppingCartItems);
           float sum=0;
           for (ShoppingCartItem s :shoppingCartItems) {
               sum=sum+s.getBookNumber()*s.getBook().getPrice();
           }
           modelAndView.addObject("totalPrice",sum);
       }
        return modelAndView;
    }
    //前台：直接购买商品
    @RequestMapping(value = "/user/directShopping")
    public ModelAndView directShopping(@RequestParam("bookId")int bookId,@RequestParam("bookNumber")int bookNumber,HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/orderCommit.html");
        modelAndView.addObject("allReceiptsAddresses",userService.getAllReceiptsAddressesByUser(user.getUserId()));
        List<ShoppingCartItem> shoppingCartItems=new LinkedList<>();
//        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
//        shoppingCartItem.setBookNumber(bookNumber);
//        shoppingCartItem.setBook(bookService.findBookByBookId(bookId));
        shoppingCartItems.add(shoppingCartService.addShoppingCartItemByUserIdAndNumber(bookId,bookNumber));
        System.out.println("进入这里"+shoppingCartItems.size());
        modelAndView.addObject("selectShoppings",shoppingCartItems);
        modelAndView.addObject("totalPrice",bookService.findBookByBookId(bookId).getPrice()*bookNumber);
        return modelAndView;
    }
    //前台：订单数量的增加
    @RequestMapping(value = "/user/addShoppingCartItemNumber",method = RequestMethod.GET)
    @ResponseBody
    public boolean addShoppingCartItemNumber(@RequestParam("shoppingCartItemId")int shoppingCartItemId,
                                            @RequestParam("number")int number){
        shoppingCartService.updateShoppingCartItemNumber(shoppingCartItemId,number+1);
        return true;
    }
    //前台：订单数量的减少
    @RequestMapping(value = "/user/reduceShoppingCartItemNumber",method = RequestMethod.GET)
    @ResponseBody
    public boolean reduceShoppingCartItemNumber(@RequestParam("shoppingCartItemId")int shoppingCartItemId,
                                            @RequestParam("number")int number){
        shoppingCartService.updateShoppingCartItemNumber(shoppingCartItemId,number-1);
        return true;
    }
    //前台：订单的删除
    @RequestMapping(value = "/user/deleteShoppingCartItem",method = RequestMethod.GET)
    @ResponseBody
    public boolean deleteShoppingCartItem(@RequestParam("shoppingCartItemId")int shoppingCartItemId){
        shoppingCartService.updateShoppingCartItem(0,shoppingCartItemId);
        return true;
    }

}
