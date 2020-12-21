<template>
  <div>
    <nav class="navbar-links">
      <div>
        <ul>
          <li>
            <router-link
              to="/catalog"
              class="nav-link"
              active-class="active-nav-link"
              >Catalog</router-link
            >
          </li>
        </ul>
        <ul>
          <li>
            <router-link
              to="/order"
              class="nav-link"
              active-class="active-nav-link"
              >Shopping Cart</router-link
            >
          </li>
        </ul>
        <ul>
          <li>
            <router-link
              to="/account"
              class="nav-link"
              active-class="active-nav-link"
              >Account</router-link
            >
          </li>
        </ul>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {};
</script>
