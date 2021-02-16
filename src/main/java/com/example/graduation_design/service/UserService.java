package com.example.graduation_design.service;

import com.example.graduation_design.bean.ShoppingCart;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }
    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

    public boolean logining(String username,String password) {
        System.out.println(username+password);
        String realPassword=userRepository.findPasswordByUsername(username);
        System.out.println(realPassword);
        if (realPassword==null||!realPassword.equals(password))
            return false;
        else
            return true;
    }
    //添加管理员
    public boolean addingAdministrator(String realname,String aAge,String phone,String username){
        User user=new User();
        user.setRealname(realname);
        user.setAge(aAge);
        user.setPhone(phone);
        user.setUsername(username);
        //添加管理员的权限
        user.setRole(2);
        //添加时间
        //添加日期
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(formatter.format(date));
        //设置初始密码
        user.setPassword("888888");
        userRepository.save(user);
        return true;
    }
    //通过用户名寻找用户
    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    //通过role查找所有的用户
    public List<User> findAllUserByRole(int role){
        return userRepository.findUsersByRole(role);
    }

}
