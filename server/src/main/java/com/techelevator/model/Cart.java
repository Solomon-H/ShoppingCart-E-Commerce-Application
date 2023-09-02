package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private BigDecimal tax;
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
        this.tax = new BigDecimal("0.00");
    }

    public Cart(List<CartItem> items) {
        this();
        this.items = items;
    }

    public CartItem[] getItems() {
        CartItem result[] = new CartItem[items.size()];
        return items.toArray(result);
    }


    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    /*
     * Note: The following two getter methods create derived properties. A derived
     *    is not stored in a variable, it is always calculated from other properties in the
     *    object. For example, the subtotal is derived (or obtained) from totaling the price
     *    from each of the cart item products.
     */
    public BigDecimal getItemSubtotal() {
        BigDecimal subtotal = new BigDecimal("0.00");
        subtotal.setScale(2);

        for (CartItem item : items) {
            subtotal = subtotal.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return subtotal;
    }

    public BigDecimal getTotal() {
        return getItemSubtotal().add(tax);
    }

}
