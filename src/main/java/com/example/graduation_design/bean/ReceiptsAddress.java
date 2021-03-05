package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.Set;

/**
 * 收获地址
 *
 */
@Entity
@Table(name = "receiptsAddress")
public class ReceiptsAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int raId;//id
    private  String address;//收货地址
    private String addressDetail;//地址详情
    private String consignee;//收货人
    private String phone;//收货人电话
    private String zipCode;//邮政编码
    private int isDefault;//是否为默认地址,是为1，或者为0
    private int isValidation;//该收货地址是否有效 1为有效，0为无效，2：为后台管理员添加的地址
    //一个用户有多个地址
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    //一个订单有一个收获地址,一个收获地址被多个订单使用
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ShoppingOrder.class,mappedBy = "receiptsAddress")
    private Set<ShoppingOrder> shoppingOrders;

    public ReceiptsAddress() {
    }

    public ReceiptsAddress(String address, String addressDetail, String consignee, String phone, String zipCode, int isDefault, User user) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.consignee = consignee;
        this.phone = phone;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
        this.user = user;
    }

    public int getRaId() {
        return raId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public User getUser() {
        return user;
    }

    public Set<ShoppingOrder> getShoppingOrders() {
        return shoppingOrders;
    }

    public void setShoppingOrders(Set<ShoppingOrder> shoppingOrders) {
        this.shoppingOrders = shoppingOrders;
    }

    public int getIsValidation() {
        return isValidation;
    }

    public void setIsValidation(int isValidation) {
        this.isValidation = isValidation;
    }
}
