package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String username;    //登录用户名
    private String realname;    //真实名字
    private String password;    //登录密码
    private String account;     //昵称
    private int sex;       //性别,1：男 0：女
    private String createTime;      //注册时间
    //private int userTypeId;     //用户类型id
    private String address;     //家庭地址

    private String userLove;       //用户喜爱
    private int role;   //用户权限，1：老板 2：管理员 3:商家 4:客户/卖家 5:无效管理员   等级越低，权限越大
//    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
//    @JoinTable(name = "tb_user_role",joinColumns = {@JoinColumn(name = "user_id")},
//    inverseJoinColumns = {@JoinColumn(name = "role_id")})
//    private List<Role> roles;
    private String avatar; //一个用户有一个头像
    private String phone;    //联系方式
    private String age; //出生年月日
    //每个用户可能有多条评论
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Evaluation.class,mappedBy ="user" )
    private List<Evaluation> evaluations;
    //一个用户有一个购物车
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartId",referencedColumnName = "cartId")
    private ShoppingCart shoppingCart;
    //一个用户有多个订单
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ShoppingOrder.class,mappedBy = "user")
    private Set<ShoppingOrder> shoppingOrders=new HashSet<>();
//    //一个用户为一种身份
//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = UserType.class)
//    @JoinColumn(name = "userTypeId",referencedColumnName = "userTypeId")
    //private UserType userType;
    //一个用户可以上传个录播图
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Slide.class,mappedBy = "publisher")
    private Set<Slide> slides;
    //一个用户有多个收货地址
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ReceiptsAddress.class,mappedBy = "user",cascade = CascadeType.PERSIST)
    private List<ReceiptsAddress> receiptsAddresses;
    @OneToOne
    @JoinColumn(name = "bId")
    private Business business;



    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserLove() {
        return userLove;
    }

    public void setUserLove(String userLove) {
        this.userLove = userLove;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Set<ShoppingOrder> getShoppingOrders() {
        return shoppingOrders;
    }

    public void setShoppingOrders(Set<ShoppingOrder> shoppingOrders) {
        this.shoppingOrders = shoppingOrders;
    }

//    public UserType getUserType() {
//        return userType;
//    }
//
//    public void setUserType(UserType userType) {
//        this.userType = userType;
//    }

    public Set<Slide> getSlides() {
        return slides;
    }

    public void setSlides(Set<Slide> slides) {
        this.slides = slides;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//
//    public int getRole() {
//        return role;
//    }
//
//    public void setRole(int role) {
//        this.role = role;
//    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<ReceiptsAddress> getReceiptsAddresses() {
        return receiptsAddresses;
    }

    public void setReceiptsAddresses(List<ReceiptsAddress> receiptsAddresses) {
        this.receiptsAddresses = receiptsAddresses;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

//    public List<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<Role> roles) {
//        this.roles = roles;
//    }


    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}
