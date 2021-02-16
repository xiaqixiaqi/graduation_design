package com.example.graduation_design.repository;

import com.example.graduation_design.bean.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
}
