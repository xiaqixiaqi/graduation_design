package com.example.graduation_design.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class PictureDeal {
    //下载图片
    protected String upload(MultipartFile fileUpload,String basepath){
        //获取文件名
        String fileName = fileUpload.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+fileName;
        //指定本地文件夹存储图片D:\JetBrains\file\graduation_design\src\main\resources\static\bookPicture
        String filePath = "D:\\JetBrains\\file\\graduation_design\\src\\main\\resources\\static\\"+basepath+"\\";
        try {
            //将图片保存到static文件夹里
            System.out.println("bbbbbb");
            fileUpload.transferTo(new File(filePath+fileName));
            return "/"+basepath+"/"+fileName;
        } catch (Exception e) {
            System.out.println("kkkkkkkkkkkk");
            e.printStackTrace();
            return "";
        }
    }
}
