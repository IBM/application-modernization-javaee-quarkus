<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <div style="margin-left: 20px">
      <h3>Catalog - {{ categoryName }}</h3>
      <mwc-list class="mdc-list" id="mdclist">
      <div
        v-for="product in this.$store.state.products"
        :key="product.id"
        class=""
      >        
          <mwc-list-item class="mwc-list-item" twoline graphic="large" hasMeta v-on:click="listItemSelected(product.id)">
            <span>{{ product.name }}</span>
            <span slot="secondary">{{ product.description }}</span>

            <img slot="graphic" v-bind:src="product.image" />

            <span slot="meta">{{ product.price }}</span>
          </mwc-list-item>   
          <br>     
      </div>
      </mwc-list>

      <br>
      <mwc-button id='addbutton'
        outlined
        :disabled="this.selectedProductId == ''"
        label="Add to Shopping Cart"
        v-on:click="addButtonClicked()"
      ></mwc-button>
      <br />
      <br />
      <div id="message" v-show="showLoadingMessage()">
        Products are being added to the shopping cart ...
      </div>
      <br />
    </div>
  </div>
</template>

<script>
import { Messaging } from "@vue-app-mod/messaging";
import "@material/mwc-button";
import "@material/mwc-list/mwc-list.js";
import "@material/mwc-list/mwc-list-item.js";
import "@material/mwc-icon";
import "@material/mwc-top-app-bar-fixed";

export default {
  data() {
    return {
      apiUrlProducts:
        "http://localhost:9083/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=",
      loadingProducts: false,
      errorLoadingProducts: "",
      categoryName: "Movies",
      selectedProductId: ""
    };
  },
  computed: {    
  },
  created() {   
    let observable = Messaging.getObservable(Messaging.MICRO_FRONTEND_CATALOG);
    observable.subscribe({
      next: (message) => {
        console.log("catalog - Home.vue - message: ");
        switch (message.topic) {
          case Messaging.TOPIC_COMMAND_RESPONSE_ADD_ITEM:
            this.$store.commit("commandResponseReceived", message.payload);
            break;
          case Messaging.TOPIC_NAVIGATOR_CATEGORY_CHANGED:
            this.readProducts(
              message.payload.categoryId,
              message.payload.categoryName
            );
            break;
        }
      },
    });

    this.readProducts(2, "Movies");
  },
  methods: {
    addButtonClicked() {
      this.addToShoppingCart(this.selectedProductId)        
      this.selectedProductId = ''
    },
    listItemSelected(productId) {      
      this.selectedProductId = productId;          
    },
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
    readProducts(categoryId, categoryName) {
      this.categoryName = categoryName;
      if (this.loadingProducts == false) {
        this.loadingProducts = true;
        fetch(this.apiUrlProducts + categoryId)
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
  },
};
</script>
<style scoped>
.mwc-list-item {
  --mdc-list-item-graphic-size: 80px;
}
.mdc-list {
  max-width: 500px;
  width:500px;
}
</style>
