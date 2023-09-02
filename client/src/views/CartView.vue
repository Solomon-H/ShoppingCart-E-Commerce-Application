<template>
  <div class="cart">
      <div class="cart-header">
        <h1>Shopping Cart</h1>
        <button class="clear" v-on:click="clear">Clear Cart</button>
      </div>
      <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Qty</th>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Amount</th>
                </tr>
            </thead>
            <tbody>
                <cart-row class="item" v-for="item in cart.items" v-bind:key="item.cartItemId" :item="item"/>
                <tr id="line">
                    <td colspan="4"><hr></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td> &nbsp; </td>
                    <td>Item Subtotal:</td>
                    <td> &nbsp; </td>
                    <td>{{ subtotal }}</td>
                </tr>
                <tr>
                    <td> &nbsp; </td>
                    <td>Tax:</td>
                    <td> &nbsp; </td>
                    <td>{{ tax }}</td>
                </tr>
                <tr>
                    <td> &nbsp; </td>
                    <td>Total:</td>
                    <td> &nbsp; </td>
                    <td>{{ total }}</td>
                </tr>
            </tfoot>      
        </table>
      </div>
  </div>
</template>

<script>
import CartRow from "../components/CartRow.vue";
import CommerceApiService from "../services/CommerceApiService.js";
export default {
    name: 'cartView',
    components: {
        CartRow
    },
    data(){
        return{
           cart: {
               type: Object
           }
        }
    },
    methods: {
        clear(){
            CommerceApiService
            .clearCart()
            .then(response => {
                if(response.status == 204){
                    this.$router.push({name: "products"});
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
    },
    computed: {
        subtotal(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
            }).format(this.cart.itemSubtotal);
        },
        tax(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
            }).format(this.cart.tax);
        },
        total(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
            }).format(this.cart.total);
        }
    },
    created(){
        CommerceApiService
        .getCart()
        .then(response => {
            if (response.status == 200){
                this.cart = response.data;
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
    .cart{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: flex-start;
    }
    .cart-header{
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }
    .clear{
        margin: 8px;
    }
    .table-container{
        display: flex;
        justify-content: center;
        align-items: center;
        flex-wrap: wrap;
    }

    table{
        border-spacing: 30px 10px;
    }
    #line{
        width: 100%;
    }
    
    @media only screen and (max-width: 600px){
        table{
            border-spacing: 15px 5px;
        }
    }
</style>