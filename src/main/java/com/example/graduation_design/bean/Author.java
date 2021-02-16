package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;
    private String authorName;
    private String aCountry;
    private String aAge;//出生年月日
    private String introduction;        //作者的简介

    @OneToMany(fetch = FetchType.LAZY,targetEntity = Book.class,mappedBy = "author")
    private Set<Book> books=new HashSet<>();
    //CascadeType.PERSIST  级联保存
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ImageAddress.class,mappedBy = "author",cascade = CascadeType.PERSIST)

    private Set<ImageAddress> imageAddresses=new HashSet<>();
    public Author() {
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getaCountry() {
        return aCountry;
    }

    public void setaCountry(String aCountry) {
        this.aCountry = aCountry;
    }

    public String getaAge() {
        return aAge;
    }

    public void setaAge(String aAge) {
        this.aAge = aAge;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<ImageAddress> getImageAddresses() {
        return imageAddresses;
    }

    public void setImageAddresses(Set<ImageAddress> imageAddresses) {
        this.imageAddresses = imageAddresses;
    }
}
