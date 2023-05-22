package com.wishlisttest.service;

import com.wishlisttest.model.WishList;
import com.wishlisttest.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> getWishList(Integer user_id) {
        return wishListRepository.findAllByUserId(user_id);
    }

}
