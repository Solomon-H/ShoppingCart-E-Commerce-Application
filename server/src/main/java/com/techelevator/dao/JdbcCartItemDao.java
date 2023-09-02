package com.techelevator.dao;

import com.techelevator.model.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemDao implements CartItemDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public CartItem getById(int cartItemId, int userId) {
        String sql = "SELECT * FROM cart_item WHERE cart_item_id = ? AND user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, cartItemId, userId);
        if (results.next()) {
            CartItem item = mapRowToCartItem(results);
            return item;
        }
        return null;
    }

    @Override
    public CartItem getByProductAndUser(int productId, int userId) {
        String sql = "SELECT * FROM cart_item WHERE product_id = ? AND user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId, userId);
        if (results.next()) {
            CartItem item = mapRowToCartItem(results);
            return item;
        }
        return null;
    }

    @Override
    public List<CartItem> getByUserId(int userId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM cart_item WHERE user_id = ? ORDER BY cart_item_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            CartItem item = mapRowToCartItem(results);
            list.add(item);
        }
        return list;
    }

    @Override
    public int insert(CartItem item) {
        String sql = "INSERT INTO cart_item(user_id, product_id, quantity) VALUES (?, ?, ?) RETURNING cart_item_id";
        int newId = jdbcTemplate.queryForObject(sql, int.class, item.getUserId(), item.getProductId(), item.getQuantity());
        return newId;

    }

    @Override
    public void update(CartItem item) {
        // The only thing that can be updated is Quantity
        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_item_id = ?";
        jdbcTemplate.update(sql, item.getQuantity(), item.getCartItemId());
    }

    @Override
    public void delete(int cartItemId, int userId) {
        // Make sure the item noted is actually the user's
        String sql = "DELETE FROM cart_item WHERE cart_item_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, cartItemId, userId);
    }

    @Override
    public void clearItemsByUserId(int userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    private CartItem mapRowToCartItem(SqlRowSet rs) {
        CartItem item = new CartItem();
        item.setCartItemId(rs.getInt("cart_item_id"));
        item.setUserId(rs.getInt("user_id"));
        item.setProductId(rs.getInt("product_id"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }


}
