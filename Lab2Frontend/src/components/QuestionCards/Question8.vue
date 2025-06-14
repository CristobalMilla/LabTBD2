<script setup>

import { ref, onMounted } from 'vue';
//Pregunta 8
import { getSectorMostCompletedByUser } from "@/api/tasks";
const sectorMostCompleted = ref(null);
onMounted(async () => {
  // Pregunta 8
  const response = await getSectorMostCompletedByUser();
  sectorMostCompleted.value = response;
});
</script>



<template>
  <div>
    <v-card class="sector-completed-card pa-6">
      <v-card-title class="d-flex align-center">
        <v-icon size="28" color="success" class="mr-3">mdi-trophy</v-icon>
        <span class="text-h5">Sector cercano con Más Tareas Completadas</span>
      </v-card-title>
      <v-card-text v-if="sectorMostCompleted">
        <p>El ID del sector con más tareas completadas cerca de ti es: <strong>{{ sectorMostCompleted }}</strong></p>
      </v-card-text>
      <v-card-text v-else>
        <p>No se encontró un sector cercano con tareas completadas.</p>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "Question8",
  props: {
    sectorId: {
      type: Number,
      default: null,
    },
  },
};
</script>
