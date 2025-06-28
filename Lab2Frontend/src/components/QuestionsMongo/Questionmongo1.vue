
<template>
  <div class="five-points-map">
    <v-select
        v-model="selectedId"
        :items="companies"
        item-title="nombre"
        item-value="empresa_id"
        label="Empresa"
        density="compact"
        clearable
        class="mb-4"
    />

    <div id="five-points-leaflet" style="height: 450px;"></div>
  </div>
</template>
<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const BASE = import.meta.env.VITE_API_BASE_URL;


const map        = ref(null);
const pointsLg   = ref(null);
const companies  = ref([]);
const selectedId = ref(null);

function toLatLng({ latitude, longitude }) {
  return [latitude, longitude];
}

function dynamicRadius() {
  const z = map.value?.getZoom() ?? 13;
  if (z < 12) return 2;
  if (z < 14) return 3;
  if (z < 16) return 5;
  return 7;
}


async function fetchCompanies() {
  const res = await fetch(`${BASE}/api/empresas/all`);
  if (res.ok) companies.value = await res.json();
}

async function fetchPoints(id) {
  pointsLg.value.clearLayers();
  if (!id) return;
  const res = await fetch(`${BASE}/api/empresas/entregascercanas/${id}`);
  if (res.ok) renderPoints(await res.json());
}

function renderPoints(puntos) {
  const radius = dynamicRadius();
  puntos.forEach(coord => {
    const latLng = toLatLng(coord);
    L.circleMarker(latLng, {
      color: 'red',
      radius,
      fillOpacity: 0.9
    }).addTo(pointsLg.value);
  });
}

onMounted(async () => {
  map.value = L.map('five-points-leaflet').setView([-33.455, -70.685], 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
  }).addTo(map.value);

  pointsLg.value = L.layerGroup().addTo(map.value);
  await fetchCompanies();

  map.value.on('zoomend', () => {
    const r = dynamicRadius();
    pointsLg.value.eachLayer(l => l.setRadius && l.setRadius(r));
  });
});

onBeforeUnmount(() => map.value?.remove());

watch(selectedId, id => fetchPoints(id));
</script>

<style scoped>
.five-points-map {
  display: flex;
  flex-direction: column;
}
</style>