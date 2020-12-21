import Vue from "vue";
import Vuex from "vuex";

const COMMAND_STATUS = "Invoked"

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
      payload.status = COMMAND_STATUS;
      state.commands.push(payload)
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
