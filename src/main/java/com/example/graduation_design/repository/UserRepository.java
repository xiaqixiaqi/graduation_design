package com.example.graduation_design.repository;


import com.example.graduation_design.bean.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select s.password from User s where s.username=?1")
     String findPasswordByUsername(String username);
    User findUserByUsername(String username);
    List<User> findUsersByRole(int role);
}
