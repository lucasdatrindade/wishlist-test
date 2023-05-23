package com.wishlisttest.service;

import com.wishlisttest.model.Product;
import com.wishlisttest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Optional<Product> findById(Long idProduct) {
        return productRepository.findById(idProduct);
    }

}
