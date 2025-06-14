<script setup>

import { ref, onMounted } from 'vue';
import { getAverageCompletedDistance } from "@/api/tasks";

const averageDistance = ref(null);
onMounted(async () => {
  // Pregunta 8
  const response = await getAverageCompletedDistance();
  averageDistance.value = response;
});

</script>

<template>
  <div>
    <v-card class="average-distance-card pa-6">
      <v-card-title class="d-flex align-center">
        <v-icon size="28" color="info" class="mr-3">mdi-ruler</v-icon>
        <span class="text-h5">Promedio de Distancia a Tareas Completadas</span>
      </v-card-title>
      <v-card-text v-if="averageDistance !== null">
        <p>El promedio de distancia a las tareas completadas es: <strong>{{ averageDistance.toFixed(2) }} km</strong></p>
      </v-card-text>
      <v-card-text v-else>
        <p>No hay datos disponibles para calcular el promedio.</p>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "Question9",
  props: {
    averageDistance: {
      type: Number,
      default: null,
    },
  },
};
</script>
