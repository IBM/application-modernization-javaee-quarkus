import { Subject } from 'rxjs';

//let subscription = subject.subscribe(data => console.log(`data: ${data}`));
import { Messaging } from "@vue-app-mod/messaging";


let BusinessFunctionality = {

  



  initialize() {
    console.log("initialize")
    if (Messaging.getObservable(Messaging.TOPIC_COMMAND_ADD_ITEM)) {
      console.log("nik huhu 7")
      Messaging.getObservable(Messaging.TOPIC_COMMAND_ADD_ITEM).subscribe(x => {
        console.log("nik huhu")
        console.log(x);
      });
    }
  },

  addProductToOrder(productId) {
    console.log("addProductToOrder")
  }
}

export default BusinessFunctionality



/*
http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/OpenOrder/LineItem
{"quantity":1,"productId":3}
*/