<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="$router.push('/home')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>Gestión de Pedidos</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-card class="pa-4" elevation="2">
          <v-card-title class="text-h5">
            <v-icon left>mdi-truck-delivery</v-icon>
            Acciones de Pedidos
          </v-card-title>
          <v-card-text>
            <div class="d-flex flex-column ga-4 mt-4">
              <v-btn
                block
                color="primary"
                @click="$router.push('/pedidos/create')"
              >
                Crear Nuevo Pedido
              </v-btn>
              <!-- Aquí se podrían añadir más botones como "Ver Pedidos Activos", etc. -->
            </div>
          </v-card-text>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<script>
export default {
  name: 'PedidosPage',
};
</script>