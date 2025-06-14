<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Tarea Pendiente Más Cercana
      </v-card-title>

      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="error" class="text-center red--text">
          {{ error }}
        </div>

        <div v-else-if="tarea" class="mt-4">
          <v-card variant="outlined">
            <v-card-item>
              <v-card-title>{{ tarea.titulo }}</v-card-title>
              <v-card-subtitle>
                Sector: {{ tarea.id_sector }}
              </v-card-subtitle>
            </v-card-item>

            <v-card-text>
              <p class="mb-2"><strong>Descripción:</strong></p>
              <p class="text-body-1">{{ tarea.descripcion }}</p>
              
              <v-divider class="my-3"></v-divider>
              
              <div class="d-flex align-center justify-space-between">
                <span class="text-caption">
                  <strong>Fecha de vencimiento:</strong> {{ formatDate(tarea.fecha_vencimiento) }}
                </span>
                <v-chip
                  :color="tarea.estado === 'Pendiente' ? 'warning' : 'success'"
                  text-color="white"
                >
                  {{ tarea.estado }}
                </v-chip>
              </div>
            </v-card-text>
          </v-card>
        </div>
        
        <div v-else-if="!loading" class="text-center pa-4">
          No se encontró ninguna tarea pendiente cercana.
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const tarea = ref(null)
const loading = ref(false)
const error = ref(null)

const formatDate = (dateString) => {
  if (!dateString) return 'No especificada'
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

const buscarTareaCercana = async () => {
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
    
    const response = await axios.get(`http://localhost:8000/api/tareas/pendiente-mas-cercana/${usuario.id_usuario}`, {
      headers: {
        Authorization: `Bearer ${usuario.token}`,
      },
    })
    
    if (response.data) {
      tarea.value = response.data
    } else {
      tarea.value = null
      error.value = "No se encontró ninguna tarea pendiente cercana"
    }
  } catch (err) {
    console.error('Error:', err)
    let mensajeError = 'Error al buscar la tarea más cercana'
    
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
    tarea.value = null
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  buscarTareaCercana()
})
</script>

<style scoped>
.v-card-text p {
  margin: 0;
}
</style>
