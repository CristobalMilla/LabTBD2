<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Concentración de Tareas Pendientes por Comuna
      </v-card-title>

      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="error" class="text-center red--text">
          {{ error }}
        </div>

        <div v-else>
          <v-table v-if="comunas.length > 0">
            <thead>
              <tr>
                <th class="text-center">Comuna</th>
                <th class="text-center">Cantidad de Tareas Pendientes</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(comuna, index) in comunas" :key="index">
                <td class="text-center">{{ comuna.comuna }}</td>
                <td class="text-center">{{ comuna.cantidad }}</td>
              </tr>
            </tbody>
          </v-table>
          <div v-else class="text-center pa-4">
            No hay tareas pendientes en ninguna comuna.
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

const comunas = ref([])
const loading = ref(false)
const error = ref(null)

const getTareasPorComuna = async () => {
  try {
    loading.value = true
    error.value = null
    const usuario = JSON.parse(localStorage.getItem("user"))
    
    if (!usuario?.token) {
      throw new Error("No se encontró el token de autenticación")
    }
    
    const response = await axios.get('http://localhost:8000/api/tareas/comunas-tareas-pendientes', {
      headers: {
        Authorization: `Bearer ${usuario.token}`,
      },
    })
    
    if (response.data && Array.isArray(response.data)) {
      comunas.value = response.data.sort((a, b) => b.cantidad - a.cantidad)
    } else {
      error.value = "No se encontraron datos de tareas por comuna"
    }
  } catch (err) {
    console.error('Error:', err)
    let mensajeError = 'Error al obtener las tareas por comuna'
    
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
    comunas.value = []
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getTareasPorComuna()
})
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