<template>
  <v-card class="empresa-card pa-4">
    <v-card-title>
      {{ empresa.nombre }}
      <v-spacer></v-spacer>
      <v-btn icon @click="editEmpresa">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <div><strong>Dirección:</strong> {{ empresa.direccion }}</div>
      <div><strong>Tipo Servicio:</strong> {{ empresa.tipoServicio || empresa.tipo_servicio }}</div>
      <div>
        <strong>Ubicación:</strong> 
        <span v-if="empresa.ubicacion">{{ empresa.ubicacion }}</span>
        <span v-else>Sin Ubicación</span>
      </div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { useRouter } from 'vue-router'
const props = defineProps({
  empresa: Object
})
const router = useRouter()
function editEmpresa(){
  router.push(`/empresas/edit/${props.empresa.empresaId || props.empresa.empresa_id}`)
}
</script>

<style scoped>
.empresa-card {
  margin-bottom: 16px;
}
</style>