<template>
    <button v-on:click.stop="add" v-show="loggedIn">
        <font-awesome-icon class="i"
        icon="fa-solid fa-cart-plus"
        title="Add to Cart"
        />
    </button>
</template>

<script>
import CommerceApiService from "../services/CommerceApiService.js";
export default {
    name: "CartIcon",
    props: {
        cartItem: {
            type: Object
        },
        productName: {
            type: String
        }
    },
     methods: {
    add(){
      CommerceApiService
      .addToCart(this.cartItem)
      .then(response => {
        if (response.status == 200){
          this.$store.commit("SET_BANNER",`Added ${this.productName} to cart.`);
        }
      })
      .catch((error) => {
          const response = error.response;
          if (!response) {
            alert(error);
          } else {
            alert(response.message);
          }
        });
      },
    },
    computed: {
      loggedIn(){
        return this.$store.state.token.length > 0;
      }
    }
}
</script>

<style scoped>
.i{
    pointer-events: auto;
}
</style>