<template>
  <v-card>
    <v-card-title>
      Contar cuántos pedidos tienen más de 3 cambios de estado en menos de 10 minutos
    </v-card-title>
    <v-card-text>
      <v-btn color="primary" @click="fetchNumeroPedidos" :loading="loading" class="mb-4">
        Consultar
      </v-btn>
      <div v-if="error" class="red--text mb-2">{{ error }}</div>
      <div v-if="numeroPedidos !== null">
        <v-alert type="success" variant="tonal">
          Número de pedidos: <strong>{{ numeroPedidos }}</strong>
        </v-alert>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref } from "vue";
import { getNumeroPedidos3Cambios } from "@/api/pedidos";

const numeroPedidos = ref(null);
const loading = ref(false);
const error = ref("");

const fetchNumeroPedidos = async () => {
  loading.value = true;
  error.value = "";
  try {
    numeroPedidos.value = await getNumeroPedidos3Cambios();
  } catch (e) {
    error.value = "Error al consultar el número de pedidos.";
    numeroPedidos.value = null;
  } finally {
    loading.value = false;
  }
};
</script>