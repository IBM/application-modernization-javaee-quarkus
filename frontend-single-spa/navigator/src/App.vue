<template>
  <div>
    <nav class="navbar-links">
      <div>
        <ul>
          <li>
            <router-link
              to="/catalog"
              class="nav-link"
              active-class="active-nav-link"
              >Catalog</router-link
            >
          </li>
        </ul>
        <ul>
          <li>
            <router-link
              to="/order"
              class="nav-link"
              active-class="active-nav-link"
              >Shopping Cart ({{ amountLineItems }})</router-link
            >
          </li>
        </ul>
        <ul>
          <li>
            <router-link
              to="/account"
              class="nav-link"
              active-class="active-nav-link"
              >Account</router-link
            >
          </li>
        </ul>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<script>
import { Messaging } from "@vue-app-mod/messaging";
export default {
  data() {
    return {
      amountLineItems: 0,
      apiUrlOrders:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders"
    }
  },
  created() {
    let observable = Messaging.getObservable(Messaging.MICRO_FRONTEND_NAVIGATOR)
    observable.subscribe({
      next: (message) => { 
        console.log("navigator - App.vue - amountLineItems: " + message.payload.amountLineItems)
        this.amountLineItems = message.payload.amountLineItems
      }
    })

    fetch(this.apiUrlOrders)
      .then((response) => response.json())
      .then((json) => {
        this.amountLineItems = 0
        if (json[0]) {
          json[0].lineitems.forEach(lineItem => {
            this.amountLineItems = this.amountLineItems + lineItem.quantity;
          })
        }
      })
      .catch((error) => {
        console.error(error);
      });
  }
};
</script>
