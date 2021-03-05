package com.example.graduation_design.bean;


import javax.persistence.*;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int evaluationId;
    private String eContent;        //评价内容
    //private int bookId;             //所属书的id
    //private int userId;             //用户id
    private String eTime;           //时间
    private int praise;//1为好评，0为差评
    //一个评论只能是一个用户，一个用户可以有多条评论
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    //一个评论只能是一个本书的，一本书可以有多条评论
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Book.class)
    @JoinColumn(name = "bookId",referencedColumnName = "bookId")
    private Book book;
    //一个评论只能是一个销售订单的，一个销售订单为一个一个评论
    @OneToOne
    @JoinColumn(name = "orderItemId")
    private OrderItem orderItem;


    public Evaluation() {
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public String geteContent() {
        return eContent;
    }

    public void seteContent(String eContent) {
        this.eContent = eContent;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }
}
