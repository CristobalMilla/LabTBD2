<template>
  <v-card>
    <v-card-title>
      Clientes a 5km o más de cualquier empresa
    </v-card-title>
    <v-card-text>
      <v-btn color="primary" @click="fetchClientes" class="mb-4">
        Consultar clientes lejanos
      </v-btn>
      <div v-if="clientes.length === 0 && fetched">
        <v-alert type="info" variant="tonal">
          No hay clientes lejanos a empresas.
        </v-alert>
      </div>
      <v-list v-else>
        <v-list-item v-for="cliente in clientes" :key="cliente.cliente_id">
          <v-list-item-title>
            Cliente ID: {{ cliente.cliente_id }}
          </v-list-item-title>
        </v-list-item>
      </v-list>
      <div id="map6" style="height: 400px; margin-top: 16px;"></div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { clientesNoCercanosAEmpresas } from "@/api/clientes";
import L from "leaflet";

const clientes = ref([]);
const fetched = ref(false);
let map = null;
let markers = [];

function parsePointWKT(wkt) {
  // WKT: "POINT(lon lat)"
  const match = wkt.match(/POINT\s*\(\s*(-?\d+\.?\d*)\s+(-?\d+\.?\d*)\s*\)/);
  if (match) {
    // Leaflet expects [lat, lon]
    return [parseFloat(match[2]), parseFloat(match[1])];
  }
  return null;
}

const fetchClientes = async () => {
  clientes.value = await clientesNoCercanosAEmpresas();
  fetched.value = true;
  drawMarkers();
};

function drawMarkers() {
  if (!map) return;
  // Remove previous markers
  markers.forEach(marker => map.removeLayer(marker));
  markers = [];
  clientes.value.forEach(cliente => {
    const latlng = parsePointWKT(cliente.ubicacion);
    if (latlng) {
      const marker = L.marker(latlng)
        .addTo(map)
        .bindPopup(`ID: ${cliente.cliente_id}`);
      markers.push(marker);
    }
  });
  // Optionally fit map to markers
  if (markers.length > 0) {
    const group = L.featureGroup(markers);
    map.fitBounds(group.getBounds().pad(0.2));
  }
}

onMounted(() => {
  map = L.map("map6").setView([-33.45, -70.68], 12);
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors"
  }).addTo(map);
});
</script>

<style scoped>
#map6 {
  width: 100%;
}
</style>