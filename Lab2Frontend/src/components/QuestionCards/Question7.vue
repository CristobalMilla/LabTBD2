<template>
  <v-card>
    <v-card-title>
      Zona de Cobertura de un Cliente
    </v-card-title>
    <v-card-text>
      <v-select
        v-model="selectedClienteId"
        :items="clientes"
        item-title="itemTitle"
        item-value="cliente_id"
        label="Selecciona un cliente"
        class="mb-4"
      />
      <div v-if="zona" class="mt-4">
        <p><strong>ID de Zona:</strong> {{ zona.zonaId }}</p>
        <p><strong>Nombre de Zona:</strong> {{ zona.nombre }}</p>
        <p><strong>Geometría (WKT):</strong> {{ zona.geom }}</p>
      </div>
      <div v-else-if="selectedClienteId" class="mt-4 text-info">
        <p>No se encontró una zona de cobertura para el cliente seleccionado.</p>
      </div>
      <div v-else class="mt-4 text-muted">
        <p>Selecciona un cliente para ver su zona de cobertura.</p>
      </div>
      <div id="map" style="height: 400px; margin-top: 16px;"></div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { getClientes} from "@/api/clientes";
import { obtenerZonaDeCliente } from "@/api/zonasCobertura";
import L from "leaflet";
import wellknown from "wellknown";

const clientes = ref([]);
const selectedClienteId = ref(null);
const zona = ref(null);

let map = null;
let drawnLayer = null;
let clienteMarker = null;

const fetchClientes = async () => {
  const fetchedClientes = await getClientes();
  clientes.value = fetchedClientes.map(cliente => ({
    ...cliente,
    itemTitle: `${cliente.cliente_id} - ${cliente.nombre}`
  }));
};

const fetchZonaDeCliente = async () => {
  if (!selectedClienteId.value) {
    zona.value = null;
    return;
  }
  try {

    zona.value = await obtenerZonaDeCliente(selectedClienteId.value);
  } catch (error) {
    console.error("Error al obtener la zona del cliente:", error);
    zona.value = null; // Ensure zona is null on error
  }
};

const initMap = () => {
  if (map) {
    map.remove();
  }
  map = L.map("map").setView([-33.45, -70.68], 13); // Default view
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "© OpenStreetMap contributors"
  }).addTo(map);
};

const drawZoneOnMap = () => {
  if (!map) {
    initMap(); 
  }

  // Clear previous layer
  if (drawnLayer) {
    map.removeLayer(drawnLayer);
    drawnLayer = null; // Clear drawnLayer reference
  }
  if (clienteMarker) {
    map.removeLayer(clienteMarker);
    clienteMarker = null;
  }

  // ahora dibujamos la zona del cliente
  if (zona.value && zona.value.geom) {
    try {
      const geojson = wellknown.parse(zona.value.geom);
      if (geojson) {
        drawnLayer = L.geoJSON(geojson).addTo(map);
        map.fitBounds(drawnLayer.getBounds().pad(0.2)); 
        map.invalidateSize(); 
      }
    } catch (e) {
      console.error("Error parsing WKT or drawing GeoJSON:", e);
      clearMap(); // Clear map on parsing error
    }
  } else {
    clearMap(); // Clear map if no zone data
  }

  // ahora dibujamos el marcador del cliente
    if (zona.value && zona.value.clienteUbicacion) {
        try {
        const punto = zona.value.clienteUbicacion
            .replace("POINT(", "")
            .replace(")", "")
            .split(" ");
        const latlng = [parseFloat(punto[1]), parseFloat(punto[0])];
        clienteMarker = L.marker(latlng)
            .addTo(map)
            .bindPopup(`Cliente ubicado aquí en zona ${zona.value.nombre}`)
            .openPopup();
        } catch (e) {
        console.error("Error al parsear el punto del cliente:", e);
        }
    }
};

const clearMap = () => {
  if (drawnLayer) {
    map.removeLayer(drawnLayer);
    drawnLayer = null;
  }
  if (map) {
    map.setView([-33.45, -70.68], 13); // Reset to default view
    map.invalidateSize();
  }
};

onMounted(async () => {
  await fetchClientes();
  initMap();
  drawZoneOnMap(); // Call drawZoneOnMap initially to show default map or initial zone if selectedClient has a default value
});

watch(selectedClienteId, (newVal) => {
  fetchZonaDeCliente();
});

watch(zona, () => {
  drawZoneOnMap();
});
</script>

<style scoped>
#map {
  width: 100%;
}
</style>
