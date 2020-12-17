<template>
  <div>
    <h1 style="text-align: center;">Catalog</h1>
    <h3>Products</h3>
    <div
      v-for="product in this.$store.state.products"
      :key="product.id"
      class=""
    >
      <div class="">
        {{ product.name }}
      </div>
      <div class="">
        {{ product.description }}
      </div>
      <div class="">
        {{ product.image }}
      </div>
      <div class="">
        {{ product.price }}
      </div>
      <br />
    </div>
    <h3>Categories</h3>
    <div
      v-for="category in this.$store.state.categories"
      :key="category.id"
      class=""
    >
      <div class="">
        {{ category.name }}
      </div>
      <div
        style="padding-left: 20px;"
        v-for="subCategory in category.subCategories"
        :key="subCategory.id"
        class=""
      >
        <div class="">
          {{ subCategory.name }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      apiUrlProducts:
        "http://localhost:9083/CustomerOrderServicesWeb/jaxrs/Product/?categoryId=2",
      apiUrlCategories:
        "http://localhost/CustomerOrderServicesWeb/jaxrs/Category",
      loadingProducts: false,
      loadingCategories: false,
      errorLoadingProducts: "",
      errorLoadingCategories: "",
    };
  },
  created() {
    this.readProducts();
    this.readCategories();
  },
  methods: {
    readProducts() {
      if (this.loadingProducts == false) {
        this.loadingProducts = true;
        fetch(this.apiUrlProducts)
          .then((r) => r.json())
          .then((json) => {
            this.loadingProducts = false;
            this.$store.commit("addProducts", json);
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
            this.$store.commit("addCategories", json);
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
