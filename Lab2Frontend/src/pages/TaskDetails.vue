<template>
  <v-container>
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title class="d-flex align-center">
        <v-btn variant="text" @click="$router.push('/home')" class="d-flex align-center">
          <v-icon size="24" class="mr-2">mdi-home</v-icon>
          Home
        </v-btn>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn variant="text" @click="logout" prepend-icon="mdi-logout">
        Cerrar Sesión
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container class="details-page">
        <v-row class="mb-6">
          <v-col cols="12" class="d-flex justify-space-between align-center">
            <h1 class="text-h4">Lista de Empresas</h1>
          </v-col>
        </v-row>

        <!-- Loading State -->
        <v-row v-if="loading">
          <v-col cols="12" class="d-flex justify-center align-center pa-8">
            <v-progress-circular indeterminate color="primary" size="64"></v-progress-circular>
          </v-col>
        </v-row>

        <!-- No Empresas -->
        <v-row v-else-if="!loading && empresas.length === 0">
          <v-col cols="12">
            <v-alert type="info" variant="tonal" class="text-center">
              No hay empresas disponibles
            </v-alert>
          </v-col>
        </v-row>

        <!-- Lista de Empresas -->
        <v-row v-else>
          <v-col cols="12">
            <div class="entity-list">
              <EmpresaCard
                v-for="empresa in empresas"
                :key="empresa.empresaId || empresa.empresa_id"
                :empresa="empresa"
                @empresa-updated="handleEmpresaUpdated"
              />
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import EmpresaCard from '@/components/EmpresaCard.vue'
import { getAllEmpresas } from '@/api/empresas'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const empresas = ref([])
const loading = ref(false)

async function fetchEmpresas() {
  loading.value = true
  try {
    empresas.value = await getAllEmpresas()
  } catch (error) {
    console.error('Error fetching empresas:', error)
  } finally {
    loading.value = false
  }
}

function handleEmpresaUpdated(updatedEmpresa) {
  const index = empresas.value.findIndex(e =>
    e.empresaId === updatedEmpresa.empresaId || e.empresa_id === updatedEmpresa.empresa_id
  )
  if (index !== -1) {
    empresas.value.splice(index, 1, updatedEmpresa)
  }
}

function logout() {
  authStore.logout()
  router.push('/login')
}

onMounted(() => {
  fetchEmpresas()
})
</script>

<style scoped>
.details-page {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 20px;
}
.entity-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>