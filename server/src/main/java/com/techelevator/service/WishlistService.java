package com.techelevator.service;

import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.dao.WishlistDao;
import com.techelevator.model.*;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class WishlistService {
    private WishlistDao wishlistDao;
    private ProductDao productDao;
    private UserDao userDao;

    public WishlistService(WishlistDao wishlistDao, ProductDao productDao, UserDao userDao) {
        this.wishlistDao = wishlistDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    private int getUserId(Principal principal) {
        return userDao.findByUsername(principal.getName()).getId();
    }

    public List<Wishlist> getWishlists(Principal principal) {
        int userId = getUserId(principal);
        return wishlistDao.getWishlists(userId);
    }

    public Wishlist getWishlist(Principal principal, int id) {
        return getWishlist(getUserId(principal), id);
    }

    public Wishlist getWishlist(int userId, int id) {
        Wishlist list = wishlistDao.getWishlist(userId, id);

        if (list == null) {
            return null;
        }
        return addProductDetails(list);
    }

    public Wishlist createWishlist(Principal principal, Wishlist wishlist) {
        int userId = getUserId(principal);
        wishlist.setUserId(userId);
        Wishlist newList = wishlistDao.createWishlist(wishlist);
        return addProductDetails(newList);
    }

    public WishlistItem addItem(Principal principal, int wishlistId, int productId) {
        int userId = getUserId(principal);
        // is wishlistId valid and owned by user?
        Wishlist list = wishlistDao.getWishlist(userId, wishlistId);
        if (list == null) {
            return null;
        }
        // is itemId a valid product?
        if (!isProductIdValid(productId)) {
            return null;
        }

        // Check if the product id is already in the list. If not, add the item.
        WishlistItem existingWishlistItem = wishlistDao.getWishlistItemByProduct(wishlistId, productId);
        if (existingWishlistItem != null) {
            return existingWishlistItem;
        }
        WishlistItem newItem = new WishlistItem();
        newItem.setWishlistId(wishlistId);
        newItem.setProductId(productId);
        newItem.setWishlistItemId(wishlistDao.addItem(newItem));
        return newItem;
    }

    public void removeItem(Principal principal, int wishlistId, int productId) {
        int userId = getUserId(principal);
        wishlistDao.removeItem(userId, wishlistId, productId);
    }

    public void delete(Principal principal, int wishlistId) {
        int userId = getUserId(principal);
        wishlistDao.deleteWishlist(userId, wishlistId);
    }

    private boolean isProductIdValid(int productId) {
        Product product = productDao.getById(productId);
        return product != null;
    }

    private Wishlist addProductDetails(Wishlist list) {
        // Get the list of Products
        List<Product> products = productDao.getProductsInWishlist(list.getWishlistId());

        // Loop through items and add the product details to each
        for (WishlistItem item : list.getItems()) {
            item.setProduct(findProduct(products, item.getProductId()));
        }
        return list;
    }

    private Product findProduct(List<Product> products, int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }
}
