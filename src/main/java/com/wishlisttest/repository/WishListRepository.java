package com.wishlisttest.repository;

import com.wishlisttest.model.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends MongoRepository<WishList, Integer> {

    //Method for fetching the wishlist of a particular user and order it by created_date
    List<WishList> findAllByUserId(Integer id);

}
