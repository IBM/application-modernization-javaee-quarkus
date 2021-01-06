<template>
  <div>
    <br />
    <br />
    <br />
    <br />
    <div style="margin-left: 20px">
      <h3>Account</h3>
    </div>
    <mwc-list class="mdc-list" id="mdclist">
      <mwc-list-item class="mwc-list-item" twoline hasMeta>
        <span>To be done</span>
      </mwc-list-item>
    </mwc-list>
  </div>
</template>
<script>
export default {};
</script>
<style scoped>
.mwc-list-item {
  --mdc-list-item-graphic-size: 80px;
}
.mdc-list {
  max-width: 500px;
  width: 500px;
}
</style>
