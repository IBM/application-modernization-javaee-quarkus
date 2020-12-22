<template>
  <div>
    <h1 style="text-align: center">Catalog</h1>
    <div id="message" v-show="showLoadingMessage()" style="color: red">
      One of more products are being added to the shopping cart
    </div>
    <br />
    <h3>Products</h3>
    <div
      v-for="product in this.$store.state.products"
      :key="product.id"
      class=""
    >
      <div class="">
        {{ product.name }}
      </div>
      <div class="">
        {{ product.description }}
      </div>
      <div class="">
        {{ product.image }}
      </div>
      <div class="">
        {{ product.price }}
      </div>
      <button v-on:click="addToShoppingCart(product.id)">
        Add to Shopping Cart
      </button>
      <br />
      <br />
    </div>
    <h3>Categories</h3>
    <div
      v-for="category in this.$store.state.categories"
      :key="category.name"
      class=""
    >
      <div class="">
        {{ category.name }}
      </div>
      <div
        style="padding-left: 20px"
        v-for="subCategory in category.subCategories"
        :key="subCategory.id"
        class=""
      >
        <div class="">
          {{ subCategory.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Messaging } from "@vue-app-mod/messaging";
export default {
  data() {
    return {
      apiUrlProducts:
        "http://localhost:9083/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2",
      apiUrlCategories:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Category",
      loadingProducts: false,
      loadingCategories: false,
      errorLoadingProducts: "",
      errorLoadingCategories: "",
    };
  },
  created() {
    let observable = Messaging.getObservable(Messaging.MICRO_FRONTEND_CATALOG)
    observable.subscribe({
      next: (message) => { 
        console.log("catalog - Home.vue - message: ")
        console.log(message)
        this.$store.commit("commandResponseReceived", message.payload);
      }
    })

    this.readProducts();
    this.readCategories();
  },
  methods: {
    showLoadingMessage() {
      let output = false;
      if (this.$store.state.commands) {
        this.$store.state.commands.forEach((command) => {
          if (command.status == "Invoked") output = true;
        });
      }
      return output;
    },
    addToShoppingCart(productId) {
      let commandId = Date.now();
      let message = {
        topic: Messaging.TOPIC_COMMAND_ADD_ITEM,
        commandId: commandId,
        payload: {
          productId: productId,
        },
      };
      this.$store.commit("sendCommand", message);
      Messaging.send(message);
    },
    readProducts() {
      if (this.loadingProducts == false) {
        this.loadingProducts = true;
        fetch(this.apiUrlProducts)
          .then((r) => r.json())
          .then((json) => {
            this.loadingProducts = false;
            this.$store.commit("addProducts", json);
          })
          .catch((error) => {
            this.loadingProducts = false;
            console.error(error);
            this.errorLoadingProducts = error;
          });
      }
    },
    readCategories() {
      if (this.loadingCategories == false) {
        this.loadingCategories = true;
        fetch(this.apiUrlCategories)
          .then((r) => r.json())
          .then((json) => {
            this.loadingCategories = false;
            this.$store.commit("addCategories", json);
            this.categories = json;
          })
          .catch((error) => {
            this.loadingCategories = false;
            console.error(error);
            this.errorloadingCategories = error;
          });
      }
    },
  },
};
</script>
