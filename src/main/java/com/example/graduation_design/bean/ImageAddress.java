package com.example.graduation_design.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "imageAddress")
public class ImageAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;
    private String pAddress;
    private int isValuable;    //记录该照片是否还有效，0表示无效，1表示有效
    //一本书有多张照片
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Book.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookId",referencedColumnName = "bookId")
    private Book book;
    //一个店铺有一张logo
    //一个作者有多个照片
    //CascadeType.PERSIST  级联保存
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Author.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "authorId",referencedColumnName = "authorId")
    private Author author;
    //一个轮播图允许有一个宣传图z
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "imageAddress")
//    private Slide slide;

    public int getPid() {
        return pid;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getIsValuable() {
        return isValuable;
    }

    public void setIsValuable(int isValuable) {
        this.isValuable = isValuable;
    }
    //    public Slide getSlide() {
//        return slide;
//    }
//
//    public void setSlide(Slide slide) {
//        this.slide = slide;
//    }
}
