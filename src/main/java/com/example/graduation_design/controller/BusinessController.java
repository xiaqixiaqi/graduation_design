package com.example.graduation_design.controller;

import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.BusinessService;
import com.example.graduation_design.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class BusinessController {
    @Resource
    private BusinessService businessService;
    @Resource
    private UserService userService;
    @RequestMapping(value = "/user/addBusiness")
    public ModelAndView addBusiness(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/addBusiness.html");
        modelAndView.addObject("user",user);
        return modelAndView;
    }
    @RequestMapping(value = "/user/addBusinessing")
    public String addBusinessing(@RequestParam("storeName")String storeName, @RequestParam("address")String address, @RequestParam("addressDetail")String addressDetail,
                                 @RequestParam("introduction")String introduction, @RequestParam("logo") MultipartFile[] logo, HttpSession session, RedirectAttributes attributes){
        User user= (User) session.getAttribute("USER");
        attributes.addAttribute("message","提交成功，请等待管理员审核");
        businessService.addBusinessing(storeName,address,addressDetail,introduction,logo,user.getUserId());
        return "redirect:/frontSuccess";
    }
    @RequestMapping(value = "/business/showAllNewBusiness")
    public ModelAndView showAllNewBusiness(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAllNewBusiness.html");
        modelAndView.addObject("Businesses",businessService.findBusinessByIsValidation(0));
        return modelAndView;
    }
    @RequestMapping(value = "/business/showNewBusiness",method = RequestMethod.GET)
    public ModelAndView showNewBusiness(@RequestParam("bId")int bId){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showNewBusiness.html");
        modelAndView.addObject("business",businessService.findBusinessByBId(bId));
        return modelAndView;
    }
    @RequestMapping(value = "/business/addingBusiness",method = RequestMethod.GET)
    public String addingBusiness(@RequestParam("bId")int bId,@RequestParam("isPass")int isPass,HttpSession session,RedirectAttributes attributes){
        User user= (User) session.getAttribute("USER");
        attributes.addAttribute("message","审核完毕");
        if (isPass==1){
            //审核通过
            businessService.updateBusinessIsValidationByBId(bId,1);
            //修改管理员权限
            userService.updateUserRoleByUserId(user.getUserId(),3);
        }else{
            //审核不通过
            businessService.updateBusinessIsValidationByBId(bId,2);
        }

        return "redirect:/backgroundSuccess";
    }
    @RequestMapping(value = "/business/updateBusiness",method = RequestMethod.GET)
    public ModelAndView updateBusiness(@RequestParam("bId")int bId, HttpSession session){
        User user= (User) session.getAttribute("USER");
        user=userService.findUserByUserId(user.getUserId());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateBusiness.html");
        modelAndView.addObject("business",businessService.findBusinessByBId(user.getBusiness().getbId()));
        return modelAndView;
    }
    @RequestMapping(value = "/business/updatingBusiness")
    public String updatingBusiness(@RequestParam("bId")int bId,@RequestParam("introduction")String introduction,
                                   @RequestParam("imgAddress") MultipartFile[] imgAddress, HttpSession session,RedirectAttributes attributes){
        if (imgAddress.length<1){
            businessService.updateIntroductionAndLogoByBId(bId,introduction,imgAddress);
        }else{
            businessService.updateIntroductionByBId(bId,introduction);
        }
        attributes.addAttribute("message","修改成功");
        return "redirect:/backgroundSuccess";
    }
    @RequestMapping(value = "/business/showAllBusiness")
    public ModelAndView showAllBusiness(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showAllNewBusiness.html");
        modelAndView.addObject("Businesses",businessService.findBusinessByIsValidation(1));
        return modelAndView;
    }
    //前台：返回商家详情页面
    @RequestMapping(value = "/user/showBusinessDetail",method = RequestMethod.GET)
    public ModelAndView showBusinessDetail(@RequestParam("bId")int bId){
        System.out.println("进入了这里");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/businessDetail.html");
        modelAndView.addObject("business",businessService.findBusinessByBId(bId));
        modelAndView.addObject("books",businessService.findHotBookByBId(bId));
        return modelAndView;
    }
    //前台：返回所有有效店铺
    @RequestMapping(value = "/user/allBusiness")
    public ModelAndView allBusiness(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/allBusiness.html");
        modelAndView.addObject("allBusiness",businessService.findBusinessByIsValidation(1));
        return modelAndView;
    }

    //更新店铺信息
    @RequestMapping(value = "/business/researchBusiness")
    public ModelAndView researchBusiness(@RequestParam("businessName")String businessName){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateBusiness.html");
        modelAndView.addObject("business",businessService.findBusinessByBusinessAndIsValuable(businessName,1));
        return modelAndView;
    }
    //更新店铺信息的页面
    @RequestMapping("/business/updateStore")
    public String updateStore(){
        return "/Background/updateBusiness.html";
    }

}
