import { Observable, Subject } from 'rxjs';
import { OrderAPI } from "@vue-app-mod/order-api";

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"
let observable_TOPIC_COMMAND_ADD_ITEM;

export default {
  TOPIC_COMMAND_ADD_ITEM,

  send(message) {
    const subject = new Subject();

    OrderAPI.initialize(subject, this.getObservable, this.TOPIC_COMMAND_ADD_ITEM)

    let topic = message.topic
    let commandId = message.commandId
    let payload = message.payload
    console.log("Messaging.send invoked - topic: " + topic + " commandId: " + commandId)

    switch (topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        console.log("productId: " + payload.productId)
        subject.next(message);
        subject.complete();
        /*if (!observable_TOPIC_COMMAND_ADD_ITEM) {
          console.log("nik2")
          subject.next(message);
          observable_TOPIC_COMMAND_ADD_ITEM = new Observable(subscriber => {
            console.log("nik5")
            console.log('Hello');
            subscriber.next(message);
          });
 
        }
        else {
          console.log("nik3")
        }*/
        console.log("nik4")
        //observable_TOPIC_COMMAND_ADD_ITEM.next('Hello');        
        break;
    }
  },

  getObservable(topic) {
    switch (topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        return observable_TOPIC_COMMAND_ADD_ITEM;
        break;
    }
  }
};

