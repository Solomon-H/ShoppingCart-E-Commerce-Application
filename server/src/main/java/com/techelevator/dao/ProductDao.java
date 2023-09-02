package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAll();

    Product getById(int productId);

    List<Product> getProductsInUserCart(int userId);

    List<Product> getProductsInWishlist(int wishlistId);

    List<Product> find(String productSku, String name);
}
