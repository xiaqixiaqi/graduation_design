package com.example.graduation_design.controller;

import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.EvaluationService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
public class EvaluationController {
    @Resource
    EvaluationService evaluationService;
    @Resource
    BookService bookService;
    //添加评论
    @RequestMapping(value = "addEvaluation")
    public String addEvaluation(@RequestParam("comment")String content, @RequestParam("bookId")int bookId, RedirectAttributes attr){
        evaluationService.addEvaltion(1,bookId,content);
        String bookName=bookService.findBookByBookId(bookId).getBookName();
        attr.addAttribute("bookname", bookName);//跳转地址带上bookname参数
        System.out.println(bookName);
        return "redirect:bookDetail";
    }

}
