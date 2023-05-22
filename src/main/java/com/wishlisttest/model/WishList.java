package com.wishlisttest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "wishlist")
public class WishList {

    @Transient
    public static final String SEQUENCE_NAME = "wishlist_sequence";
    @Id
    private long id;
    //private User user;
    private Integer userId;
    //private Product product;
    private Long idProduct;
    private Date dateCreated;


    public WishList() {

    }

    public WishList(Integer userId, Long idProduct) {
        this.userId = userId;
        this.idProduct = idProduct;
        this.dateCreated = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
