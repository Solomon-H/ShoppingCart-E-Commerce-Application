<template>
  <div id="app">
    <header>
      <div class="logo">
        <img src="https://via.placeholder.com/200x100.jpg" alt="Logo">
      </div>
      <nav>
        <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link class="link" v-bind:to="{ name: 'cart' }" exact>Cart</router-link>
        <router-link class="link" v-bind:to="{ name: 'logout' }" v-if="$store.state.token" exact>Logout</router-link>
        <router-link class="link" v-bind:to="{ name: 'login' }" v-else exact>Login</router-link>
      </nav>
      <span class="banner">{{ this.$store.state.messageBanner }}</span>
    </header>
    <main>
      <router-view />
    </main>
    <footer>
      <p>© 2022 All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
  #app {
    display: flex;
    flex-direction: column;
    justify-content: center;
    border: 0.25px solid black;
    width: 100%;
    min-height: 100vh;
  }

  header {
    background-color: #333; 
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    position: sticky;
    top: 0;
    z-index: 999;
  }

  .logo img {
    width: 100px;
  }

  nav {
    display: flex;
    gap: 20px;
  }

  .link {
    text-decoration: none;
    color: white; 
    font-weight: 700;
    transition: color 0.3s ease; 
  }

  .link:hover {
    color: #ff5722; 
  }

  main {
    padding: 20px;
  }

  footer {
    background-color: #333; 
    padding: 10px 20px;
    position: sticky;
    bottom: 0;
  }

  footer p {
    margin: 0;
    color: white; 
  }
</style>
