<template>
  <div class="container">
    <router-view />
  </div>
</template>

<script>
import { Messaging } from "@vue-app-mod/messaging";

export default {
  data() {
    return {
    }
  },
  created() {
    let observableFromMessaging = Messaging.getObservable(Messaging.MICRO_FRONTEND_ORDER)
   
    observableFromMessaging.subscribe({
      next: (message) => { 
        console.log("order - App.vue - created - message:")
        console.log("Topic: " + message.topic) 
        console.log("CommandId: " + message.commandId) 
        console.log("ProductId: " + message.payload.productId) 
        this.addProductToOrder(message.payload.productId, message.commandId)
      }
    })
  },
  methods: {
    addProductToOrder(productId, commandId) {
      let addProductToOrderUrl = "http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/OpenOrder/LineItem"
      let body = {
        "quantity":1,
        "productId":productId
      }
      const requestOptions = {
        method: "POST",
        headers: { 
          "Content-Type": "application/json", 
          "Accept": "*/*",
          "If-Match": 1
        },
        body: JSON.stringify(body)
      }
      fetch(addProductToOrderUrl, requestOptions)
        .then(response => response.json())
        .then((json) => {
          console.log("Product has been addded to order")
          
          let amountProducts = json.lineitems.length
          let amountLineItems = 0
          json.lineitems.forEach(lineItem => {
            amountLineItems = amountLineItems + lineItem.quantity;
          })
          console.log("Products in shopping cart: " + amountProducts)
          console.log("Line items in shopping cart: " + amountLineItems)
          let messageAmountShoppingCartChanged = {
            topic: Messaging.TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED,
            payload: {
              amountLineItems: amountLineItems,
              amountProducts: amountProducts
            },
          };
          Messaging.send(messageAmountShoppingCartChanged);

          let messageCommandResponseAddItem = {
            topic: Messaging.TOPIC_COMMAND_RESPONSE_ADD_ITEM,
            payload: {
              succesful: true,
              commandId: commandId           
            }
          };
          Messaging.send(messageCommandResponseAddItem);
        })
        .catch((error) => {
          console.log("Product has NOT been addded to order")
          console.error(error);
          let messageCommandResponseAddItem = {
            topic: Messaging.TOPIC_COMMAND_RESPONSE_ADD_ITEM,
            payload: {
              succesful: false            
            }
          };
          Messaging.send(messageCommandResponseAddItem);
        });
    }
  }     
}    
</script>

<style scoped>
.container {
  margin-left: var(--navbar-width);
}
</style>
