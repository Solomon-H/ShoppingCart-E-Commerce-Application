<template>
  <tr>
    <td class="clickable" v-on:click.stop="getDetails(product)">{{ product.productSku }}</td>
    <td>{{ product.name }}</td>
    <td>{{ price }}</td>
    <td><cart-icon v-bind:cartItem="cartItem" :productName="productName"/></td>
  </tr>
</template>

<script>
import CartIcon from './CartIcon.vue';
export default {
components: { CartIcon },
  name: 'product-table',
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
.clickable{
  text-decoration: underline 0.25px solid black;
}
</style>