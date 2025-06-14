<script setup>

import { ref, onMounted } from 'vue';
//Pregunta 7
import { getAllTasksPerUserPerSector } from "@/api/tasks";

const tasksBySectorAndUser = ref([]);

onMounted(async () => {
  // Pregunta 7
  const response = await getAllTasksPerUserPerSector();
  tasksBySectorAndUser.value = response;
});

</script>

<template>
  <v-container>
    <v-card class="sector-tasks-section mt-8" elevation="2">
      <v-card-title class="d-flex align-center pa-6 bg-grey-lighten-4">
        <v-icon size="28" color="primary" class="mr-3">mdi-format-list-bulleted</v-icon>
        <span class="text-h5">Tareas por Sector</span>
      </v-card-title>
      <v-card-text>
      <div>
        <v-list v-if="tasksBySectorAndUser.length > 0">
          <v-list-item
            v-for="task in tasksBySectorAndUser"
            :key="task.id_sector"
            class="py-4 px-6"
            >
            <v-list-item-content>
              <v-list-item-title>Id del sector: {{ task.id_sector }}</v-list-item-title>
              <v-list-item-subtitle>Id del usuario: {{ task.id_usuario }}</v-list-item-subtitle>
              <v-list-item-subtitle>Numero de tareas: {{ task.numero_de_tareas }}</v-list-item-subtitle>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <div v-else class="text-center text-grey pa-6">
          No hay tareas completadas.
        </div>
      </div>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "Question7",
};
</script>
