import { Observable } from 'rxjs';
import { Messaging } from "@vue-app-mod/messaging";


let OrderAPI = {

  initialize() {
    console.log("OrderAPI.initialize")
    debugger
    let jhg = Messaging;
    
      /*
    if (Messaging.getObservable(Messaging.TOPIC_COMMAND_ADD_ITEM)) {
      console.log("nik huhu 7")
    
      Messaging.getObservable(Messaging.TOPIC_COMMAND_ADD_ITEM).subscribe(x => {
        console.log("nik huhu")
        console.log(x);
      });
     
    }
     */
  },

  addProductToOrder(productId) {
    console.log("addProductToOrder")
  }
}

export default OrderAPI

