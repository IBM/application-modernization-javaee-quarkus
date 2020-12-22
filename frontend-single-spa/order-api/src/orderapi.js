import { Observable, Subject } from 'rxjs';

let OrderAPI = {

  initialize(observableFromMessaging) {
    console.log("order-api - orderapi.js - initialize invoked")

    observableFromMessaging.subscribe({
      next: (message) => { 
        console.log("order-api - orderapi.js - message:")
        console.log("Topic: " + message.topic) 
        console.log("CommandId: " + message.commandId) 
        console.log("ProductId: " + message.payload.productId) 
        this.addProductToOrder(message.payload.productId, message.commandId)
      }
    })
  },

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
        "If-Match": 4
      },
      body: JSON.stringify(body)
    }
    fetch(addProductToOrderUrl, requestOptions)
      .then(response => response.json())
      .then((json) => {
        console.log("Product has been addded to order")
      })
      .catch((error) => {
        console.log("Product has NOT been addded to order")
        console.error(error);
      });
  }
}

export default OrderAPI