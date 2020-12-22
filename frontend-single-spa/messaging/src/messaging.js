import { Observable, Subject } from 'rxjs';

const TOPIC_COMMAND_ADD_ITEM = "TOPIC_COMMAND_ADD_ITEM"
const TOPIC_COMMAND_RESPONSE_ADD_ITEM = "TOPIC_COMMAND_RESPONSE_ADD_ITEM"
const TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED = "TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED"

const MICRO_FRONTEND_NAVIGATOR = "MICRO_FRONTEND_NAVIGATOR"
const MICRO_FRONTEND_CATALOG = "MICRO_FRONTEND_CATALOG"
const MICRO_FRONTEND_ORDER = "MICRO_FRONTEND_ORDER"

let observableForNavigator
let observableForCatalog
let observableForOrder

export default {
  TOPIC_COMMAND_ADD_ITEM,
  TOPIC_COMMAND_RESPONSE_ADD_ITEM,
  TOPIC_EVENT_AMOUNT_LINE_ITEMS_CHANGED,

  MICRO_FRONTEND_NAVIGATOR,
  MICRO_FRONTEND_CATALOG,
  MICRO_FRONTEND_ORDER,

  getObservable(microFrontendName) {
    switch (microFrontendName) {      
      case MICRO_FRONTEND_NAVIGATOR:
        if (!observableForNavigator) {
          observableForNavigator = new Subject()
        }
        return observableForNavigator
      case MICRO_FRONTEND_ORDER:
          if (!observableForOrder) {
            observableForOrder = new Subject()
          }
          return observableForOrder
      case MICRO_FRONTEND_CATALOG:
        if (!observableForCatalog) {
          observableForCatalog = new Subject()
        }
        return observableForCatalog
    }
  },

  send(message) {
    let topic = message.topic
    let commandId = message.commandId
    console.log("messaging - messaging.js - send invoked - topic: " + topic + " commandId: " + commandId)
    console.log(message)

    switch (topic) {
      case TOPIC_COMMAND_ADD_ITEM:
        if (observableForOrder) {
          observableForOrder.next(message)
        }
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