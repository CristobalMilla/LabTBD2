<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import {getPedidosQueCruzanMasDe2Zonas} from '@/api/pedidos'

const pedidos = ref([])
const loading = ref(false)
const error = ref(null)

const getpedidos = async () => {
  try {
    const response = await getPedidosQueCruzanMasDe2Zonas()
    console.log(response)
  } catch (error) {
    console.error('Error fetching pedidos:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getpedidos()
})
</script>

<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Tareas Realizadas por Sector
      </v-card-title>

      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="error" class="text-center red--text">
          {{ error }}
        </div>

        <div v-else>
          <v-table v-if="pedidos.length > 0">
            <thead>
              <tr>
                <th class="text-center">Sector</th>
                <th class="text-center">Cantidad de Tareas Completadas</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(pedido, index) in pedidos" :key="index">
                <td class="text-center">{{ pedido.pedido_id }}</td>
                <td class="text-center">{{ pedido.cliente_id }}</td>
              </tr>
            </tbody>
          </v-table>
          <div v-else class="text-center pa-4">
            No hay tareas completadas en ningún sector.
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
    name: "Query1",
};
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