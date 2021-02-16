package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shoppingOrder")
public class ShoppingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
   // private int userId;
    //一个用户有多个订单
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    private String oTime;
    private float totalPrice;
    //一个订单有多个订单条目
    @OneToMany(fetch = FetchType.LAZY,targetEntity = OrderItem.class,mappedBy = "shoppingOrder")
    private Set<OrderItem> orderItems=new HashSet<>();

    public ShoppingOrder() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
