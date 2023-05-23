package com.wishlisttest.controller;

import com.wishlisttest.model.Product;
import com.wishlisttest.model.User;
import com.wishlisttest.model.UserWishList;
import com.wishlisttest.model.WishList;
import com.wishlisttest.service.ProductService;
import com.wishlisttest.service.SequenceGeneratorService;
import com.wishlisttest.service.UserService;
import com.wishlisttest.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private ProductService productService;

    @GetMapping(path = "/getwishlist")
    public ResponseEntity<List<Long>> getWishList(@RequestParam("id") Integer id) {

        List<WishList> userWishList = wishListService.getWishList(id);
        List<Long> productList = new ArrayList<>();

        for (WishList wishList : userWishList) {
            productList.add(wishList.getIdProduct());
        }

        return new ResponseEntity<List<Long>>(productList, HttpStatus.OK);
    }

    @GetMapping(path = "/getuserwishlist")
    public ResponseEntity<UserWishList> getUserWishList(@RequestParam("id") Integer id) {

        try {

            List<WishList> body = wishListService.getWishList(id);

            UserWishList userWishList = new UserWishList();

            if (!body.isEmpty()) {

                Optional<User> idUser = userService.findById(body.get(0).getUserId());
                List<Optional<Product>> productList = new ArrayList<>();

                userWishList.setUser(idUser);

                for (WishList wishList : body) {
                    Optional<Product> product = getProduct(wishList);
                    productList.add(product);
                }
                userWishList.setProductList(productList);

            }

            return new ResponseEntity<UserWishList>(userWishList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ApiResponse> addWishList(@RequestBody Long product_id, @RequestParam("name") String name) {

        try {

            Integer idUser = getIdUser(name);
            List<WishList> userWishList = wishListService.getWishList(idUser);

            if (userWishList.size() < 20) {

                boolean exists = false;

                for (WishList wishList : userWishList) {
                    if (Objects.equals(wishList.getIdProduct(), product_id)) {
                        exists = true;
                    }
                }

                if (!exists) {
                    Long idproduct = productService.findById(product_id).get().getId();
                    WishList wishList = new WishList(idUser, idproduct);
                    wishList.setId(sequenceGeneratorService.generateSequence(WishList.SEQUENCE_NAME));
                    wishListService.createWishList(wishList);
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto adcionado na wishlist do usuário"), HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto ja existe na wishlist do usuário"), HttpStatus.OK);
                }

            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Não é possível adcionar mais que 20 produtos na wishList"), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/remove")
    public ResponseEntity<ApiResponse> deleteWishListProduct(@RequestParam("name") String name, @RequestParam("productName") String productName) {

        try {

            Integer idUser = getIdUser(name);

            if (idUser != null) {

                List<WishList> userWishList = wishListService.getWishList(idUser);
                long idWishList = 0;

                for (WishList wishList : userWishList) {
                    Optional<Product> product = getProduct(wishList);
                    if (Objects.equals(product.get().getName(), productName)) {
                        idWishList = wishList.getId();
                    }
                }

                if (idWishList != 0) {
                    wishListService.deleteWishList((int) idWishList);
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto apagado da wishlist do usuário."), HttpStatus.OK);
                } else {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto não existe na wishlist do usuário."), HttpStatus.OK);
                }

            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @GetMapping(path = "checkwishlistproduct")
    public ResponseEntity<ApiResponse> checkWishListProduct(@RequestParam("name") String name, @RequestParam("productName") String productName) {

        try {

            Integer idUser = getIdUser(name);

            if (idUser != null) {

                List<WishList> userWishList = wishListService.getWishList(idUser);
                boolean exists = false;

                for (WishList wishList : userWishList) {

                    Optional<Product> product = getProduct(wishList);
                    if (Objects.equals(product.get().getName(), productName)) {
                        exists = true;
                    }
                }
                if (exists) {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto existe na wishlist do usuário."), HttpStatus.OK);
                } else {
                    return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Produto não existe na wishlist do usuário."), HttpStatus.OK);
                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    private Integer getIdUser(String name) {
        Integer idUser = userService.getUserByName(name).getId();
        return idUser;
    }

    private Optional<Product> getProduct(WishList wishList) {
        Optional<Product> product = productService.findById(wishList.getIdProduct());
        return product;
    }

}
