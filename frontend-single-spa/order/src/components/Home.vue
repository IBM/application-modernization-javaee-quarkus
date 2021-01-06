<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <div style="margin-left: 20px">
      <h3>Shopping Cart</h3>
      <div
        v-for="order in this.$store.state.orders"
        :key="order.orderId"
        class=""
      >
        <mwc-list class="mdc-list" id="mdclist">
          <div class="">Total: {{ order.total }}</div>
          <br />
          <div
            v-for="lineitem in order.lineitems"
            :key="lineitem.name"
            class=""
          >
            <mwc-list-item class="mwc-list-item" twoline hasMeta graphic="large">
              <span>{{ lineitem.product.name }}</span>
              <span slot="secondary"
                >Quantity: {{ lineitem.quantity }} - Price:
                {{ lineitem.product.price }} - Current Price:
                {{ lineitem.priceCurrent }}</span
              >
              <span slot="meta">{{ lineitem.amount }}</span>
              <img slot="graphic" v-bind:src="lineitem.product.image" />
            </mwc-list-item>
            <br />
          </div>
        </mwc-list>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      apiUrlOrders:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Customer/Orders",
      loadingOrders: false,
      errorLoadingOrders: "",
    };
  },
  created() {
    this.readOrders();
  },
  methods: {
    readOrders() {
      if (this.loadingOrders == false) {
        this.loadingOrders = true;
        fetch(this.apiUrlOrders)
          .then((r) => r.json())
          .then((json) => {
            this.loadingOrders = false;
            this.$store.commit("addOrders", json);
          })
          .catch((error) => {
            this.loadingOrders = false;
            console.error(error);
            this.errorloadingOrders = error;
          });
      }
    },
  },
};
</script>
<style scoped>
.mwc-list-item {
  --mdc-list-item-graphic-size: 80px;
}
.mdc-list {
  max-width: 500px;
  width:500px;
}
</style>