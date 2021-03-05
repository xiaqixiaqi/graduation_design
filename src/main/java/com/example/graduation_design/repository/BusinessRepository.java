package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.ReceiptsAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusinessRepository extends CrudRepository<Business,Integer> {
    List<Business> findBusinessesByIsValidationEquals(int isValidation);
    @Modifying
    @Transactional
    @Query("update Business s set s.isValidation=?2 where s.bId=?1")
    int updateIsValidationByBId(int bId,int isValidation);
    @Modifying
    @Transactional
    @Query("update Business s set s.introduction=?2 where s.bId=?1")
    int updateIntroductionByBId(int bId,String introduction);
    @Modifying
    @Transactional
    @Query("update Business s set s.introduction=?2 , s.logo=?3 where s.bId=?1")
    int updateIntroductionAndLogoByBId(int bId,String introduction,String logo);
//    @Query("select b from Business b where b.isValidation=?1")
//    List<Business>
    @Query("select b from Business b where b.storeName=?1 and b.isValidation=?2")
    Business findBusinessByStoreNameAndIsValidation(String storeName,int isValidation);
    @Query("select b from Business b where b.storeName like %?1%")
    List<Business> findAllByStoreNameLike(String storeName);

}
