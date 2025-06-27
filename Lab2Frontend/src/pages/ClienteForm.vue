<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="goBack"><v-icon>mdi-arrow-left</v-icon></v-btn>
      <v-toolbar-title>{{ isEdit ? 'Editar' : 'Crear' }} Cliente</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-card elevation="2" class="pa-6">
          <v-form ref="form" lazy-validation>
            <v-text-field v-model="formData.nombre"    label="Nombre"   required/>
            <v-text-field v-model="formData.direccion" label="Dirección" required/>
            <v-text-field v-model="formData.email"     label="Email"     required/>
            <v-text-field v-model="formData.telefono"  label="Teléfono"  required/>
            <v-divider class="my-4"/>
            <p><strong>Ubicación WKT:</strong> {{ formData.ubicacion || '—' }}</p>
            <div id="cliente-map" class="map-container"></div>
          </v-form>
          <v-card-actions class="mt-6">
            <v-spacer/>
            <v-btn color="primary" :loading="loading" @click="save">
              {{ isEdit ? 'Actualizar' : 'Crear' }}
            </v-btn>
            <v-btn text @click="goBack">Cancelar</v-btn>
          </v-card-actions>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'
import { getClienteById, createCliente, updateCliente } from '@/api/clientes'
import { getAllVertices } from '@/api/vertices'

const route = useRoute()
const router = useRouter()
const id = route.params.id
const isEdit = Boolean(id)

const loading = ref(false)
const form = ref(null) // Referencia para el componente v-form
const formData = ref({ // Referencia para los datos del formulario
  nombre: '',
  direccion: '',
  email: '',
  telefono: '',
  ubicacion: null
})

let map, layer, marker

function goBack() {
  router.push('/clientes')
}

function initMap() {
  map = L.map('cliente-map').setView([-33.455, -70.685], 13)
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map)
  layer = L.layerGroup().addTo(map)
  map.on('zoomend', drawVertices)
  drawVertices()
}

async function drawVertices() {
  layer.clearLayers()
  const verts = await getAllVertices()
  verts.forEach(v => {
    if (!v.theGeomWkt) return
    const m = v.theGeomWkt.match(/POINT\(\s*(-?\d+\.\d+)\s+(-?\d+\.\d+)\s*\)/)
    if (!m) return
    const latlng = [parseFloat(m[2]), parseFloat(m[1])]
    L.circleMarker(latlng, { radius: 4, color: 'blue' })
     .on('click', () => select(latlng, v.theGeomWkt))
     .addTo(layer)
  })
  if (formData.value.ubicacion && !marker) {
    const m = formData.value.ubicacion.match(/POINT\(\s*(-?\d+\.\d+)\s+(-?\d+\.\d+)\s*\)/)
    select([parseFloat(m[2]), parseFloat(m[1])], formData.value.ubicacion)
  }
}

function select(latlng, wkt) {
  formData.value.ubicacion = wkt // Usar formData
  marker && map.removeLayer(marker)
  marker = L.marker(latlng).addTo(map)
  map.setView(latlng, 15)
}

async function load() {
  if (!isEdit) return
  loading.value = true
  try {
    const c = await getClienteById(id)
    // Asignar directamente al .value para asegurar la reactividad
    formData.value = {
      nombre:    c.nombre,
      direccion: c.direccion,
      email:     c.email,
      telefono:  c.telefono,
      ubicacion: c.ubicacion
    }
  } finally { loading.value = false }
}

async function save() {
  const { valid } = await form.value.validate() // Validar usando la ref del v-form
  if (!valid) return

  if (loading.value) return
  loading.value = true
  try {
    if (isEdit) await updateCliente({ ...formData.value, cliente_id: id })
    else       await createCliente(formData.value)
    goBack()
  } catch (e) {
    console.error(e)
    alert('Error al guardar')
  } finally { loading.value = false }
}

onMounted(async () => {
  await load()
  initMap()
})
</script>

<style scoped>
.map-container, #cliente-map {
  width: 100%; height: 300px; margin-top: 8px; border:1px solid #ccc;
}
</style>