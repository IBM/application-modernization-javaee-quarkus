import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authenticationEnabled: "authentication-enabled-no",
    orders: [],
  },
  mutations: {
    addOrders(state, payload) {
      state.orders = payload;
    },
  },
  actions: {},
});
