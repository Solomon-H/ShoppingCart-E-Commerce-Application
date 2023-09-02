package com.techelevator.dao;

import com.techelevator.model.CartItem;
import java.util.List;

public interface CartItemDao {
    CartItem getById(int cartItemId, int userId);

    CartItem getByProductAndUser(int productId, int userId);
    List<CartItem> getByUserId(int userId);
    int insert(CartItem item);
    void update(CartItem item);
    void delete(int cartItemId, int userId);
    void clearItemsByUserId(int userId);
}
