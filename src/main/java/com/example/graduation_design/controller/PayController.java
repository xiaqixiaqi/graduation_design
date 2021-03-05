package com.example.graduation_design.controller;

import com.example.graduation_design.bean.User;
import com.example.graduation_design.service.AlipayService;
import com.example.graduation_design.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@RestController
public class PayController {
    @Resource
    private OrderService orderService;
    @Autowired
    @Qualifier("alipayService")
    private AlipayService alipayService;
//@RequestParam("receiptsAddress")int receiptsAddress,@RequestParam("itemId")int[] itemId
    @RequestMapping("/pay")
    //public String payController(@RequestParam("WIDout_trade_no") String id,@RequestParam("WIDsubject") String name,@RequestParam("WIDtotal_amount") float amount ) throws Exception {
    public String payController(@RequestParam("receiptsAddress")int receiptsAddress, @RequestParam("itemId")int[] itemIds,
                                @RequestParam("message") String message, @RequestParam("totalPrice")float totalPrice, HttpSession session) throws Exception {
        // Integer pay=Integer.valueOf(amount);
        //生成订单号
        Random random = new Random();
        SimpleDateFormat allTime = new SimpleDateFormat("YYYYMMddHHmmSSS");
        String subjectno = allTime.format(new Date())+random.nextInt(10);
        String orderNumber=subjectno+random.nextInt(10);
        User user= (User) session.getAttribute("USER");
        int orderId=orderService.addOrder(orderNumber,receiptsAddress,itemIds,message,user);
        String pays = alipayService.webPagePay(orderNumber, totalPrice, Integer.toString(orderId));
        return pays;
    }


}