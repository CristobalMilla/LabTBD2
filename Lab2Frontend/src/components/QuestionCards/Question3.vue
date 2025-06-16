<template>
  <v-card class="pa-4">
    <v-card-title>
      Distancia Total Recorrida (Último Mes)
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-select
          v-model="selectedId"
          :items="repartidores"
          item-value="repartidor_id"
          item-title="nombre"
          label="Seleccione Repartidor"
          outlined
          dense
          class="mb-4"
        />
      </v-form>

      <div v-if="loading" class="d-flex justify-center my-4">
        <v-progress-circular indeterminate color="primary"/>
      </div>

      <div v-else-if="error">
        <v-alert type="error" dense>{{ error }}</v-alert>
      </div>

      <div v-else-if="distance !== null">
        <span class="text-h6">
          ID {{ selectedId }} – {{ distance.toLocaleString() }} metros
        </span>
      </div>
      <div v-else class="text-body-2">
        Selecciona un repartidor para ver su distancia.
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getAllRepartidores, getDistanciaMensual } from '@/api/repartidores'

const repartidores = ref([])
const selectedId = ref(null)
const distance = ref(null)
const loading = ref(false)
const error = ref('')

async function fetchRepartidores() {
  try {
    repartidores.value = await getAllRepartidores()
  } catch (err) {
    console.error(err)
    error.value = 'No se pudieron cargar los repartidores.'
  }
}

async function fetchDistance(id) {
  if (id == null) {
    distance.value = null
    return
  }
  loading.value = true
  error.value = ''
  distance.value = null
  try {
    const metros = await getDistanciaMensual(id)
    distance.value = metros
  } catch (err) {
    console.error(err)
    error.value = 'Error al obtener la distancia mensual.'
  } finally {
    loading.value = false
  }
}

onMounted(async () => {
  await fetchRepartidores()
})

watch(selectedId, id => {
  fetchDistance(id)
})
</script>

<style scoped>
.v-card {
  max-width: 450px;
  margin: 0 auto;
}
</style>