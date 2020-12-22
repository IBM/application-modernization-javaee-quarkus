import { Observable, Subject } from 'rxjs';
import { OrderAPI } from "@vue-app-mod/order-api";

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"

export default {
  TOPIC_COMMAND_ADD_ITEM,

  send(message) {
    const observableForOrderAPI = new Subject()
    OrderAPI.initialize(observableForOrderAPI)

    let topic = message.topic
    let commandId = message.commandId
    console.log("messaging - messaging.js - send invoked - topic: " + topic + " commandId: " + commandId)

    switch (topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        observableForOrderAPI.next(message);
        observableForOrderAPI.complete();
        break;
    }
  },
};

