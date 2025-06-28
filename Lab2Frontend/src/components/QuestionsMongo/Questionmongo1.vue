<script setup>
import {onMounted, ref} from 'vue'
import { getPromedioPuntuacionXEmpresa } from '@/api/opiniones';

const loading = ref(true)
const errorFetching = ref(false)
const promediosXempresa = ref([])

const getPromedios = async () => {
  try {
    promediosXempresa.value = await getPromedioPuntuacionXEmpresa()
  } catch (error) {
    console.error('Error fetching pedidos:', error)
    errorFetching.value = true
  } finally {
    loading.value = false
  }
};

onMounted(async () => {
  await getPromedios();
})

</script>

<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Promedio de puntuación de cada empresa (de opiniones dejadas por los clientes)
      </v-card-title>
      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="errorFetching" class="text-center red--text">
          Error al cargar los promedios
        </div>

        <div v-else>
          <div>
            <v-table v-if="promediosXempresa.length > 0">
              <thead>
                <tr>
                  <th class="text-center">Id</th>
                  <th class="text-center">Empresa</th>
                  <th class="text-center">Promedio de Puntuación</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(promedio, index) in promediosXempresa" :key="index">
                    <td class="text-center">{{ promedio.empresa_id }}</td>
                    <td class="text-center">{{ promedio.nombre_empresa }}</td>
                    <td class="text-center">{{ promedio.promedio }}</td>
                </tr>
              </tbody>
            </v-table>
            <div v-else class="text-center pa-4">
              No hay opiniones en ninguna empresa.
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

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