<template>
  <div>
    <mwc-top-app-bar-fixed>
      <div slot="title">Electronic and Movie Depot</div>
    </mwc-top-app-bar-fixed>
    
    <md-app>
      <md-app-drawer md-permanent="full" style="width: 240px;">
        <br>
        <md-list>
          <md-list-item to="/catalog" exact>
            <md-icon style="margin-right: 10px;">explore</md-icon>
            <span class="md-list-item-text">Catalog</span>
          </md-list-item>

          <div
            v-for="category in this.categories"
            :key="category.name"
            class=""
          >
          <md-list-item style="margin-left:50px">
            <span class="md-list-item-text">{{ category.name }}</span>
          </md-list-item>
            <div
              style="padding-left: 50px"
              v-for="subCategory in category.subCategories"
              :key="subCategory.id"
              class=""
            >
              <md-list-item @click="loadProducts(subCategory.id, subCategory.name)" style="padding-left:0px" exact>
                <md-icon style="margin-right: 10px;">explore</md-icon>
                <span class="md-list-item-text">{{ subCategory.name }}</span>
              </md-list-item>
            </div>
          </div>

          <md-list-item to="/order" exact
            ><md-icon style="margin-right: 10px;">add_shopping_cart</md-icon>
            <span class="md-list-item-text"
              >Shopping Cart ({{ amountLineItems }})</span
            >
          </md-list-item>
          <md-list-item to="/account" exact>
            <md-icon style="margin-right: 10px;">settings</md-icon>
            <span class="md-list-item-text">Account</span>
          </md-list-item>
        </md-list>
      </md-app-drawer>
    </md-app>
  </div>
</template>

<script>
import { Messaging } from "@vue-app-mod/messaging";
export default {
  data() {
    return {
      loadingCategories: false,
      amountLineItems: 0,
      apiUrlCategories:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Category",
      apiUrlOrders:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders",
    };
  },
  created() {
    this.readCategories();

    let observable = Messaging.getObservable(
      Messaging.MICRO_FRONTEND_NAVIGATOR
    );
    observable.subscribe({
      next: (message) => {
        console.log(
          "navigator - App.vue - amountLineItems: " +
            message.payload.amountLineItems
        );
        this.amountLineItems = message.payload.amountLineItems;
      },
    });

    fetch(this.apiUrlOrders)
      .then((response) => response.json())
      .then((json) => {
        this.amountLineItems = 0;
        if (json[0]) {
          json[0].lineitems.forEach((lineItem) => {
            this.amountLineItems = this.amountLineItems + lineItem.quantity;
          });
        }
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    loadProducts (categoryId, categoryName) {
      let commandId = Date.now();
      let message = {
        topic: Messaging.TOPIC_NAVIGATOR_CATEGORY_CHANGED,
        commandId: commandId,
        payload: {
          categoryId: categoryId,
          categoryName: categoryName
        },
      };
      Messaging.send(message);
      this.$router.push('/catalog').catch(err => {})
    },
    readCategories() {
      if (this.loadingCategories == false) {
        this.loadingCategories = true;
        fetch(this.apiUrlCategories)
          .then((r) => r.json())
          .then((json) => {
            this.loadingCategories = false;
            this.categories = json;
          })
          .catch((error) => {
            this.loadingCategories = false;
            console.error(error);
          });
      }
    },
  },
};
</script>
