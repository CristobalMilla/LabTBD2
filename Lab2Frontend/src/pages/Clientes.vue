<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="$router.push('/home')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>Gestión</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-row justify="center" align="center" class="gap-4">
          <!-- Botones de Cliente -->
          <v-btn
            color="primary"
            large
            @click="$router.push('/clientes/create')"
          >
            Crear Cliente
          </v-btn>
          <v-btn
            color="secondary"
            large
            @click="editCliente"
          >
            Editar Cliente
          </v-btn>
          <!-- Botones de Opinión -->
          <v-btn
            color="info"
            large
            @click="createOpinion"
          >
            Crear Opinión
          </v-btn>
          <v-btn
            color="warning"
            large
            @click="editOpinion"
          >
            Editar Opinión
          </v-btn>
        </v-row>
      </v-container>
    </v-main>
  </v-container>
</template>

<script>
export default {
  methods: {
    editCliente() {
      const id = prompt('Ingrese el ID del cliente a editar:');
      if (id && /^\d+$/.test(id.trim())) {
        this.$router.push(`/clientes/edit/${id.trim()}`);
      }
    },
    createOpinion() {
      this.$router.push('/opiniones/create');
    },
    editOpinion() {
      const id = prompt('Ingrese el ID de la opinión a editar:');
      if (id && /^\d+$/.test(id.trim())) {
        this.$router.push(`/opiniones/edit/${id.trim()}`);
      }
    }
  }
}
</script>