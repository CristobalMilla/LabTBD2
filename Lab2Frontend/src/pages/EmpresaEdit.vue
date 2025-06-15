<template>
  <v-container fluid>
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title>
        Editar Empresa
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn variant="text" @click="goBack">
        Volver
      </v-btn>
    </v-app-bar>
    
    <v-main>
      <v-container class="py-8">
        <v-card elevation="2" class="pa-6">
          <v-card-title>
            <span class="text-h5">
              Editar Información de {{ editForm.nombre }}
            </span>
          </v-card-title>
          <v-card-text>
            <v-form ref="formRef" lazy-validation>
              <v-text-field label="Nombre" v-model="editForm.nombre" required></v-text-field>
              <v-textarea label="Dirección" v-model="editForm.direccion" required></v-textarea>
              <v-text-field label="Tipo Servicio" v-model="editForm.tipoServicio" required></v-text-field>
              <v-divider class="my-4"></v-divider>
              <div>
                <div>
                  Ubicación Actual: 
                  <strong>{{ editForm.ubicacion || 'No especificada' }}</strong>
                </div>
                <div id="edit-map" class="map-container"></div>
                <small>
                  Haz clic en un vértice en el mapa para seleccionar la ubicación de la empresa.
                </small>
              </div>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" @click="saveChanges" :loading="loading">
              Guardar Cambios
            </v-btn>
            <v-btn color="secondary" @click="goBack" text>
              Cancelar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getEmpresaById, updateEmpresa } from '@/api/empresas'
import { getAllVertices } from '@/api/vertices'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

const route = useRoute()
const router = useRouter()
const empresaId = route.params.id

const loading = ref(false)
const editForm = ref({
  empresa_id: null,
  nombre: '',
  direccion: '',
  tipoServicio: '',   // <-- camelCase
  ubicacion: null     // se espera que sea un WKT (por ejemplo, "POINT(-70.685 -33.455)")
})

const formRef = ref(null)
const map = ref(null)
const verticesLayer = ref(null)
const vertices = ref([])
const selectedMarker = ref(null)

function initMap() {
  if(map.value) map.value.remove()
  map.value = L.map('edit-map').setView([-33.455, -70.685], 13)
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
  }).addTo(map.value)
  verticesLayer.value = L.layerGroup().addTo(map.value)
  drawVertices()
  if(editForm.value.ubicacion) {
    const point = parsePointWKT(editForm.value.ubicacion)
    if(point) {
      selectedMarker.value = L.marker(point, {
        icon: L.icon({
          iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
          iconSize: [25, 41]
        })
      }).addTo(map.value).bindPopup('Ubicación seleccionada').openPopup()
      map.value.setView(point, 16)
    }
  }
}

async function drawVertices() {
  try {
    vertices.value = await getAllVertices()
    verticesLayer.value.clearLayers()
    vertices.value.forEach(vertex => {
      if (vertex.theGeomWkt) {
        const point = parsePointWKT(vertex.theGeomWkt)
        if(point){
          const marker = L.circleMarker(point, {
            radius: 3,
            color: 'blue',
            fillColor: '#30f',
            fillOpacity: 0.5
          }).addTo(verticesLayer.value)
          marker.on('click', () => {
            handleVertexSelection(vertex, point)
          })
        }
      }
    })
  } catch (error) {
    console.error("Error fetching vertices:", error)
  }
}

function parsePointWKT(wkt) {
  if(!wkt || !wkt.startsWith("POINT")) return null
  const match = wkt.match(/POINT\s*\(\s*(-?\d+\.?\d*)\s+(-?\d+\.?\d*)\s*\)/)
  if (match && match.length === 3) {
    return [parseFloat(match[2]), parseFloat(match[1])] // Leaflet utiliza [lat, lng]
  }
  return null
}

function handleVertexSelection(vertex, point) {
  editForm.value.ubicacion = vertex.theGeomWkt
  if(selectedMarker.value) {
    map.value.removeLayer(selectedMarker.value)
  }
  selectedMarker.value = L.marker(point, {
    icon: L.icon({
      iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
      shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41]
    })
  }).addTo(map.value).bindPopup("Ubicación Seleccionada").openPopup()
}

async function fetchEmpresa() {
  loading.value = true
  try {
    const empresa = await getEmpresaById(empresaId)
    editForm.value = {
      empresa_id: empresa.empresaId || empresa.empresa_id,
      nombre: empresa.nombre,
      direccion: empresa.direccion,
      // backend te devuelve JSON con clave "tipoServicio" (o "tipo_servicio"):
      tipoServicio: empresa.tipoServicio ?? empresa.tipo_servicio,
      ubicacion: empresa.ubicacion  // WKT en formato "POINT(lng lat)"
    }
  } catch (error) {
    console.error("Error fetching empresa:", error)
  } finally {
    loading.value = false
  }
}

async function saveChanges() {
  loading.value = true
  try {
    const payload = {
      nombre:       editForm.value.nombre,
      direccion:    editForm.value.direccion,
      tipoServicio: editForm.value.tipoServicio, // <-- camelCase aquí
      ubicacion:    editForm.value.ubicacion
    }
    await updateEmpresa(editForm.value.empresa_id, payload)
    alert("Empresa actualizada correctamente!")
    router.push('/taskdetails')
  } catch (error) {
    console.error(error)
    alert("Error actualizando la empresa")
  } finally {
    loading.value = false
  }
}

function goBack() {
  router.push('/taskdetails')
}

onMounted(async () => {
  await fetchEmpresa()
  initMap()
})
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 400px;
  margin-top: 10px;
  border: 1px solid #ddd;
}
</style>