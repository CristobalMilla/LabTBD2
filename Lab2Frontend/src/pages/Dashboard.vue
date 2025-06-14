<script setup>
import Query1 from '../components/QuestionCards/Question1.vue'
import Query2 from '../components/QuestionCards/Question2.vue'
import Query3 from '../components/QuestionCards/Question3.vue'
import Query4 from '../components/QuestionCards/Question4.vue'
import Query5 from '../components/QuestionCards/Question5.vue'
import Query6 from '../components/QuestionCards/Question6.vue'
import Query7 from '../components/QuestionCards/Question7.vue'
import Query8 from '../components/QuestionCards/Question8.vue'
import Query9 from '../components/QuestionCards/Question9.vue'
import { getAllTasksPerUserPerSector } from "@/api/tasks";
import { getSectorMostCompletedByUser } from "@/api/tasks";
import { getAverageCompletedDistance } from "@/api/tasks";
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import NotificationBadge from '@/components/NotificationBadge.vue'

import { ref } from 'vue'

const router = useRouter();
const authStore = useAuthStore();
const selected_query = ref("");

const questions = ref([
                  {name: "1. ¿Cuántas tareas ha hecho el usuario por sector?", opt: 1},
                  {name: "2. ¿Cuál es la tarea más cercana al usuario (que esté pendiente)?", opt: 2},
                  {name: "3. ¿Cuál es el sector con más tareas completadas en un radio de 2 kilómetros del usuario?", opt: 3},
                  {name: "4. ¿Cuál es el promedio de distancia de las tareas completadas respecto a la ubicación del usuario?", opt: 4},
                  {name: "5. ¿En qué sectores geográficos se concentran la mayoría de las tareas pendientes? (utilizando agrupación espacial)", opt: 5},
                  {name: "6. ¿Cuál es la tarea pendiente más cercana a la ubicación del usuario?", opt: 6},
                  {name: "7. ¿Cuántas tareas ha realizado cada usuario por sector?", opt: 7},
                  {name: "8. ¿Cuál es el sector con más tareas completadas dentro de un radio de 5 km desde la ubicación del usuario?", opt: 8},
                  {name: "9. ¿Cuál es el promedio de distancia entre las tareas completadas y el punto registrado del usuario?", opt: 9}
                  ]);

const tasksBySectorAndUser = ref([]); // Nuevo estado para almacenar la consulta de la pregunta 7
const sectorMostCompleted = ref(null); // Estado para almacenar el sector de la consulta 8
const averageCompletedDistance = ref(null); // Estado para almacenar el promedio de la pregunta 9

const logout = () => {
  authStore.logout();
  router.push('/login');
};

const goToTaskDetails = () => {
  router.push('/taskdetails');
};
</script>

<template>
  <v-container>
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title class="d-flex align-center">
        <v-btn variant="text" @click="$router.push('/home')" class="d-flex align-center">
          <v-icon size="24" class="mr-2">mdi-view-dashboard</v-icon>
          Home
        </v-btn>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn variant="text" @click="goToTaskDetails" class="mr-2" prepend-icon="mdi-clipboard-text">
        Detalles de Tareas
      </v-btn>
      <NotificationBadge class="mr-2" />
      <v-btn @click="logout" variant="text" prepend-icon="mdi-logout">
        Cerrar Sesión
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-row>
        <v-col cols="12" md="6">
          <h1>Consultas</h1>

          <v-select
            v-model="selected_query"
            :items="questions"
            item-title="name"
            item-value="opt"
            label="Consultas"
            variant="outlined"
            density="comfortable"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="10">
          <div v-if="selected_query == 1">
            <Query1/>
          </div>
          <div v-if="selected_query == 2">
            <Query2/>
          </div>
          <div v-if="selected_query == 3">
            <Query3/>
          </div>
          <div v-if="selected_query == 4">
            <Query4/>
          </div>
          <div v-if="selected_query == 5">
            <Query5/>
          </div>
          <div v-if="selected_query == 6">
            <Query6/>
          </div>
          <div v-if="selected_query == 7">
            <Query7/>
          </div>
          <div v-if="selected_query == 8">
            <Query8/>
          </div>
          <div v-if="selected_query == 9">
            <Query9/>
          </div>
        </v-col>
      </v-row>
    </v-main>
  </v-container>
</template>

<style>
label {
  font-family: sans-serif;
  font-size: 1rem;
  padding-right: 10px;
}

select {
  border: 1px solid black;
  font-size: 0.9rem;
  padding: 2px 5px;
}
</style>