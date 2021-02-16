package com.example.graduation_design.repository;

import com.example.graduation_design.bean.ShoppingCartItem;

import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartItemRepository extends CrudRepository<ShoppingCartItem,Integer> {
}
