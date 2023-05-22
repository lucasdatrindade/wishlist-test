package com.wishlisttest.repository;

import com.wishlisttest.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    User findByName(String name);

}
