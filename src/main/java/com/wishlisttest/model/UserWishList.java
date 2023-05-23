package com.wishlisttest.model;

import java.util.List;
import java.util.Optional;

public class UserWishList {
    private Optional<User> user;
    private List<Optional<Product>> productList;

    public UserWishList() {
    }

    public UserWishList(Optional<User> user, List<Optional<Product>> productList) {
        this.user = user;
        this.productList = productList;
    }

    public Optional<User> getUser() {
        return user;
    }

    public List<Optional<Product>> getProductList() {
        return productList;
    }

    public void setUser(Optional<User> user) {
        this.user = user;
    }

    public void setProductList(List<Optional<Product>> productList) {
        this.productList = productList;
    }
}
