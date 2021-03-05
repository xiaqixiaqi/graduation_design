package com.example.graduation_design.repository;

import com.example.graduation_design.bean.ReceiptsAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReceiptsAddressRepository extends CrudRepository<ReceiptsAddress,Integer> {
    @Query("select r from ReceiptsAddress r  where r.user.userId=?1 and r.isValidation =?2 ORDER BY isDefault desc ")
    List<ReceiptsAddress> findAllOrderByIsDefaultDesc(int userId,int isValidation);
    @Modifying
    @Transactional
    @Query("update ReceiptsAddress s set s.isValidation=?2 where s.raId=?1")
    int updateIsValidationByRaId(int raId,int isValidation);
}
