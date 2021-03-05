package com.example.graduation_design.repository;

import com.example.graduation_design.bean.OrderItem;
import com.example.graduation_design.bean.ShoppingOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem,Integer> {
    @Modifying
    @Transactional
    @Query("update OrderItem s set s.isPayment=?1 where s.orderItemId=?2")
    int updateIsPaymentByorderItemId(int isPayment,int orderItemId);
    @Query("select  s from OrderItem s  where s.shoppingOrder.user.userId=?1 and s.isPayment=?2")
    List<OrderItem> findOrderItemByUserAndIsPayment(int userId, int isPayment);
    List<OrderItem> findOrderItemByIsPaymentEquals(int isPayment);
    @Query("select o from OrderItem o where o.business.bId=?1 and o.isPayment=?2")
    List<OrderItem> findAllByBusinessBIdAndIsPayment(int bId,int isPayment);
    @Query(value = "select o from OrderItem o where o.isPayment=?1 and o.business.bId=?2 ")
    List<OrderItem> findOrderItemByIsPaymentAndBusinessBId(int isPayment,int bId);
}
