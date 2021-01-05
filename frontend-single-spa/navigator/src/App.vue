<template>
  <div>
    <aside class="mdc-drawer">
  <div class="mdc-drawer__content">
    <nav class="mdc-list">
     
          <router-link
              to="/catalog"
              class="mdc-list-item mdc-list-item--activated" aria-current="page"
              active-class="active-nav-link"
              >Catalog</router-link
            >
      <a class="mdc-list-item" >
        <span class="mdc-list-item__ripple"></span>
        <i class="material-icons mdc-list-item__graphic" aria-hidden="true">send</i>
        <span class="mdc-list-item__text">
           <router-link
              to="/order"
              class="nav-link"
              active-class="active-nav-link"
              >Shopping Cart ({{ amountLineItems }})</router-link
            >
        </span>
      </a>
      <a class="mdc-list-item" >
        <span class="mdc-list-item__ripple"></span>
        <i class="material-icons mdc-list-item__graphic" aria-hidden="true">drafts</i>
        <span class="mdc-list-item__text">
          <router-link
              to="/account"
              class="nav-link"
              active-class="active-nav-link"
              >Account</router-link
            >
        </span>
      </a>
    </nav>
  </div>
   <router-view />
</aside>
    
   
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
