package com.example.graduation_design.service;

import com.example.graduation_design.bean.*;
import com.example.graduation_design.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //添加商品到购物车：先判断购物车是否已经有了，没有要重新添加，有了只需修改数量
    public boolean addShoppingCartItem(int bookNumber,int bookId,int userId){
        ShoppingCartItem shoppingCartItem;

       // System.out.println("shoppingCartItem:"+shoppingCartItem==null);
        if (shoppingCartItemRepository.findShoppingCartItemByBookIdAndShoppingCartItemId(bookId,userId,1).size()<1){
        //添加用户的购物车条目
        shoppingCartItem=new ShoppingCartItem();
        shoppingCartItem.setBook(bookRepository.findById(bookId).get());
        //获取当前的时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        shoppingCartItem.setsCartTime(formatter.format(date));
        shoppingCartItem.setBookNumber(bookNumber);
        shoppingCartItem.setIsValidation(1);
        User user=userRepository.findById(userId).get();
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
        return true; }
        else {
            shoppingCartItem=shoppingCartItemRepository.findShoppingCartItemByBookIdAndShoppingCartItemId(bookId,userId,1).get(0);
            System.out.println("修改书籍的id:"+shoppingCartItem.getShoppingCartItemId());
            int updateBookNumber=shoppingCartItem.getBookNumber()+bookNumber;
            System.out.println("修改书籍的数量："+updateBookNumber);
            updateShoppingCartItemNumber(shoppingCartItem.getShoppingCartItemId(),updateBookNumber);
            return true;
        }
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

    //前台：增加商品的数量
    public boolean updateShoppingCartItemNumber(int shoppingCartItemId,int number){
        shoppingCartItemRepository.updateShoppingCartItemNumber(number,shoppingCartItemId);
        return true;
    }
    //前台：删除一个购物车的商品条目
    public boolean updateShoppingCartItem(int isValidation,int shoppingCartItemId){
        shoppingCartItemRepository.updateShoppingCartItemIsValidation(isValidation,shoppingCartItemId);
        return true;
    }
    //前台：返回用户所选择的商品集合
    public List<ShoppingCartItem> findAllSelectShoppingCartItem(int[] itemIds){
        List<ShoppingCartItem> shoppingCartItems=new ArrayList<>();
        ShoppingCartItem shoppingCartItem;
        for (int itemId:itemIds) {
            shoppingCartItem= (ShoppingCartItem) shoppingCartItemRepository.findById(itemId).get();
            shoppingCartItems.add(shoppingCartItem);
        }
        return shoppingCartItems;
    }
    //增加购物车条目，用于前台直接购买
    public ShoppingCartItem addShoppingCartItemByUserIdAndNumber(int bookId,int number){
        ShoppingCartItem shoppingCartItem=new ShoppingCartItem();
        shoppingCartItem.setBook(bookRepository.findById(bookId).get());
        shoppingCartItem.setBookNumber(number);
        shoppingCartItem.setIsValidation(1);//设置有效值为1
        return shoppingCartItemRepository.save(shoppingCartItem);

    }


}
