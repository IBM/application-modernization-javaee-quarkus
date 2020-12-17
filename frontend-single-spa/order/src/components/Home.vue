<template>
  <div>
    <h1 style="text-align: center;">Order</h1>

    <div
      v-for="order in this.$store.state.orders"
      :key="order.orderId"
      class=""
    >
      <div class="">Total: {{ order.total }}</div>
      <div class="">Status: {{ order.status }}</div>
      <br />
      <div v-for="lineitem in order.lineitems" :key="lineitem.orderId" class="">
        <div class="">Amount: {{ lineitem.amount }}</div>
        <div class="">Current Price: {{ lineitem.priceCurrent }}</div>
        <div class="">Price: {{ lineitem.product.price }}</div>
        <div class="">Product Name: {{ lineitem.product.name }}</div>
        <div class="">
          Product Description: {{ lineitem.product.description }}
        </div>
        <div class="">Image: {{ lineitem.product.image }}</div>
        <div class="">Quantity: {{ lineitem.quantity }}</div>
        <br />
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
            console.log(json);
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
