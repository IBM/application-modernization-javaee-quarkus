
<script>
export default {
  props: ["nik"],
  sendOrder(productId) {
    console.log("sendOrder invoked " + productId)
  }
};
</script>
