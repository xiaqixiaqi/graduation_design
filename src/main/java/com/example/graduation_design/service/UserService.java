package com.example.graduation_design.service;

import com.example.graduation_design.bean.ReceiptsAddress;
import com.example.graduation_design.bean.ShoppingCart;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.repository.ImageAddressRepository;
import com.example.graduation_design.repository.ReceiptsAddressRepository;
import com.example.graduation_design.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private ReceiptsAddressRepository receiptsAddressRepository;
    @Resource
    private PictureDeal pictureDeal;
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
        //user.setRoles();
        user.setRole(2);
        //添加时间
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
    //前台：添加地址
    public boolean addAddressing(String address,String addressDetail,String zipCode,String consignee,String phone,int isDefault,User user){
        ReceiptsAddress receiptsAddress=new ReceiptsAddress(address, addressDetail, consignee, phone, zipCode, isDefault,user);
        receiptsAddress.setIsValidation(1);//设置有效值为1
        receiptsAddressRepository.save(receiptsAddress);
        return true;
    }
    //前台：跟新地址
    public boolean updateAddressing(String address,String addressDetail,String zipCode,String consignee,String phone,int isDefault,int raId){
        ReceiptsAddress receiptsAddress=receiptsAddressRepository.findById(raId).get();
        receiptsAddress.setAddress(address);
        receiptsAddress.setAddressDetail(addressDetail);
        receiptsAddress.setZipCode(zipCode);
        receiptsAddress.setConsignee(consignee);
        receiptsAddress.setPhone(phone);
        receiptsAddress.setIsDefault(isDefault);
        receiptsAddressRepository.save(receiptsAddress);
       // ReceiptsAddress receiptsAddress=new ReceiptsAddress(address, addressDetail, consignee, phone, zipCode, isDefault,user);
       // receiptsAddress.setIsValidation(1);//设置有效值为1
        //receiptsAddressRepository.save(receiptsAddress);
        return true;
    }
    //前台：获取所有的地址，有效地址
    public List<ReceiptsAddress> getAllReceiptsAddressesByUser(int userId){
        return receiptsAddressRepository.findAllOrderByIsDefaultDesc(userId,1);
    }
    //更新用户的信息
    public boolean updatingUser(MultipartFile[] imgAddress, String account, String realname, int sex, String address,int userId){
        User user=userRepository.findById(userId).get();
        user.setAccount(account);
        user.setRealname(realname);
        user.setSex(sex);
        user.setAddress(address);
        if (imgAddress.length>1){
        String avatar = pictureDeal.upload(imgAddress[0], "userPicture");
            if ((avatar != "") && !(avatar.equals("")))
                user.setAvatar(avatar);
        }
        userRepository.save(user);
        return true;
    }
    //修改用户密码
    public boolean upateUserPassword(int userId,String password){
        userRepository.updatePasswordByUserId(userId,password);
        return true;
    }
    //删除收货地址
    public boolean deleteReceiptAddress(int raId,int isValidation){
        receiptsAddressRepository.updateIsValidationByRaId(raId,isValidation);
        return true;
    }
    //按照收货地址id,返回收货地址信息
    public ReceiptsAddress findReceiptsAddresByRaId(int raId){
        return receiptsAddressRepository.findById(raId).get();
    }
    //根据用户id，返回用户
    public User findUserByUserId(int userId){
        return userRepository.findById(userId).get();
    }
    //根据用户id，修改用户权限
    public boolean updateUserRoleByUserId(int userId,int role){
        userRepository.updateRoleByUserId(userId,role);
        return true;
    }
    public boolean cancelAdministrator(int userId){
        Random r = new Random();
        long l1 = r.nextLong();
        User user=userRepository.findById(userId).get();
        user.setUsername(Long.toString(l1));
        user.setPassword(Long.toString(l1));
        user.setRole(5);
        userRepository.save(user);
        return true;
    }
}
