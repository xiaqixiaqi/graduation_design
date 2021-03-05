package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shoppingOrder")
public class ShoppingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String orderNumber;//订单编号
   // private int userId;
    //一个用户有多个订单
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    private String oTime;//付款的时间
    private float totalPrice;//总价
    private float shipping;//运费
    private int isPayment;//是否付款，0：未付款,1：已付款，
    private String createDate;//创建时间
    //给卖家留言
    private String message;
    //一个订单有多个订单条目
    @OneToMany(fetch = FetchType.LAZY,targetEntity = OrderItem.class,mappedBy = "shoppingOrder",cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;
    //一个订单有一个收获地址,一个收获地址被多个订单使用
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = ReceiptsAddress.class)
    @JoinColumn(name = "raId",referencedColumnName = "raId")
    private ReceiptsAddress receiptsAddress;
    //一个订单项为一个店铺所有

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Business.class)
//    @JoinColumn(name = "bId",referencedColumnName = "bId")
//    private Business business;

    public ShoppingOrder() {
    }

    public int getOrderId() {
        return orderId;
    }

    public String getoTime() {
        return oTime;
    }

    public void setoTime(String oTime) {
        this.oTime = oTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getShipping() {
        return shipping;
    }

    public void setShipping(float shipping) {
        this.shipping = shipping;
    }

    public ReceiptsAddress getReceiptsAddress() {
        return receiptsAddress;
    }

    public void setReceiptsAddress(ReceiptsAddress receiptsAddress) {
        this.receiptsAddress = receiptsAddress;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
//
//    public Business getBusiness() {
//        return business;
//    }
//
//    public void setBusiness(Business business) {
//        this.business = business;
//    }

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }
}
