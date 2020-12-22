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
      }
    })
  }
}

export default OrderAPI