package com.example.graduation_design.controller;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.BusinessService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.BatchUpdateException;

@Controller
public class BookController {
    @Resource
    private BookService bookService;
    @Resource
    private BusinessService businessService;
    //书籍详情
    @RequestMapping(value ="/user/bookDetail",method = RequestMethod.GET )
    public ModelAndView bookDetail(@RequestParam("bookId")int bookId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/bookDetail.html");
        Book book=bookService.findBookByBookId(bookId);
        System.out.println("书籍详情页面的数据:"+book.getBookId());
        modelAndView.addObject("book",book);
        return modelAndView;
    }
    //添加书籍
    @RequestMapping(value = "/business/addBook")
    public ModelAndView addBook(){

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/addBook.html");//转发到addBook.html
        modelAndView.addObject("bookTypes",bookService.findAllBookType());//封装数据，用于前端页面，这里返回的是list
        return modelAndView;
    }
    @RequestMapping(value = "/business/addingBook")
    public String addingBook(@RequestParam("bookName")String bookName,@RequestParam("price")float price,@RequestParam("inventory")int inventory,
                             @RequestParam("author")String author,@RequestParam("bookType")String bookType,
                             @RequestParam("introduction") String introduction,@RequestParam("imgAddress") MultipartFile[] imgAddress,HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        System.out.println(imgAddress);
        bookService.addBook(bookName,price,inventory, author,bookType,introduction,imgAddress,business.getbId());

        return "redirect:/business/addBook";
    }
    //添加商品类型
    @RequestMapping(value = "/business/addBookType")
    public String addBookType(){
        return "/Background/addBookType.html";
    }
    @RequestMapping(value = "addingBookType")
    public String addingBookType(@RequestParam("bTypeName")String bTypeName){
        bookService.addBookType(bTypeName);
        return "/Background/addBookType.html";
    }
    //按照书的类型显示该类型的所有书籍
   @RequestMapping(value = "/user/booksByType",method = RequestMethod.GET )
    public ModelAndView booksByType(@RequestParam("bookType")String bookType){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/booksBybookType.html");
        modelAndView.addObject("typeName",bookType);
        modelAndView.addObject("books",bookService.findBooksByType(bookType));
        modelAndView.addObject("bookTypes",bookService.findAllBookType());
        return modelAndView;
    }
    //后台：显示所有书的类型
    @RequestMapping(value = "/business/showBookType")
    public ModelAndView showBookType(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showBookType.html");
        modelAndView.addObject("bookTypes",bookService.findAllBookTypeAndBookSize());

        return modelAndView;
    }
    //后台：显示所有的书籍
    @RequestMapping(value = "/business/BackShowBooks")
    public ModelAndView showbooks(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showBooks.html");
        if (user.getRole()<=2){
            modelAndView.addObject("books",bookService.findAllBook());
            return modelAndView;
        }
        else {
            Business business= (Business) session.getAttribute("BUSINESS");
            business=businessService.findBusinessByBId(business.getbId());
            modelAndView.addObject("books",business.getBooks());
            return modelAndView;
        }
    }
    //后台：修改书籍
    @RequestMapping(value = "/business/updateBook")
    public String updateBook(){
        return "/Background/updateBook.html";
    }
    //后台：修改书籍信息
    @RequestMapping(value = "/business/updateBookShow",method = RequestMethod.GET )
    public ModelAndView updatingBook(@RequestParam("bookName")String bookName,HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateBook.html");
        Book book=bookService.findBookByBookNameAndBId(bookName,business.getbId());
        modelAndView.addObject("bookTypes",bookService.findAllBookType());
        if(book==null){
            modelAndView.addObject("book", null);
        }
        else {
            if (book.getIsValuable() == 1) {
                modelAndView.addObject("book", book);
            } else {
                modelAndView.addObject("book", null);

            }
        }
        return modelAndView;
    }
    //后台：修改书籍信息的按书名搜索
    @RequestMapping(value = "/business/updateBookByBookName")
    public String updateBookByBookName(@RequestParam("bookName")String bookName,RedirectAttributes attr){
        attr.addAttribute("bookName",bookName);
        return "redirect:updateBookShow";
    }
    //后台：修改书籍提交
    @RequestMapping(value = "/business/updatingBook")
    public String updatingBook(@RequestParam("bookName")String bookName,@RequestParam("price")float price,@RequestParam("inventory")int inventory,
                               @RequestParam("authorName") String authorName, @RequestParam("typeName")String typeName,
                               @RequestParam("introduction")String introduction,@RequestParam("imgAddress") MultipartFile[] imgAddress,RedirectAttributes attributes){
        bookService.updatingBook(bookName,price,inventory,authorName,typeName,introduction,imgAddress);
        attributes.addAttribute("message","修改成功");
        return "redirect:/business/backgroundSuccess";
    }
    //前台：“图书”
    @RequestMapping(value = "/user/allBooks")
    public ModelAndView allBookShow(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/allBook.html");
        modelAndView.addObject("allBook",bookService.findAllBook());
        modelAndView.addObject("allBookType",bookService.findAllBookType());
        return modelAndView;
    }
    //后台：书籍热销销售量
    @RequestMapping(value = "/business/hotBookBySale")
    public ModelAndView hotBookBySale(HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/hotBookBySale.html");
        modelAndView.addObject("data",bookService.findBookSaleByBId(business.getbId()));
        return modelAndView;
    }
    @RequestMapping(value = "/business/deleteBook",method = RequestMethod.GET)
    public String deleteBook(int bookId){
        bookService.updateBookIsValuation(bookId);
        return "redirect:/business/BackShowBooks";
    }
    @RequestMapping(value = "/business/showBookDetail",method = RequestMethod.GET)
    public ModelAndView showBookDetail(@RequestParam("bookId")int bookId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showBookDetail.html");
        modelAndView.addObject("book",bookService.findBookByBookId(bookId));
        return modelAndView;
    }


}
