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
      loadingProducts: false,
      errorLoadingProducts: "",
    };
  },
  created() {
    this.readProducts();
    //this.readCategories();
  },
  methods: {
    readProducts() {
      if (this.loadingProducts == false) {
        this.loadingProducts = true;
        fetch(this.apiUrlProducts)
          .then((r) => r.json())
          .then((json) => {
            this.loadingProducts = false;
            this.products = json;
            console.log(json);
          })
          .catch((error) => {
            this.loadingProducts = false;
            console.error(error);
            this.errorLoadingProducts = error;
          });
      }
    },
    readCategories() {
      if (this.loadingCategories == false) {
        this.loadingCategories = true;
        fetch(this.apiUrlCategories)
          .then((r) => r.json())
          .then((json) => {
            this.loadingCategories = false;
            this.categories = json;
            console.log(json);
          })
          .catch((error) => {
            this.loadingCategories = false;
            console.error(error);
            this.errorloadingCategories = error;
          });
      }
    },
  },
};
</script>
