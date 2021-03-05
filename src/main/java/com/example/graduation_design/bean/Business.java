package com.example.graduation_design.bean;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.List;

/**
 * 商家页面
 */
@Entity
@Table(name = "business")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bId;//商家id
    private String storeName;//店铺名
    private String address;//商家地址
    private String detailAddress;//详细地址
    private String createDate;//创建时间
    private int isValidation;//是否有效，0:表示还在审核 1：审核通过 2:审核不通过
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;//一个店铺一个商家
    private String introduction;//店铺的介绍
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Book.class,mappedBy = "business",cascade = CascadeType.PERSIST)
    private List<Book> books;//一个店铺有多个商品
    private String logo;//一个店铺有一个logo的照片地址
    @OneToMany(fetch = FetchType.LAZY,targetEntity = OrderItem.class,mappedBy = "business",cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;//一个店铺有多个订单
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Information.class,mappedBy = "business",cascade = CascadeType.PERSIST)
    private List<Information> informationList;

    public Business() {
    }

    public Business(String storeName, String address, String detailAddress, String createDate, User user, String introduction, String logo) {
        this.storeName = storeName;
        this.address = address;
        this.detailAddress = detailAddress;
        this.createDate = createDate;
        this.user = user;
        this.introduction = introduction;
        this.logo = logo;
    }

    public int getbId() {
        return bId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

//    public List<ShoppingOrder> getShoppingOrders() {
//        return shoppingOrders;
//    }
//
//    public void setShoppingOrders(List<ShoppingOrder> shoppingOrders) {
//        this.shoppingOrders = shoppingOrders;
//    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Information> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<Information> informationList) {
        this.informationList = informationList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getIsValidation() {
        return isValidation;
    }

    public void setIsValidation(int isValidation) {
        this.isValidation = isValidation;
    }
}
