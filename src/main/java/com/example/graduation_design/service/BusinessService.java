package com.example.graduation_design.service;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.Business;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.repository.BookRepository;
import com.example.graduation_design.repository.BusinessRepository;
import com.example.graduation_design.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BusinessService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private BusinessRepository businessRepository;
    @Resource
    private PictureDeal pictureDeal;
    @Resource
    private BookRepository bookRepository;
    public boolean addBusinessing(String storeName, String address, String addressDetail, String introduction, MultipartFile[] logo, int  userId){
        User user=userRepository.findById(userId).get();
        String logoAdress="";
        if (logo.length>0){
            logoAdress = pictureDeal.upload(logo[0], "logoPicture");
        }
        //获取当前的时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createDate=formatter.format(date);
        Business business=new Business(storeName, address, addressDetail, createDate, user, introduction, logoAdress);
        business.setIsValidation(0);//待审核
        businessRepository.save(business);
        user.setBusiness(business);
        userRepository.save(user);
        return true;
    }
    public List<Business> findBusinessByIsValidation(int isValidation){
        return businessRepository.findBusinessesByIsValidationEquals(isValidation);
    }
    //通过bId，返回business
    public Business findBusinessByBId(int bId){
        return businessRepository.findById(bId).get();
    }
    public boolean updateBusinessIsValidationByBId(int bId,int isValidation){
        businessRepository.updateIsValidationByBId(bId,isValidation);
        return true;
    }
    //修改introduction
    public boolean updateIntroductionByBId(int bId,String introduction){
        businessRepository.updateIntroductionByBId(bId,introduction);
        return true;
    }
    //修改introduction和logo
    public boolean updateIntroductionAndLogoByBId(int bId,String introduction,MultipartFile[] imgAddress){
        String logo=pictureDeal.upload(imgAddress[0], "logoPicture");
        businessRepository.updateIntroductionAndLogoByBId(bId,introduction,logo);
        return true;
    }
    //按照销售量排序，返回最热销商品
    public List<Book> findHotBookByBId(int bId){
        return bookRepository.findBooksByBusinessBIdOrderBySalesDesc(bId,1);//有效的热销书籍
    }
    //返回所有有效店铺
    public List<Business> findAllBusinessByIsValuable(int isValuable){
       return businessRepository.findBusinessesByIsValidationEquals(isValuable);
    }
    //按店铺名和有效值，查询该店铺
    public Business findBusinessByBusinessAndIsValuable(String businessName,int isValuable){
        return businessRepository.findBusinessByStoreNameAndIsValidation(businessName,isValuable);
    }
    //用店铺名模糊查询店铺
    public List<Business> findBusinessByStoreNameLike(String storeName){
        return businessRepository.findAllByStoreNameLike(storeName);
    }

}
