<template>
  <v-card>
    <v-card-title>
      Zonas de Cobertura por Cliente
    </v-card-title>
    <v-card-text>
      <v-select
        v-model="selectedClienteId"
        :items="clientes"
        item-title="nombre"
        item-value="cliente_id"
        label="Selecciona un cliente"
        class="mb-4"
      />
      <v-list v-if="zonas.length">
        <v-list-item v-for="zona in zonas" :key="zona.zona_id">
          <v-list-item-title>{{ zona.nombre }}</v-list-item-title>
        </v-list-item>
      </v-list>
      <div id="map" style="height: 400px; margin-top: 16px;"></div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { getByClienteId } from "@/api/zonasCobertura";
import { getClientes } from "@/api/clientes";
import wellknown from "wellknown";
import L from "leaflet";

const clientes = ref([]);
const selectedClienteId = ref(null);
const zonas = ref([]);
let map = null;
let drawnLayers = [];

const fetchClientes = async () => {
  clientes.value = await getClientes();
};

const fetchZonasCobertura = async () => {
  if (!selectedClienteId.value) return;
  zonas.value = await getByClienteId(selectedClienteId.value);
  drawPolygons();
};

const drawPolygons = () => {
  if (!map) return;
  drawnLayers.forEach(layer => map.removeLayer(layer));
  drawnLayers = [];
  zonas.value.forEach(zona => {
    const geojson = wellknown.parse(zona.geom);
    if (geojson) {
      const layer = L.geoJSON(geojson).addTo(map);
      drawnLayers.push(layer);
    }
  });
};

onMounted(async () => {
  await fetchClientes();
  map = L.map("map").setView([-33.45, -70.68], 13);
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors"
  }).addTo(map);
});

watch(zonas, () => {
  drawPolygons();
});
watch(selectedClienteId, (newVal) => {
  console.log('Selected:', newVal);
  fetchZonasCobertura();
});
</script>

<style scoped>
#map {
  width: 100%;
}
</style>