package com.example.graduation_design.controller;

import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.Evaluation;
import com.example.graduation_design.bean.ShoppingOrder;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.EvaluationService;
import com.example.graduation_design.service.OrderService;
import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class EvaluationController {
    @Resource
    EvaluationService evaluationService;
    @Resource
    OrderService orderService;
    @Resource
    BookService bookService;
    //添加评论
//    @RequestMapping(value = "/user/addEvaluation")
//    public String addEvaluation(@RequestParam("comment")String content, @RequestParam("bookId")int bookId, RedirectAttributes attr){
//        evaluationService.addEvaltion(1,bookId,content);
//        String bookName=bookService.findBookByBookId(bookId).getBookName();
//        attr.addAttribute("bookId", bookId);//跳转地址带上bookname参数
//        System.out.println(bookName);
//        return "redirect:bookDetail";
//    }
    @RequestMapping(value = "/user/addEvaluate")
    public ModelAndView addEvaluate(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/addOrderEvaluation.html");
        modelAndView.addObject("orderItems",orderService.findOrderItemByUserIdAndIsPayment(user.getUserId(),3));
        return modelAndView;
    }
    @RequestMapping(value = "/user/addEvaluating")
    public String addEvaluating(@RequestParam("orderItemId")int orderItemId,@RequestParam("praise")int praise,@RequestParam("comment")String comment,HttpSession session){
        User user= (User) session.getAttribute("USER");
        //praise为1时为好评，为0时为差评
        evaluationService.addEvaluation(orderItemId,praise,comment,user.getUserId());
        return "redirect:/user/addEvaluate";
    }
    @RequestMapping(value = "/user/showMyEvaluation")
    public ModelAndView showMyEvaluation(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/orderEvaluation.html");
        modelAndView.addObject("evaluations",evaluationService.findAllEvaluationByUserId(user.getUserId()));
        return modelAndView;
    }
    //评价分析
    @RequestMapping(value = "/business/praiseAnalysis")
    public ModelAndView praise(HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/praiseAnalysis.html");
        modelAndView.addObject("data",evaluationService.findAllBookPraise(business.getbId()));
        return modelAndView;
    }
    @RequestMapping(value = "/business/showBackEvaluation")
    public String showBackEvaluation(){
        return "/Background/showbookpraise.html";
    }
    ///后台:查询该书籍的所有评价
    @RequestMapping(value = "/business/researchEvaluation")
    public ModelAndView showBookPraise(@RequestParam("bookName")String bookName,HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showbookpraise.html");
        modelAndView.addObject("evaluations",bookService.findAllEvaluation(business.getbId(),bookName));
        return modelAndView;
    }
}
