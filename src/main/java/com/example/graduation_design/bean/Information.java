package com.example.graduation_design.bean;

import javax.persistence.*;

@Entity
@Table(name = "information")
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int infoId;
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = User.class)
    @JoinColumn(name = "userId",referencedColumnName = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Business.class)
    @JoinColumn(name = "bId",referencedColumnName = "bId")
    private Business business;

    public int getInfoId() {
        return infoId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }
}
