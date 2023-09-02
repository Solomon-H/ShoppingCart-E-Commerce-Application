package com.techelevator.controller;

import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Get the user's cart
    @RequestMapping(path = "", method = RequestMethod.GET)
    public Cart get(Principal principal) {
        return cartService.getUserCart(principal);
    }

    // Add an item to the user's cart
    @RequestMapping(path = "/items", method = RequestMethod.POST)
    public CartItem addProduct(@Valid @RequestBody CartItem item, Principal principal) {
        return cartService.addToCart(principal, item);
    }

    // Remove an item from the user's cart
    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable int itemId, Principal principal) {
        cartService.removeFromCart(principal, itemId);
    }

    // Clear the user's cart
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear(Principal principal) {
        cartService.clearCart(principal);
    }
}
