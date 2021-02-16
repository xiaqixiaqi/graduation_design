package com.example.graduation_design.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "slide")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int slideId;
    //轮播图主题
    private String slideTheme;
    //时间
    private String createDate;
    //内容
    private String content;
    //链接
    private String url;
    //宣传图
    private String imgAddress;
//    @OneToOne
//    @JoinColumn(name = "pid")
//    private ImageAddress imageAddress;
    //发布者
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User publisher;

    public int getSlideId() {
        return slideId;
    }

    public String getSlideTheme() {
        return slideTheme;
    }

    public void setSlideTheme(String slideTheme) {
        this.slideTheme = slideTheme;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public ImageAddress getImageAddress() {
//        return imageAddress;
//    }
//
//    public void setImageAddress(ImageAddress imageAddress) {
//        this.imageAddress = imageAddress;
//    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public String getImgAddress() {
        return imgAddress;
    }

    public void setImgAddress(String imgAddress) {
        this.imgAddress = imgAddress;
    }
}
