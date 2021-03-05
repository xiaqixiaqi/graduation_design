package com.example.graduation_design.controller;

import com.example.graduation_design.service.SlideService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
public class SlideController {

    @Resource
    private SlideService slideService;
    @RequestMapping(value = "/business/addSlide")
    public ModelAndView addSlide(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/addSlide.html");//转发到addSlide.html
        modelAndView.addObject("slideImg",slideService.findAllSlideImg());//封装数据，用于前端页面，这里返回的是list
        return modelAndView;
    }
    @RequestMapping(value = "/business/updateslide")
    public String updateSlide(@RequestParam("slideTheme")String slideTheme,@RequestParam("content")String content,
                              @RequestParam("url")String url,@RequestParam("updateIm") String updateIm,
                              @RequestParam("imgAddress") MultipartFile[] imgAddress){
     //   System.out.println("kkkkk:"+updateIm);
        if (slideService.addSlide(slideTheme,content,url,updateIm,imgAddress)==1){
            return "/errorOrsuccess/BackgroundSuccess.html";
        }else {
            return "/errorOrsuccess/BackgroundError.html";
        }
    }
    @RequestMapping(value = "/business/showAllSlide")
    public ModelAndView showAllSlide(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAllSlide.html");
        modelAndView.addObject("slides",slideService.findAllSlide());
        return modelAndView;
    }
    @RequestMapping(value = "rr404")
    public String error404(){
        return "/errorOrsuccess/404.html";
    }
}
