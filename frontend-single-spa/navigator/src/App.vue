<template>
  <div>
    <md-app>
      <md-app-drawer md-permanent="full">
        <md-toolbar class="md-transparent" md-elevation="0">
          Navigation
        </md-toolbar>

        <md-list>
          
<router-link
              to="/catalog"
              class="nav-link"
              active-class="active-nav-link"
              >Catalog</router-link
            >

          <router-link
              to="/order"
              class="nav-link"
              active-class="active-nav-link"
              >Shopping Cart ({{ amountLineItems }})</router-link
            >

          <md-list-item>
            <md-icon>delete</md-icon>
            <span class="md-list-item-text">Trash</span>
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
      amountLineItems: 0,
      apiUrlOrders:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders",
    };
  },
  created() {
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
};
</script>
