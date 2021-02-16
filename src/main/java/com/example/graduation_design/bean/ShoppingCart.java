package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    //private int userId;
    //一个购物车有多个购物车条目
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ShoppingCartItem.class,mappedBy = "shoppingCart")
    private Set<ShoppingCartItem> shoppingCartItems=new HashSet<>();
    @OneToOne(mappedBy = "shoppingCart")
    private User user;

    public ShoppingCart() {
    }

    public int getCartId() {
        return cartId;
    }

    public Set<ShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(Set<ShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
