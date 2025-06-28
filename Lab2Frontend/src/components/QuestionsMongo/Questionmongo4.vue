<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="4">
        <v-select
          v-model="selectedRepartidorId"
          :items="repartidores"
          item-title="nombre"
          item-value="repartidor_id"
          label="Seleccione un Repartidor"
          variant="outlined"
          dense
          :loading="loadingRepartidores"
        ></v-select>
        <v-btn
          @click="fetchRutasFrecuentes"
          :disabled="!selectedRepartidorId || loadingRutas"
          :loading="loadingRutas"
          color="primary"
          class="mt-2"
          block
        >
          Buscar Rutas Frecuentes
        </v-btn>
      </v-col>
      <v-col cols="12" md="8">
        <div id="rutas-map" style="height: 400px; width: 100%; border: 1px solid #ccc;"></div>
      </v-col>
    </v-row>

    <v-row v-if="rutasFrecuentes.length > 0" class="mt-4">
      <v-col cols="12">
        <v-list lines="one">
          <v-list-subheader>Rutas Encontradas</v-list-subheader>
          <v-list-item
            v-for="(ruta, index) in rutasFrecuentes"
            :key="index"
            :title="`Ruta ${index + 1}`"
            :subtitle="`Frecuencia: ${ruta.frecuencia} veces`"
          >
            <template v-slot:prepend>
              <v-avatar :color="routeColors[index % routeColors.length]">
                <v-icon color="white">mdi-road-variant</v-icon>
              </v-avatar>
            </template>
          </v-list-item>
        </v-list>
      </v-col>
    </v-row>
    <v-alert v-if="error" type="error" class="mt-4">{{ error }}</v-alert>
  </v-container>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { getAllRepartidores } from '@/api/repartidores';
import { getFrecuenciaRutas } from '@/api/pedidos';

const repartidores = ref([]);
const selectedRepartidorId = ref(null);
const rutasFrecuentes = ref([]);
const loadingRepartidores = ref(false);
const loadingRutas = ref(false);
const error = ref(null);

let map = null;
const routeLayers = L.layerGroup();
const routeColors = ['#FF5733', '#33FF57', '#3357FF', '#FF33A1', '#A133FF', '#33FFA1'];

// Cargar repartidores al montar el componente
onMounted(async () => {
  loadingRepartidores.value = true;
  try {
    repartidores.value = await getAllRepartidores();
  } catch (e) {
    error.value = 'Error al cargar la lista de repartidores.';
  } finally {
    loadingRepartidores.value = false;
  }
  
  // Inicializar el mapa
  await nextTick();
  initMap();
});

function initMap() {
  if (map) return;
  map = L.map('rutas-map').setView([-33.455, -70.685], 12);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
  routeLayers.addTo(map);
}

async function fetchRutasFrecuentes() {
  if (!selectedRepartidorId.value) return;

  loadingRutas.value = true;
  error.value = null;
  rutasFrecuentes.value = [];
  routeLayers.clearLayers();

  try {
    const data = await getFrecuenciaRutas(selectedRepartidorId.value);
    rutasFrecuentes.value = data;
    if (data.length > 0) {
      drawRoutesOnMap(data);
    } else {
      alert('No se encontraron rutas frecuentes para este repartidor en los últimos 7 días.');
    }
  } catch (e) {
    error.value = 'Error al obtener las rutas frecuentes.';
  } finally {
    loadingRutas.value = false;
  }
}

function drawRoutesOnMap(rutas) {
  const allBounds = [];
  rutas.forEach((ruta, index) => {
    const latLngs = parseLineStringWKT(ruta.rutaEstimadaWkt);
    if (latLngs.length > 0) {
      const color = routeColors[index % routeColors.length];
      const polyline = L.polyline(latLngs, { color: color, weight: 5 }).addTo(routeLayers);
      polyline.bindPopup(`<b>Ruta ${index + 1}</b><br>Frecuencia: ${ruta.frecuencia}`);
      allBounds.push(polyline.getBounds());
    }
  });

  if (allBounds.length > 0) {
    const combinedBounds = allBounds.reduce((bounds, currentBounds) => bounds.extend(currentBounds));
    map.fitBounds(combinedBounds, { padding: [50, 50] });
  }
}

function parseLineStringWKT(wkt) {
  if (!wkt || !wkt.toUpperCase().startsWith("LINESTRING")) return [];
  const coordsStr = wkt.substring(wkt.indexOf('(') + 1, wkt.lastIndexOf(')'));
  return coordsStr.split(',').map(pair => {
    const [lng, lat] = pair.trim().split(' ').map(Number);
    return [lat, lng]; // Leaflet usa [lat, lng]
  });
}
</script>