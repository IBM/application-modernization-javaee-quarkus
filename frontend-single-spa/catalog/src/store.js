import Vue from "vue";
import Vuex from "vuex";

const COMMAND_STATUS_INVOKED = "Invoked"
const COMMAND_STATUS_SUCCESS = "Success"
const COMMAND_STATUS_ERROR = "Error"

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authenticationEnabled: "authentication-enabled-no",
    products: [],
    categories: [],
    commands: []
  },
  mutations: {
    sendCommand(state, payload) {
      payload.status = COMMAND_STATUS_INVOKED;
      state.commands.push(payload)
    },
    commandResponseReceived(state, payload) {
      if (state.commands) {
        state.commands.forEach((command) => {
          if (command.commandId == payload.commandId) {
            if (payload.successful = true) {
              command.status = COMMAND_STATUS_SUCCESS
            }
            else {
              command.status = COMMAND_STATUS_ERROR
            }                  
          }
        });
      }    
    },
    addProducts(state, payload) {
      state.products = payload;
    },
    addCategories(state, payload) {
      state.categories = payload;
    },
  },
  actions: {},
});
