<template>
  <div class="card" v-on:click.stop="getDetails(product)">
    <h2 class="sku">{{ product.productSku }}</h2>
    <h2 class="price">{{ price }}</h2>
    <p class="name">{{ product.name }}</p>
    <div class="image">
      <img src="https://via.placeholder.com/700x200.jpg">
    </div>
    <div class="icon">
      <cart-icon  v-bind:cartItem="cartItem" :productName="productName"/>
    </div>
  </div>
</template>

<script>
import CartIcon from '../components/CartIcon.vue';
export default {
  components: { CartIcon },
  name: 'product-card',
  props: ['product'],
  data(){
    return{
      cartItem: {
        productId: this.product.productId,
        quantity: 1
      },
    }
  },
  computed: {
      productName(){
          return this.product.name;
      },
      price(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
        }).format(this.product.price);
      }
  },
  methods: {
    getDetails(product){
      this.$router.push({name: "details", params: {productId: product.productId}});
    }
  }
}
</script>

<style scoped>
  .card{
    display: grid;
    border: 0.25px solid black;
    border-radius: 10px;
    width: 225px;
    height: 225px;
    padding: 8px;
    margin: 10px;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 2fr 1fr 6fr 2fr;
    grid-template-areas: 
    "sku price"
    "name name"
    "image image"
    ". icon";
  }
  .sku{
    grid-area: sku;
  }
  .price{
    grid-area: price;
    display: flex;
    justify-content: flex-end ;
  }
  .name{
    grid-area: name;
    display: flex;
    align-items: flex-start;
  }
  .image{
    grid-area: image;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .image > img{
    width: 100%;
    height: auto;
    border-radius: 15px;
  }
  .icon{
    grid-area: icon;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
  }
</style>