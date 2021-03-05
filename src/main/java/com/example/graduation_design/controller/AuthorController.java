package com.example.graduation_design.controller;

import com.example.graduation_design.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.xml.bind.ValidationEvent;

@Controller
public class AuthorController {
    @Resource
    private AuthorService authorService;
    //添加作者
    @RequestMapping(value = "/business/addAuthor")

    public String addAuthor(){
        return "/Background/addAuthor.html";
    }
    @RequestMapping(value = "/business/addingAuthor")
    public String addingAuthor(@RequestParam("authorName")String authorName,@RequestParam("aCountry")String aCountry,@RequestParam("aAge")String aAge,
                            @RequestParam("introduction")String introduction,@RequestParam("imgAddress") MultipartFile[] imgAddress){
        System.out.println(authorName+","+aCountry+","+aAge+","+introduction+","+imgAddress[0].getOriginalFilename());
        authorService.addingAuthor(authorName,aCountry,aAge,
                introduction,imgAddress);
        return "/Background/addAuthor.html";
    }
    //后台：修改作者信息（按照作者名搜索）
    @RequestMapping(value = "/business/updateAuthorShow",method = RequestMethod.GET)
    public ModelAndView updateAuthorByAuthorName(@RequestParam("authorName")String authorName){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateAuthor.html");
        modelAndView.addObject("author",authorService.findAuthorByAuthorName(authorName));
        return modelAndView;
    }
    //后台：修改作者信息（搜索返回）
    @RequestMapping(value = "/business/updateAuthorSearch")
    public String updateAuthorSearch(@RequestParam("authorName")String authorName, RedirectAttributes attributes){
        attributes.addAttribute("authorName",authorName);
        return "redirect:/business/updateAuthorShow";
    }
    //后台：返回修改作者页面
    @RequestMapping(value = "/business/updateAuthor")
    public String updateAuthor(){
        return "/Background/updateAuthor.html";
    }
    //后台：修改作者信息的提交
    @RequestMapping(value = "/business/updatingAuthor")
    public String updatingAuthor(@RequestParam("authorName")String authorName,@RequestParam("aCountry")String aCountry,
                                 @RequestParam("aAge")String aAge,@RequestParam("introduction")String introduction,
                                 @RequestParam("imgAddress")MultipartFile[] imgAddress){
        authorService.updatingAuthor(authorName,aCountry,aAge,introduction,imgAddress);
        return "/errorOrsuccess/BackgroundSuccess.html";

    }
    //后台:显示所有作者
    @RequestMapping(value = "/business/showAllAuthor")
    public ModelAndView showAllAuthor(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAuthors.html");
        modelAndView.addObject("authors",authorService.findAllAuthor());
        return modelAndView;
    }
    //后台：显示后台的作者详情
    @RequestMapping(value = "/business/showAuthorDetail",method = RequestMethod.GET)
    public ModelAndView showAuthorDetail(@RequestParam("authorId")int authorId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAuthorDetail.html");
        modelAndView.addObject("author",authorService.findAuthorByAuthorId(authorId));
        return modelAndView;
    }

}
