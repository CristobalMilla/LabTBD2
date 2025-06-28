<template>
  <v-card>
    <v-card-title>
      Entrega Más Lejana por Empresa
    </v-card-title>
    <v-card-text>
      <v-select
        v-model="selectedEmpresaId"
        :items="empresas"
        :item-title="item => item.empresaId + ' - ' + item.nombre"
        item-value="empresaId"
        label="Selecciona una empresa"
        
        class="mb-4"
      />
      <div v-if="error" class="text-red mb-4">
        {{ error }}
      </div>
      <div v-if="ubicacionActual" class="text-grey mb-4">
        Ubicación actual: Latitud: {{ ubicacionActual.latitude }}, Longitud: {{ ubicacionActual.longitude }}
      </div>
      <div id="map" style="height: 400px;"></div>
    </v-card-text>
  </v-card>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import L from "leaflet";
import { getAllEmpresas, getEntregaMasLejana } from "@/api/empresas";

const empresas = ref([]);
const selectedEmpresaId = ref(null);
const ubicacionActual = ref(null);
const error = ref(null);
let map = null;
let marker = null;

// No necesitamos el redIcon si usamos circleMarker
// const redIcon = L.icon({
//   iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-red.png',
//   shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
//   iconSize: [25, 41],
//   iconAnchor: [12, 41],
//   popupAnchor: [1, -34],
//   shadowSize: [41, 41]
// });

const fetchEmpresas = async () => {
  try {
    empresas.value = await getAllEmpresas();
    console.log("Empresas obtenidas:", empresas.value);
  } catch (error) {
    console.error("Error al obtener empresas:", error);
    error.value = "Error al cargar las empresas";
  }
};

const fetchEntregaMasLejana = async () => {
  console.log("fetchEntregaMasLejana function called.");
  if (!selectedEmpresaId.value || !map) {
    console.log("Condición de retorno met: selectedEmpresaId.value =", selectedEmpresaId.value, ", map =", map);
    return;
  }
  
  try {
    console.log("Buscando entrega más lejana para empresa ID:", selectedEmpresaId.value);
    const ubicacion = await getEntregaMasLejana(selectedEmpresaId.value);
    console.log("Ubicación recibida:", ubicacion);
    
    if (!ubicacion || !ubicacion.latitude || !ubicacion.longitude) {
      throw new Error("La ubicación recibida no es válida");
    }

    ubicacionActual.value = ubicacion;
    error.value = null;
    
    // Remover marcador anterior si existe
    if (marker) {
      map.removeLayer(marker);
    }
    
    // Crear nuevo circleMarker
    marker = L.circleMarker([ubicacion.latitude, ubicacion.longitude], {
      color: 'red', // Borde rojo
      radius: 8, // Tamaño del círculo (ajustable)
      fillColor: '#f03', // Relleno rojo más oscuro
      fillOpacity: 0.8
    }).addTo(map);
    
    // Centrar el mapa en la nueva ubicación con un zoom más cercano
    map.setView([ubicacion.latitude, ubicacion.longitude], 15);
    
    // Forzar actualización del mapa
    map.invalidateSize();
  } catch (error) {
    console.error("Error al obtener la entrega más lejana:", error);
    error.value = "Error al obtener la ubicación de la entrega más lejana";
    ubicacionActual.value = null;
  }
};

onMounted(async () => {
  try {
    await fetchEmpresas();
    map = L.map("map").setView([-33.45, -70.68], 13);
    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
      attribution: "© OpenStreetMap contributors"
    }).addTo(map);
    
    // Forzar actualización inicial del mapa
    setTimeout(() => {
      map.invalidateSize();
    }, 100);
    console.log("selectedEmpresaId inicial en onMounted:", selectedEmpresaId.value);
  } catch (error) {
    console.error("Error en la inicialización:", error);
    error.value = "Error al inicializar el mapa";
  }
});

// Nuevo watch para selectedEmpresaId
watch(selectedEmpresaId, (newVal, oldVal) => {
  console.log('selectedEmpresaId cambió de:', oldVal, 'a:', newVal);
  if (newVal) {
    fetchEntregaMasLejana();
  }
});
</script>

<style scoped>
#map {
  width: 100%;
}
</style>
