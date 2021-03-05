package com.example.graduation_design.repository;

import com.example.graduation_design.bean.ShoppingOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface ShoppingOrderRepository extends CrudRepository<ShoppingOrder,Integer> {
    @Query("select  s from ShoppingOrder s  where s.orderNumber=?1 ")
    List<ShoppingOrder> findShoppingOrderByOrderNumber(String orderNumber);
    @Modifying
    @Transactional
    @Query("update ShoppingOrder s set s.isPayment=?1 where s.orderNumber=?2")
    int updateIsPaymentByOrderNumber(int isPayment,String orderNumber);
    @Modifying
    @Transactional
    @Query("update ShoppingOrder s set s.oTime=?1 where s.orderNumber=?2")
    int updateOTimeByOrderNumber(String oTime,String orderNumber);
    @Query("select  s from ShoppingOrder s  where s.user.userId=?1 and s.isPayment=?2")
    List<ShoppingOrder> findShoppingOrderByUserAndIsPayment(int userId,int isPayment);
    @Query("select  s from ShoppingOrder s  where s.isPayment=?1")
    List<ShoppingOrder> findShoppingOrderByIsPaymentEquals(int isPayment);

//    List<ShoppingOrder> findShoppingOrderByIsPaymentEquals(int isPayment);
//    @Query("select s from ShoppingOrder s where s.isPayment=?1 and s.business.bId=?2 and s.oTime like ?3")
//    List<ShoppingOrder> findShoppingOrderByIsPaymentEqualsAndBusiness(int isPayment,int bId,String year);
//    @Query(value = "select year(a_age),count(*) from author group by year(a_age)",nativeQuery = true)
//    List<Object> vvv();
//    @Query(value = "select month(o_time),count(*) from shopping_order where year(o_time)=?1 and is_payment=?2 and b_id=?3 group by month(o_time) ",nativeQuery = true)
//    List<Object> findmonthandcount(int year,int is_payment,int b_id);

}
