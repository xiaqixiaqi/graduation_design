package com.example.graduation_design.bean;

import javax.persistence.*;

@Entity
@Table(name = "shoppingCartItem")
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shoppingCartItemId;
   // private int bookId;
    //一个购物车条目对应一种书
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bookId",referencedColumnName = "bookId")
    private Book book;
    private String sCartTime;
    private int bookNumber;
    private int isValidation;//是否有效的处理 1:有效 0：无效
    //一个购物车有多个购物车条目
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = ShoppingCart.class)
    @JoinColumn(name = "cartId",referencedColumnName ="cartId" )
    private ShoppingCart shoppingCart ;

    public ShoppingCartItem() {
    }

    public int getShoppingCartItemId() {
        return shoppingCartItemId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getsCartTime() {
        return sCartTime;
    }

    public void setsCartTime(String sCartTime) {
        this.sCartTime = sCartTime;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public int getIsValidation() {
        return isValidation;
    }

    public void setIsValidation(int isValidation) {
        this.isValidation = isValidation;
    }
}
