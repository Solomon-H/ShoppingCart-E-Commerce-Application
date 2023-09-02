package com.techelevator.controller;

import com.techelevator.model.Wishlist;
import com.techelevator.model.WishlistItem;
import com.techelevator.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/wishlists")

@PreAuthorize("isAuthenticated()")
public class WishlistController {

    private WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // Get a list of wishlists
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Wishlist> getWishlists(Principal principal) {
        return wishlistService.getWishlists(principal);
    }

    // Get a single wishlist
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Wishlist getWishlist(@PathVariable int id, Principal principal) {
        Wishlist wishlist = wishlistService.getWishlist(principal, id);
        if (wishlist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return wishlist;
    }

    // Create a new wishlist
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Wishlist createWishlist(@Valid @RequestBody Wishlist wishlist, Principal principal) {
        return wishlistService.createWishlist(principal, wishlist);
    }

    // Delete a wishlist
    @RequestMapping(path = "{wishlistId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int wishlistId, Principal principal) {
        wishlistService.delete(principal, wishlistId);
    }

    // Add a product to a wishlist
    @RequestMapping(path = "{wishlistId}/products/{productId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@PathVariable int wishlistId, @PathVariable int productId, Principal principal) {
        WishlistItem newWishlistItem = wishlistService.addItem(principal, wishlistId, productId);
        if (newWishlistItem == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more values were invalid.");
        }
    }

    // Remove a product from a wishlist
    @RequestMapping(path = "{wishlistId}/products/{productId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable int wishlistId, @PathVariable int productId, Principal principal) {
        wishlistService.removeItem(principal, wishlistId, productId);
    }
}
