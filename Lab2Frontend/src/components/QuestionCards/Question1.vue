<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const tareasPorSector = ref([])
const loading = ref(false)
const error = ref(null)

const getTareasPorSector = async () => {
  try {
    loading.value = true
    error.value = null
    const usuario = JSON.parse(localStorage.getItem("user"))
    
    if (!usuario?.token) {
      throw new Error("No se encontró el token de autenticación")
    }

    if (!usuario?.id_usuario) {
      throw new Error("No se encontró el ID del usuario")
    }
    
    const response = await axios.get(`http://localhost:8000/api/tareas/porSector/${usuario.id_usuario}`, {
      headers: {
        Authorization: `Bearer ${usuario.token}`,
      },
    })
    
    if (response.data && Array.isArray(response.data)) {
      tareasPorSector.value = response.data.sort((a, b) => b.tareas_hechas - a.tareas_hechas)
    } else {
      error.value = "No se encontraron datos de tareas por sector"
    }
  } catch (err) {
    console.error('Error:', err)
    let mensajeError = 'Error al obtener las tareas por sector'
    
    if (err.response) {
      switch (err.response.status) {
        case 404:
          mensajeError = 'No se encontró el recurso solicitado. Error 404.'
          break
        case 401:
          mensajeError = 'No está autorizado para realizar esta acción'
          break
        case 403:
          mensajeError = 'No tiene permisos para realizar esta acción'
          break
        case 500:
          mensajeError = 'Error interno del servidor. Error 500.'
          break
        default:
          mensajeError = err.response.data?.message || 'Error al procesar la solicitud'
      }
    } else if (err.request) {
      mensajeError = 'No se pudo conectar con el servidor'
    } else {
      mensajeError = err.message || 'Error desconocido'
    }
    
    error.value = mensajeError
    tareasPorSector.value = []
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getTareasPorSector()
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
          <v-table v-if="tareasPorSector.length > 0">
            <thead>
              <tr>
                <th class="text-center">Sector</th>
                <th class="text-center">Cantidad de Tareas Completadas</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(tarea, index) in tareasPorSector" :key="index">
                <td class="text-center">{{ tarea.id_sector }}</td>
                <td class="text-center">{{ tarea.tareas_hechas }}</td>
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