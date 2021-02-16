package com.example.graduation_design.service;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.ShoppingCart;
import com.example.graduation_design.bean.ShoppingCartItem;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.repository.BookRepository;
import com.example.graduation_design.repository.ShoppingCartItemRepository;
import com.example.graduation_design.repository.ShoppingCartRepository;
import com.example.graduation_design.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShoppingCartService {
    @Resource
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Resource
    private BookRepository bookRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private ShoppingCartRepository shoppingCartRepository;
    //添加商品到购物车
    public boolean addShoppingCartItem(int bookNumber,int bookId){
        //添加用户的购物车条目
        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
        shoppingCartItem.setBook(bookRepository.findById(bookId).get());
        //获取当前的时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        shoppingCartItem.setsCartTime(formatter.format(date));
        shoppingCartItem.setBookNumber(bookNumber);
        User user=userRepository.findById(1).get();
        ShoppingCart shoppingCart;
        //判断该用户是否有购物车
        if (user.getShoppingCart()!=null){
            shoppingCart=user.getShoppingCart();
        }else {
            //创建新的购物车
            shoppingCart=new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCartRepository.save(shoppingCart);
            //为用户添加购物车
            user.setShoppingCart(shoppingCart);
            userRepository.save(user);
        }
        //保存到购物车条目
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItemRepository.save(shoppingCartItem);
        return true;
    }
    //用用户的id查找用户，返回用户的购物车
    public ShoppingCart findUserShoppingCart(int userId){
        ShoppingCart shoppingCart=userRepository.findById(userId).get().getShoppingCart();
        if (shoppingCart!=null){
            return shoppingCart;
        }else{
            return  null;
        }

    }
}
