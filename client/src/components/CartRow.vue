<template>
    <tr>
        <td>{{ item.quantity }}</td>
        <td class="name" v-on:click.stop="getDetails(item)">{{ item.product.name }}</td>
        <td>{{ price }}</td>
        <td>{{ amount }}</td>
        <td>
            <button v-on:click.stop="deleteItem">
                <font-awesome-icon class="i"
                icon="fa-solid fa-trash-can"
                title="Delete Item"
                />
            </button>
        </td>
    </tr>
</template>

<script>
import CommerceApiService from '../services/CommerceApiService';
export default {
name: 'cartRow',
props: {
    item: {
        type: Object
    }
},
data(){
    return{
        delete: {
            productId: this.item.productId,
            quantity: this.item.quantity
        }
    }
},
methods: {
    deleteItem(){
        CommerceApiService
        .removeCartItem(this.item.cartItemId, this.delete)
        .then(response => {
            if (response.status == 204){
                this.$router.go();
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
    getDetails(item){
      this.$router.push({name: "details", params: {productId: item.productId}});
    }
},
computed: {
    amount(){
        return Intl.NumberFormat(`en-US`, {
        currency: `USD`,
        style: "currency",
        }).format(this.item.product.price * this.item.quantity);
    },
    price(){
            return Intl.NumberFormat(`en-US`, {
            currency: `USD`,
            style: "currency",
        }).format(this.item.product.price);
    }
}
}
</script>

<style scoped>
.name{
    text-decoration: underline;
}

tr{
    flex-wrap: wrap;
}
</style>