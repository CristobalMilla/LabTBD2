<template>
  <div class="layout">
    <header>
      <h1>Mi Aplicación</h1>
      <nav>
        <router-link to="/dashboard">Inicio</router-link>
        <button @click="logout">Cerrar sesión</button>
      </nav>
    </header>
    <main>
      <slot></slot>
    </main>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.removeItem("jwtToken");
      this.$router.push("/login");
    },
  },
};
</script>

<style>
.layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}
header {
  background: #333;
  color: white;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
main {
  flex: 1;
  padding: 1rem;
}
</style>
