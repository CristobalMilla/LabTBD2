<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Listar las opiniones que contengan palabras clave como 'demora' o 'error'
      </v-card-title>

      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="error" class="text-center red--text">
          error
        </div>

        <div v-else>
          <div>
            <v-table v-if="opiniones.length > 0">
              <thead>
              <tr>
                <th class="text-center">Cliente</th>
                <th class="text-center">Comentario</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="(opinion, index) in opiniones" :key="index">
                <td class="text-center">{{ opinion.cliente }}</td>
                <td class="text-center">{{ opinion.comentarios }}</td>
                <td class="text-center">
                </td>
              </tr>
              </tbody>
            </v-table>
            <div v-else class="text-center pa-4">
              No hay opiniones que cumplan con el filtro.
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { getByClienteId } from "@/api/zonasCobertura";
import { getClientes } from "@/api/clientes";
import wellknown from "wellknown";
import L from "leaflet";
import {getOpinionesQuery2} from "@/api/opiniones.js";

const opiniones = ref([]);
const loading = ref(true)
const error = ref(false)

const fetchOpiniones = async () => {
  try {
    opiniones.value = await getOpinionesQuery2();
  } catch (error) {
    console.error('Error fetching opiniones:', error)
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await fetchOpiniones();

});

</script>

<style scoped>
.v-table {
  width: 100%;
  margin-top: 1rem;
}

th, td {
  padding: 12px;
}

th {
  background-color: #f5f5f5;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #fafafa;
}

tr:hover {
  background-color: #f0f0f0;
}
</style>