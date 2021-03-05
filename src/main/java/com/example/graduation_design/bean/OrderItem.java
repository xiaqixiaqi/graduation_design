package com.example.graduation_design.bean;

import javax.persistence.*;

@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;
    //private int bookId;

    //一个购书订单的一个条目只能是一种书
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    private Book book;
    private int oINumber;//购买数量
    private int isPayment;//是否付款，0：未付款,1：已付款，2.已发货（未收货），3.已收货，4：缺货 5：已评价
   // private int isStock;//是否有货，0：没货，1：有货
   // private int isPayment;
    //一个订单有多个订单条目
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = ShoppingOrder.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId",referencedColumnName = "orderId")
    private ShoppingOrder shoppingOrder;
    //一个订单项为多个用户所有
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Business.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bId",referencedColumnName = "bId")
    private Business business;
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "evaluationId", referencedColumnName = "evaluationId")
    private Evaluation evaluation;//一个店铺一个商家

    public OrderItem() {
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getoINumber() {
        return oINumber;
    }

    public void setoINumber(int oINumber) {
        this.oINumber = oINumber;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
//    public int getIsPayment() {
//        return isPayment;
//    }
//
//    public void setIsPayment(int isPayment) {
//        this.isPayment = isPayment;
//    }

    public ShoppingOrder getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(ShoppingOrder shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }
    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
    //    public int getIsStock() {
//        return isStock;
//    }
//
//    public void setIsStock(int isStock) {
//        this.isStock = isStock;
//    }
}
