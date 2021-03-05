package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.ShoppingCartItem;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem,Integer> {
    @Modifying
    @Transactional
    @Query("update ShoppingCartItem s set s.bookNumber=?1 where s.shoppingCartItemId=?2")
    int updateShoppingCartItemNumber(int bookNumber,int shoppingCartItemId);
    @Modifying
    @Transactional
    @Query("update ShoppingCartItem s set s.isValidation=?1 where s.shoppingCartItemId=?2")
    int updateShoppingCartItemIsValidation(int isValidation,int shoppingCartItemId);
    @Query("select s from ShoppingCartItem s where s.book.bookId=?1 and s.shoppingCart.user.userId=?2 and s.isValidation=?3")
    List<ShoppingCartItem> findShoppingCartItemByBookIdAndShoppingCartItemId(int bookId,int userId,int isValidation);
}
