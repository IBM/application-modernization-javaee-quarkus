<template>
  <div>
    <h1 style="text-align: center;">Catalog</h1>
  </div>
</template>

<script>
export default {
  data() {
    return {
      apiUrlProducts:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2",
      apiUrlCategories:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Category",
      products: [],
      categories: [],
      loading: false,
      error: "",
    };
  },
  created() {
    this.readProducts();
    //this.readCategories();
  },
  methods: {
    readProducts() {
      fetch(apiUrlProducts)
        .then((r) => r.json())
        .then((json) => {
          //this.products = json;
          console.log(json);
        });
    },
    readCategories() {
      fetch(this.apiUrlCategories, { mode: "no-cors" })
        .then((r) => r.json())
        .then((json) => {
          //this.categories = json;
          console.log(json.message);
        });
    },
  },
};
</script>
