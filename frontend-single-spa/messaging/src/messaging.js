import { Observable, Subject } from 'rxjs';
import { OrderAPI } from "@vue-app-mod/order-api";

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"
const TOPIC_COMMAND_RESPONSE_ADD_ITEM = "TOPIC_COMMAND_RESPONSE_ADD_ITEM"
const TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED = "TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED"

export default {
  TOPIC_COMMAND_ADD_ITEM,
  TOPIC_COMMAND_RESPONSE_ADD_ITEM,
  TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED,

  send(message) {
    const observableForOrderAPI = new Subject()
    OrderAPI.initialize(this, observableForOrderAPI)

    let topic = message.topic
    let commandId = message.commandId
    console.log("messaging - messaging.js - send invoked - topic: " + topic + " commandId: " + commandId)
    console.log(message)

    switch (topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        observableForOrderAPI.next(message)
        observableForOrderAPI.complete()
        break
      case TOPIC_COMMAND_RESPONSE_ADD_ITEM:
        // to be done
        break
      case TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED:
        // to be done
        break
    }
  },
};

