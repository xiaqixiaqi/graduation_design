package com.example.graduation_design.bean;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;
   // private int bookLabelId;        //标签id
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = BookType.class)
    @JoinColumn(name = "bookTypeId",referencedColumnName = "bookTypeId")
    private BookType bookType;
    //private int bookTypeId;         //类型id
   // private int authorId;           //作者id
    private String bookName;        //书名
    private String introduction;        //对书的描述
    private float price;            //单价
    private int inventory;          //库存
    //评价
    private int poorReviews;        //差评度
    private int praise;             //好评度
    private int sales;              //销售量
    private int isValuable;         //记录该书是否还有效，即是否还存在,1表示有效，0表示无效
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Author.class)
    @JoinColumn(name = "authorId",referencedColumnName = "authorId")
    private Author author;
    //一本书有多个标签
    @ManyToMany(targetEntity = Label.class)
    @JoinTable(name = "sys_Book_Label",
    joinColumns = {@JoinColumn(name = "book_Label",referencedColumnName = "bookId")},
    inverseJoinColumns = {@JoinColumn(name = "label_Book",referencedColumnName = "labelId")})
    private Set<Label> labels=new HashSet<>();
    //一本书有多个评论
    @OneToMany(fetch = FetchType.LAZY,targetEntity = Evaluation.class,mappedBy = "book")
    private Set<Evaluation> evaluations=new HashSet<>();

    //一本书有多张照片
    @OneToMany(fetch = FetchType.LAZY,targetEntity = ImageAddress.class,mappedBy = "book",cascade = CascadeType.PERSIST)
    private Set<ImageAddress> bookImages =new HashSet<>();
    public Book() {
    }

    public Book(String bookName, String introduction, float price, int inventory) {
        this.bookName = bookName;
        this.introduction = introduction;
        this.price = price;
        this.inventory = inventory;
    }

    public int getBookId() {
        return bookId;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getPoorReviews() {
        return poorReviews;
    }

    public void setPoorReviews(int poorReviews) {
        this.poorReviews = poorReviews;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Set<ImageAddress> getBookImages() {
        return bookImages;
    }

    public void setBookImages(Set<ImageAddress> bookImages) {
        this.bookImages = bookImages;
    }

    public int getIsValuable() {
        return isValuable;
    }

    public void setIsValuable(int isValuable) {
        this.isValuable = isValuable;
    }
}
