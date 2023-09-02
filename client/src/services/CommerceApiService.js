import axios from "axios";

export default {
    getAllProducts(){
        return axios.get("/products")
    },

    addToCart(cartItem){
        return axios.post("/cart/items", cartItem);
    },

    searchProduct(searchTerm){
        return axios.get(`/products?name=${searchTerm}`);
    },

    getProductById(id){
        return axios.get(`/products/${id}`);
    },

    getCart(){
        return axios.get('/cart');
    },

    removeCartItem(itemId){
        return axios.delete(`/cart/items/${itemId}`);
    },

    clearCart(){
        return axios.delete('/cart');
    }
}