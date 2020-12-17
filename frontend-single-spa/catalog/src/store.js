import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    authenticationEnabled: "authentication-enabled-no",
    products: [],
    categories: [],
  },
  mutations: {
    addProducts(state, payload) {
      state.products = payload;
    },
    addCategories(state, payload) {
      state.categories = payload;
    },
  },
  actions: {},
});
