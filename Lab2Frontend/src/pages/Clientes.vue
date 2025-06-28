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
        <v-row>
          <!-- Tarjeta de Gestión de Clientes -->
          <v-col cols="12" md="6">
            <v-card class="pa-4" elevation="2">
              <v-card-title class="text-h5">
                <v-icon left>mdi-account-group</v-icon>
                Gestión de Clientes
              </v-card-title>
              <v-card-text>
                <div class="d-flex flex-column ga-4 mt-4">
                  <v-btn
                    block
                    color="primary"
                    @click="$router.push('/clientes/create')"
                  >
                    Crear Nuevo Cliente
                  </v-btn>
                  <v-btn
                    block
                    color="secondary"
                    @click="editCliente"
                  >
                    Editar Cliente Existente
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>
          </v-col>

          <!-- Tarjeta de Gestión de Opiniones -->
          <v-col cols="12" md="6">
            <v-card class="pa-4" elevation="2">
              <v-card-title class="text-h5">
                <v-icon left>mdi-comment-quote</v-icon>
                Gestión de Opiniones
              </v-card-title>
              <v-card-text>
                <div class="d-flex flex-column ga-4 mt-4">
                  <v-btn
                    block
                    color="info"
                    @click="createOpinion"
                  >
                    Crear Nueva Opinión
                  </v-btn>
                  <v-btn
                    block
                    color="warning"
                    @click="editOpinion"
                  >
                    Editar Opinión Existente
                  </v-btn>
                  <v-divider class="my-2"></v-divider>
                  <v-btn
                    block
                    color="success"
                    @click="goToStats"
                  >
                    Ver Estadísticas de Opiniones
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
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
    },
    goToStats() {
      this.$router.push('/opiniones/stats');
    }
  }
}
</script>