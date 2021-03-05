package com.example.graduation_design.service;

import com.example.graduation_design.bean.ImageAddress;
import com.example.graduation_design.bean.Slide;
import com.example.graduation_design.repository.ImageAddressRepository;
import com.example.graduation_design.repository.SlideRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SlideService {
    @Resource
    private SlideRepository slideRepository;
    @Resource
    private PictureDeal pictureDeal;

    public List<String> findAllSlideImg(){
        return slideRepository.findAllImgAddress();
    }

    public int addSlide(String slideTheme,String content, String url, String updateIm, MultipartFile[] imgAddress) {
        String address=pictureDeal.upload(imgAddress[0],"slidePicture");
        System.out.println("更新的照片:"+address);
        if ((address!="")&&!(address.equals(""))) {
              return slideRepository.updateSlide(slideTheme,content,url,address,updateIm);
            }
        return 0;
        }
    public List<Slide> findAllSlide(){
        return (List<Slide>) slideRepository.findAll();
    }
}
