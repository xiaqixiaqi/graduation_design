package com.example.graduation_design.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResultController {
    @RequestMapping(value = "/frontSuccess",method = RequestMethod.GET)
    public ModelAndView frontSuccess(@RequestParam("message")String message){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/errorOrsuccess/FrontSuccess.html");
        modelAndView.addObject("message",message);
        return modelAndView;
    }
    @RequestMapping(value = "/backgroundSuccess",method = RequestMethod.GET)
    public ModelAndView backgroundSuccess(@RequestParam("message")String message){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/errorOrsuccess/BackgroundSuccess.html");
        modelAndView.addObject("message",message);
        return modelAndView;
    }
}
