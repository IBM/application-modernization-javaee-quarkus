import "./set-public-path";
import Vue from "vue";
import singleSpaVue from "single-spa-vue";

import App from "./App.vue";
import router from "./router";

Vue.config.productionTip = false;

import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

Vue.use(VueMaterial)

const vueLifecycles = singleSpaVue({
  Vue,
  appOptions: {
    el: '#navigator',
    render(h) {
      return h(App, { props: { githubLink: this.githubLink } });
    },
    router,
  },
});

export const bootstrap = vueLifecycles.bootstrap;
export const mount = vueLifecycles.mount;
export const unmount = vueLifecycles.unmount;
