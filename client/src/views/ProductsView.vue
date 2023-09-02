<template>
  <div class="home">
    <div id="heading-line">
      <h1>
        Products
        <loading-spinner id="spinner" v-bind:spin="isLoading" />
      </h1>
      <form action="POST" >
        <input type="text" name="searchTerm" id="searchTerm" placeholder="Search Products" v-model="searchTerm"
        v-on:keyup="searchProduct">
      </form>
    </div>

    <p id="login-message" v-if="!isLoggedIn">
      Welcome! You may browse anonymously as much as you wish,<br />
      but you must
      <router-link v-bind:to="{ name: 'login' }">Login</router-link> to add
      items to your shopping cart.
    </p>
    
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: cardView }"
      v-on:click="cardView = true"
      icon="fa-solid fa-grip"
      title="View tiles"
    />
    <font-awesome-icon
      v-bind:class="{ 'view-icon': true, active: !cardView }"
      v-on:click="cardView = false"
      icon="fa-solid fa-table"
      title="View table"
    />
    <section v-bind:class="{'products-card' : cardView}">
      <product-card v-show="cardView" v-bind:product="product" v-for="product in products" v-bind:key="product.productSku"/>

      <div class="table-container" v-show="!cardView">
        <table>
          <thead>
            <tr>
              <th>SKU</th>
              <th>Product</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            <product-row class="row" v-bind:product="product" v-for="product in products" v-bind:key="product.productSku"/>
          </tbody>
        </table>

      </div>
    </section>
  </div>
</template>

<script>
import CommerceApiService from "../services/CommerceApiService.js";

import LoadingSpinner from "../components/LoadingSpinner.vue";
import ProductCard from "../components/ProductCard.vue";
import ProductRow from "../components/ProductRow.vue";
export default {
  name: "ProductsView",
  components: {
    LoadingSpinner,
    ProductCard,
    ProductRow
  },
  data() {
    return {
      isLoading: false,
      cardView: true,
      products: [],
      searchTerm: ""
    };
  },

  methods: {
    searchProduct(){
      this.isLoading = true;
      CommerceApiService
      .searchProduct(this.searchTerm)
      .then(response => {
        if (response.data != []){
          this.products = response.data;
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
      this.isLoading = false;
    }
  },

  computed: {
    isLoggedIn() {
      return this.$store.state.token.length > 0;
    },
  },

  created() {
      CommerceApiService
      .getAllProducts()
      .then(response => {
        if (response.status == 200){
          response.data.forEach(product => {
            this.products.push(product);
          });
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
};
</script>

<style scoped>
#heading-line{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
#heading-line > form{
  margin-top: 10.72px;
  margin-right: 8px;
}

.products-card{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
  align-items: center;
}
@media only screen and (max-width: 600px){
  .products-card{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
  }
}

#spinner {
  color: green;
}

.view-icon {
  font-size: 1.2rem;
  margin-right: 7px;
  padding: 3px;
  color: #444;
  border-radius: 3px;
}

.view-icon.active {
  background-color: lightgreen;
}

.view-icon:not(.active) {
  font-size: 1.2rem;
  margin-right: 7px;
  cursor: pointer;
}

.view-icon:not(.active):hover {
  color: blue;
  background-color: rgba(255, 255, 255, 0.7);
}

table{
  border-spacing: 30px;
}
</style>