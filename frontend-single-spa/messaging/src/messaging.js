import { Observable, Subject } from 'rxjs';
import { OrderAPI } from "@vue-app-mod/order-api";

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"
const TOPIC_COMMAND_RESPONSE_ADD_ITEM = "TOPIC_COMMAND_RESPONSE_ADD_ITEM"
const TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED = "TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED"

const MICRO_FRONTEND_NAVIGATOR = "MICRO_FRONTEND_NAVIGATOR"
const MICRO_FRONTEND_CATALOG = "MICRO_FRONTEND_CATALOG"

let observableForOrderAPI
let observableForNavigator
let observableForCatalog

export default {
  TOPIC_COMMAND_ADD_ITEM,
  TOPIC_COMMAND_RESPONSE_ADD_ITEM,
  TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED,

  MICRO_FRONTEND_NAVIGATOR,
  MICRO_FRONTEND_CATALOG,

  getObservable(microFrontendName) {
    switch (microFrontendName) {      
      case MICRO_FRONTEND_NAVIGATOR:
        if (!observableForNavigator) {
          observableForNavigator = new Subject()
        }
        return observableForNavigator
      case MICRO_FRONTEND_CATALOG:
        if (!observableForCatalog) {
          observableForCatalog = new Subject()
        }
        return observableForCatalog
    }
  },

  send(message) {
    observableForOrderAPI = new Subject(),
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
        if (observableForCatalog) {
          observableForCatalog.next(message)
        }
        break
      case TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED:
        if (observableForNavigator) {
          observableForNavigator.next(message)
        }
        break
    }
  },
};

