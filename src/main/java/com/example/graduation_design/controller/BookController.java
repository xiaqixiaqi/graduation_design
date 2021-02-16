package com.example.graduation_design.controller;

import com.example.graduation_design.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
public class BookController {
    @Resource
    private BookService bookService;
    //书籍详情
    @RequestMapping(value ="/bookDetail",method = RequestMethod.GET )
    public ModelAndView bookDetail(@RequestParam("bookname")String bookname){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/bookDetail.html");
        modelAndView.addObject("book",bookService.findBookByBookName(bookname));
        return modelAndView;
    }
    //添加书籍
    @RequestMapping(value = "addBook")
    public ModelAndView addBook(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/addBook.html");//转发到addBook.html
        modelAndView.addObject("bookTypes",bookService.findAllBookType());//封装数据，用于前端页面，这里返回的是list
        return modelAndView;
    }
    @RequestMapping(value = "addingBook")
    public String addingBook(@RequestParam("bookName")String bookName,@RequestParam("price")float price,@RequestParam("inventory")int inventory,
                             @RequestParam("author")String author,@RequestParam("bookType")String bookType,
                             @RequestParam("introduction") String introduction,@RequestParam("imgAddress") MultipartFile[] imgAddress){
        bookService.addBook(bookName,price,inventory, author,bookType,introduction,imgAddress);

        return "redirect:/addBook";
    }
    //添加商品类型
    @RequestMapping(value = "addBookType")
    public String addBookType(){
        return "/Background/addBookType.html";
    }
    @RequestMapping(value = "addingBookType")
    public String addingBookType(@RequestParam("bTypeName")String bTypeName){
        bookService.addBookType(bTypeName);
        return "/Background/addBookType.html";
    }
    //按照书的类型显示改类型的所有书籍
    @RequestMapping(value = "/booksByType",method = RequestMethod.GET )
    public ModelAndView booksByType(@RequestParam("bookType")String bookType){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/booksBybookType.html");
        modelAndView.addObject("typeName",bookType);
        modelAndView.addObject("books",bookService.findBooksByType(bookType));
        return modelAndView;
    }
    //后台：显示所有书的类型
    @RequestMapping(value = "/showBookType")
    public ModelAndView showBookType(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showBookType.html");
        modelAndView.addObject("bookTypes",bookService.findAllBookTypeAndBookSize());
        return modelAndView;
    }
    //后台：显示所有的书籍
    @RequestMapping(value = "/BackShowBooks")
    public ModelAndView showbooks(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showBooks.html");
        modelAndView.addObject("books",bookService.findAllBook());
        return modelAndView;
    }
    //后台：修改书籍
    @RequestMapping(value = "/updateBook")
    public String updateBook(){
        return "/Background/updateBook.html";
    }
    //后台：修改书籍信息
    @RequestMapping(value = "updateBookShow",method = RequestMethod.GET )
    public ModelAndView updatingBook(@RequestParam("bookName")String bookName){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateBook.html");
        modelAndView.addObject("book",bookService.findBookByBookName(bookName));
        modelAndView.addObject("bookTypes",bookService.findAllBookType());
        return modelAndView;
    }
    //后台：修改书籍信息的按书名搜索
    @RequestMapping(value = "/updateBookByBookName")
    public String updateBookByBookName(@RequestParam("bookName")String bookName,RedirectAttributes attr){
        attr.addAttribute("bookName",bookName);
        return "redirect:updateBookShow";
    }
    //后台：修改书籍提交
    @RequestMapping(value = "updatingBook")
    public String updatingBook(@RequestParam("bookName")String bookName,@RequestParam("price")float price,@RequestParam("inventory")int inventory,
                               @RequestParam("authorName") String authorName, @RequestParam("typeName")String typeName,
                               @RequestParam("introduction")String introduction,@RequestParam("imgAddress") MultipartFile[] imgAddress){
        System.out.println("修改书"+typeName);
        System.out.println("修改书"+imgAddress.length+"kkkkk");
        bookService.updatingBook(bookName,price,inventory,authorName,typeName,introduction,imgAddress);
        return "/errorOrsuccess/BackgroundSuccess.html";
    }

}
