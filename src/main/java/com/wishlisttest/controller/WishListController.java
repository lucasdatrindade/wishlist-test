package com.wishlisttest.controller;

import com.wishlisttest.model.WishList;
import com.wishlisttest.repository.ProductRepository;
import com.wishlisttest.service.SequenceGeneratorService;
import com.wishlisttest.service.UserService;
import com.wishlisttest.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private UserService userService;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(path = "/getwishlist")
    public ResponseEntity<List<Long>> getWishList(@RequestParam("id") Integer id) {

        List<WishList> body = wishListService.getWishList(id);
        List<Long> productList = new ArrayList<>();

        for (WishList wishList : body) {
            productList.add(wishList.getIdProduct());
        }

        return new ResponseEntity<List<Long>>(productList, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody Long product_id, @RequestParam("name") String name) {

        Integer idUser = userService.getUserByName(name).getId();
        Long idproduct = productRepository.findById(product_id).get().getId();
        WishList wishList = new WishList(idUser, idproduct);
        wishList.setId(sequenceGeneratorService.generateSequence(WishList.SEQUENCE_NAME));

        wishListService.createWishList(wishList);

        //return ResponseEntity.noContent().build();
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED);
    }

}
