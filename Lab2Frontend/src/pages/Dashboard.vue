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
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import NotificationBadge from '@/components/NotificationBadge.vue'

import { ref } from 'vue'

const router = useRouter();
const authStore = useAuthStore();
const selected_query = ref("");

const questions = ref([
                  {name: "1. Encontrar los 5 puntos de entrega más cercanos a una farmacia o empresa asociada.", opt: 1},
                  {name: "2. Determinar si un cliente se encuentra dentro de una zona de cobertura.", opt: 2},
                  {name: "3. Calcular la distancia total recorrida por un repartidor en el último mes.", opt: 3},
                  {name: "4. Identificar el punto de entrega más lejano desde cada empresa asociada.", opt: 4},
                  {name: "5. Listar todos los pedidos cuya ruta estimada cruce más de 2 zonas de reparto.", opt: 5},
                  {name: "6. Determinar los clientes que están a más de 5km de cualquier empresa o farmacia.", opt: 6}
                ]);

const logout = () => {
  authStore.logout();
  router.push('/login');
};

const goToTaskDetails = () => {
  router.push('/taskdetails');
};
</script>

<template>
  <v-container fluid class="dashboard pa-0">
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title class="d-flex align-center">
        <v-btn variant="text" @click="$router.push('/home')" class="d-flex align-center">
          <v-icon size="24" class="mr-2">mdi-home</v-icon>
          Home
        </v-btn>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn variant="text" @click="goToTaskDetails" class="mr-2" prepend-icon="mdi-clipboard-text">
        Empresas
      </v-btn>
      <NotificationBadge class="mr-2" />
      <v-btn @click="logout" variant="text" prepend-icon="mdi-logout">
        Cerrar Sesión
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container class="py-8">
        <v-card class="mt-8" elevation="2">
          <v-card-title class="d-flex align-center pa-6 bg-grey-lighten-4">
            <v-icon size="28" color="primary" class="mr-3">mdi-database-search</v-icon>
            <span class="text-h5">Consultas Espaciales</span>
          </v-card-title>
          <v-card-text class="pa-6">
            <v-row>
              <v-col cols="12">
                <v-select
                  v-model="selected_query"
                  :items="questions"
                  item-title="name"
                  item-value="opt"
                  label="Seleccione una consulta"
                  variant="outlined"
                  density="comfortable"
                  class="mb-6"
                />
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="12">
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
          </v-card-text>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<style scoped>
.dashboard {
  min-height: 100vh;
  background: #f0f2f5;
}

</style>