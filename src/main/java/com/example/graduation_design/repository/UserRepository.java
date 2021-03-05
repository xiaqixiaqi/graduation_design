package com.example.graduation_design.repository;


import com.example.graduation_design.bean.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select s.password from User s where s.username=?1")
     String findPasswordByUsername(String username);
    User findUserByUsername(String username);
    List<User> findUsersByRole(int role);
    @Modifying
    @Transactional
    @Query("update User s set s.password=?2 where s.userId=?1")
    int updatePasswordByUserId(int userId,String password);
    @Modifying
    @Transactional
    @Query("update User s set s.role=?2 where s.userId=?1")
    int updateRoleByUserId(int userId,int role);
}
