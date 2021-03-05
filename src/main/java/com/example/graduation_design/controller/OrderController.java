package com.example.graduation_design.controller;

import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.OrderItem;
import com.example.graduation_design.bean.ShoppingOrder;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.AlipayService;
import com.example.graduation_design.service.BookService;
import com.example.graduation_design.service.OrderService;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Resource
    private OrderService orderService;
    @Resource
    private BookService bookService;
    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;

    @RequestMapping(value = "/user/addOrdering")
    public String addOrdering(HttpServletRequest request, HttpServletRequest response,RedirectAttributes attributes){
        System.out.println("支付成功, 进入同步通知接口...");
        Map<String,String[]> requestParams = request.getParameterMap();
        String orderNumber="";
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            if (name.equals( "out_trade_no")) {
                orderNumber=requestParams.get(name)[0];
            }
        }
       // ShoppingOrder shoppingOrder=orderService.findShoppingOrderByOrderNumber(orderNumber);
        //System.out.println("is:"+shoppingOrder.getIsPayment());
        //if (shoppingOrder!=null){
        ShoppingOrder shoppingOrder=orderService.findShoppingOrderByOrderNumber(orderNumber);//修改订单项是否支付了，1为已支付
        for (OrderItem o :shoppingOrder.getOrderItems()) {
            //o.setIsPayment(1);
            System.out.println("是否进入这里"+o.getOrderItemId());
            orderService.updateIsPaymentByOrderItemId(1,o.getOrderItemId());
        }
        orderService.updateIsPaymentByOrderNumber(1,orderNumber);//修改订单为已支付
        orderService.updateOTimeByOrderNumber(orderNumber);
        //对商品的销售量修改
        bookService.updateAllBookSalesByOrderNumber(orderNumber);
        attributes.addAttribute("isPayment",1);

        //}
        return "redirect:/user/order";
    }
    //前台：订单
    @RequestMapping(value = "/user/order",method = RequestMethod.GET)
    public ModelAndView order(@RequestParam("isPayment") int isPayment, HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("Front/order.html");
        modelAndView.addObject("orders",orderService.findShoppingOrderByUserIdAndIsPayment(user.getUserId(),isPayment));
        modelAndView.addObject("isPayment",isPayment);
       // modelAndView.addObject("orderItems",orderService.findorderItemByUserIdAndIsPayment(user.getUserId(),isPayment));
        modelAndView.addObject("isPayment",isPayment);
        return modelAndView;
    }
    //前台：待收货
    @RequestMapping(value = "/user/received",method = RequestMethod.GET)
    public ModelAndView received(HttpSession session){
        User user= (User) session.getAttribute("USER");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Front/orderReceive.html");
        modelAndView.addObject("orderItems",orderService.findOrderItemByUserIdAndIsPayment(user.getUserId(),2));
        return modelAndView;
    }

    //前台：未支付订单的提交
    @ResponseBody
    @RequestMapping(value = "/user/updateOrder",method = RequestMethod.GET)
    public String updateOrder(@RequestParam("orderNumber")String orderNumber,@RequestParam("totalPrice")String totalPrice,
                              @RequestParam("orderId")String orderId)throws Exception {
        System.out.println(orderNumber);
        System.out.println(totalPrice);
        System.out.println();
       // orderNumber=orderNumber;
        System.out.println(orderNumber);
        String pays = alipayService.webPagePay(orderNumber, Float.valueOf(totalPrice), orderId);
        //attributes.addAttribute("isPayment",1);
        return pays;
    }
    //后台：显示新的订单
    @RequestMapping(value = "/business/showNewOrder")
        public ModelAndView showNewOrder(HttpSession session){
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showNewOrder.html");
        //modelAndView.addObject("allNewOrder",orderService.findShoppingOrderByIsPayment(1));
        modelAndView.addObject("allNewOrder",orderService.findAllNewOrderByBId(business.getbId()));
        return modelAndView;
    }
    //后台：订单详情
    @RequestMapping(value = "/business/showOrderDetail",method = RequestMethod.GET)
    public ModelAndView showOrderDetail(@RequestParam("orderNumber")String orderNumber){
        System.out.println("订单号："+orderNumber);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showNewOrderDetail.html");

        modelAndView.addObject("order",orderService.findShoppingOrderByOrderNumber(orderNumber));
        return modelAndView;
    }
    //后台：新订单的处理
    @RequestMapping(value = "/business/orderDeal",method = RequestMethod.GET)
    public String orderDeal(@RequestParam("orderNumber")String orderNumber,@RequestParam("isStoke")int isStock,RedirectAttributes attributes){
        if (isStock==0){
            //缺货处理
            orderService.updateIsPaymentByOrderItemId(4,orderService.findShoppingOrderByOrderNumber(orderNumber).getOrderId());
        //orderService.updateIsPaymentByOrderNumber(4,orderNumber);
        }
        else{
            //待发货
            orderService.updateIsPaymentByOrderItemId(2,orderService.findShoppingOrderByOrderNumber(orderNumber).getOrderId());
            //orderService.updateIsPaymentByOrderNumber(2,orderNumber);
        }
        attributes.addAttribute("message","该订单已处理完毕");
        return "redirect:/backgroundSuccess";
    }
    //后台:订单修改页面
    @RequestMapping(value = "/business/updateOrderInBack")
    public String updateOrderInBack(){
        return "/Background/updateOrder.html";
    }
    //后台：通过订单编号搜索订单，返回要修改的订单
    @RequestMapping(value = "/business/searchOrderByOrderNumber",method = RequestMethod.GET)
    public ModelAndView searchOrderByOrderNumber(@RequestParam("orderNumber")String orderNumber){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/updateOrder.html");
        modelAndView.addObject("order",orderService.findShoppingOrderByOrderNumber(orderNumber));
        return modelAndView;
    }
    //修改订单收货地址
    @RequestMapping(value = "/business/backUpdateOrderAddress",method = RequestMethod.POST)
    public String backUpdateOrderAddress(@RequestParam("orderId")int orderId,@RequestParam("address")String address,@RequestParam("addressDetail")String addressDetail,
                                         @RequestParam("zipCode")String zipCode,@RequestParam("consignee")String consignee,@RequestParam("phone")String phone,RedirectAttributes attributes){
        orderService.manageUpdateOrderAddress(orderId,address,addressDetail,zipCode,consignee,phone);
        attributes.addAttribute("message","该订单收货地址已修改完毕");
        return "redirect:/backgroundSuccess";
    }
    //后台：每月订单销售分析
    @RequestMapping(value = "/business/showMonthSale")
    public ModelAndView showMonthSale(HttpSession session) throws ParseException {
        Business business= (Business) session.getAttribute("BUSINESS");
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/Background/showSaleByMonth.html");
        modelAndView.addObject("data",orderService.findSaleByMonth(1,business.getbId(),2021));
//        modelAndView.addObject("data","[{day:\"Monday\", sales: 3},{day: \"Tuesday\", sales: 2},{day: \"Wednesday\", sales: 3}," +
//                "{day: \"Thursday\", sales: 4},{day: \"Friday\", sales: 6},{day: \"Saturday\", sales: 11},{day: \"Sunday\", sales: 4}");
        return modelAndView;
    }
    //前台：确认收货
    @RequestMapping(value = "/user/updateOrderItem",method = RequestMethod.GET)
    public String updateOrderItem(@RequestParam("orderItemId") int orderItemId){
        orderService.updateOrderItem(orderItemId);
        return "/errorOrsuccess/FrontSuccess.html";
    }


}
