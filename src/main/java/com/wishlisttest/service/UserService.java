package com.wishlisttest.service;

import com.wishlisttest.model.User;
import com.wishlisttest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }
}
