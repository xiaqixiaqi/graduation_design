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
    //People是关系的维护端，当删除 people，会级联删除 address
    @JoinColumn(name = "bookId", referencedColumnName = "bookId")
    //people中的address_id字段参考address表中的id字段
    private Book book;
    private int oINumber;
    private int isPayment;
    //一个订单有多个订单条目
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = ShoppingOrder.class)
    @JoinColumn(name = "orderId",referencedColumnName = "orderId")
    private ShoppingOrder shoppingOrder;

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

    public int getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(int isPayment) {
        this.isPayment = isPayment;
    }

    public ShoppingOrder getShoppingOrder() {
        return shoppingOrder;
    }

    public void setShoppingOrder(ShoppingOrder shoppingOrder) {
        this.shoppingOrder = shoppingOrder;
    }
}
