import { Observable, Subject } from 'rxjs';


let OrderAPI = {

  /**
   * 
   * @param {Subject} subject 
   * @param {*} getObservable 
   * @param {*} TOPIC_COMMAND_ADD_ITEM 
   */
  initialize(subject, getObservable, TOPIC_COMMAND_ADD_ITEM) {
    console.log("OrderAPI.initialize")

    subject.subscribe({
      next: (message) => { console.log("OrderAPI: "); console.log(message) }
    })


    if (getObservable(TOPIC_COMMAND_ADD_ITEM)) {
      console.log("nik huhu 7")

      getObservable(TOPIC_COMMAND_ADD_ITEM).subscribe(x => {
        console.log("nik huhu")
        console.log(x);
      });

    }

  },

  addProductToOrder(productId) {
    console.log("addProductToOrder")
  }
}

export default OrderAPI

