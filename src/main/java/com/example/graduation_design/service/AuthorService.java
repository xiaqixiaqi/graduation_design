package com.example.graduation_design.service;

import com.example.graduation_design.bean.Author;
import com.example.graduation_design.bean.ImageAddress;
import com.example.graduation_design.repository.AuthorRepository;
import com.example.graduation_design.repository.ImageAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {
    @Resource
    private AuthorRepository authorRepository;
    @Resource
    private ImageAddressRepository imageAddressRepository;
    @Resource
    private PictureDeal pictureDeal;
    private  Author author;
    //添加作者的照片
    public Set<ImageAddress> addAuthorImge(Author author,MultipartFile[] imgAddress){
        Set<ImageAddress> imageAddressSet=new HashSet<>();
        for(int i=0;i<imgAddress.length-1;i++) {
            String address=pictureDeal.upload(imgAddress[i],"authorPicture");
            if ((address!="")&&!(address.equals(""))) {
                ImageAddress imageAddress=new ImageAddress();
                imageAddress.setpAddress(address);
                imageAddress.setIsValuable(1);
                imageAddress.setAuthor(author);
                //保存作者的照片
                imageAddressRepository.save(imageAddress);
                imageAddressSet.add(imageAddress);
            }
        }
        return imageAddressSet;
    }
    //添加作者
    public boolean addingAuthor(String authorName,String aCountry,String aAge,
                                String introduction,  MultipartFile[] imgAddress){
        author=new Author();
        author.setAuthorName(authorName);
        author.setaAge(aAge);
        author.setaCountry(aCountry);
        author.setIntroduction(introduction);
        author.setImageAddresses(addAuthorImge(author,imgAddress));
        authorRepository.save(author);
            return true;
        }
        //按照作者名找出作者
    public Author findAuthorByAuthorName(String authorName){
        return  authorRepository.findAuthorByAuthorName(authorName);
    }
    //更新作者的信息
    public Boolean updatingAuthor(String authorName,String aCountry,String aAge,String introduction,MultipartFile[] imgAddress){
        author=authorRepository.findAuthorByAuthorName(authorName);
        if (imgAddress.length>1){
            for (ImageAddress img:author.getImageAddresses()) {
                img.setIsValuable(0);
            }
            author.setImageAddresses(addAuthorImge(author,imgAddress));
        }
        author.setaAge(aAge);
        author.setaCountry(aCountry);
        author.setAuthorName(authorName);
        author.setIntroduction(introduction);
        authorRepository.save(author);
        return true;
    }
    //返回所有的作者

    public List<Author> findAllAuthor(){
        return (List<Author>) authorRepository.findAll();
    }

}

