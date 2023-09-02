<template>
  <div class="card">
      <h1 class="name">{{ product.name }}</h1>
      <p class="iconC" v-show="loggedIn">
        <cart-icon class="fac" v-bind:cartItem="cartItem" :productName="productName"/>
        Add to Cart
      </p>
      <h2 class="details">{{ product.description }}</h2>
      <h3 class="sku"> {{ product.productSku }} </h3>
      <h3 class="price">{{ price }}</h3>
      <div class="image">
        <img src="https://via.placeholder.com/700x200.jpg">
      </div>
  </div>
</template>

<script>
import CartIcon from "../components/CartIcon.vue";
import CommerceApiService from "../services/CommerceApiService.js";
export default {
    name: "ProductDetailsView",
    components: {
        CartIcon
    },
    data(){
        return{
            product: {},
            cartItem: {
                productId: this.$route.params.productId,
                quantity: 1
            }
        }       
    },
    computed: {
        productName(){
            return this.product.name;
        },
        loggedIn(){
            return this.$store.state.token.length > 0;
        },
        price(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
        }).format(this.product.price);
      }
    },
    created(){
        CommerceApiService
        .getProductById(this.$route.params.productId)
        .then(response => {
            if (response.status == 200){
                this.product = response.data
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
    }
}
</script>

<style scoped>
.card{
    display: grid;
    width: 300px;
    height: 300px;
    margin: 16px;
    padding: 8px;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 1fr 1fr 1fr 6fr 1fr;
    grid-template-areas: 
    "name ."
    "details ."
    "sku price"
    "image image"
    "iconC iconC";
    bottom: 30px;
  }
.name{
    grid-area: name;
}
.iconC{
    grid-area: iconC;
    display: flex;
    justify-content: flex-start;
    align-items: center;
}
.iconC > .fac{
    margin-right: 5px;
} 
.details{
    grid-area: details;
} 
.sku{
    grid-area: sku;
}
.price{
    grid-area: price;
}
.image{
    grid-area: image;
    display: flex;
    justify-content: center;
    align-items: center;
}
.image > img {
    width: 100%;
    height: auto;
    border-radius: 15px;
}
</style>