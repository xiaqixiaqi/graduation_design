package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "bookType")
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookTypeId;
    private String bTypeName;       //类型名
    //private Set<Label> bookLabels;      //属于该类型的标签
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Book.class,mappedBy = "bookType")
    private Set<Book> books=new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Label.class,mappedBy = "bookType")
    private Set<Label> labels=new HashSet<>();
    public BookType() {
    }

    public BookType(String bTypeName) {
        this.bTypeName = bTypeName;
    }

    public int getBookTypeId() {
        return bookTypeId;
    }

    public String getbTypeName() {
        return bTypeName;
    }

    public void setbTypeName(String bTypeName) {
        this.bTypeName = bTypeName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }
}
