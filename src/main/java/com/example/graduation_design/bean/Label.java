package com.example.graduation_design.bean;

import org.aspectj.weaver.NameMangler;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int labelId;
   // private int bookTypeID;     //所属的类型
    private String labelName;       //标签名
    @ManyToMany(targetEntity = Book.class)
    @JoinTable(name = "sys_Label_Book",
    joinColumns = {@JoinColumn(name = "label_book",referencedColumnName = "labelId")},
    inverseJoinColumns = {@JoinColumn(name = "book_Label",referencedColumnName = "bookId")})
    private Set<Book> books=new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = BookType.class)
    @JoinColumn(name = "bookTypeId",referencedColumnName = "bookTypeId")
    private BookType bookType;

    public Label() {
    }

    public int getLabelId() {
        return labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }
}
