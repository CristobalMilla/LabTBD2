<script setup>
import { nextTick, onMounted, ref, watch } from 'vue'
import { getZonasAltaDensidad } from '@/api/zonasCobertura';
import wellknown from "wellknown";
import L from "leaflet";

const zonas_alta_densidad = ref([])
const loading = ref(true)
const error = ref(false)
const map = ref(null)
const view_map = ref(false)

const getZonas = async () => {
  try {
  zonas_alta_densidad.value = await getZonasAltaDensidad()
  } catch (error) {
    console.error('Error fetching zonas:', error)
  } finally {
    loading.value = false
  }
};

const initMap = async () => {
  await nextTick();
  const sectorCenterInit = [-33.464467, -70.705074];
  map.value = L.map("map").setView(sectorCenterInit, 15);
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "&copy; OpenStreetMap contributors",
  }).addTo(map.value);

  const geoJSON_1_zona = wellknown.parse(zonas_alta_densidad.value[0].geom)
  const sectorCenter = [
    geoJSON_1_zona.coordinates[0][0][1],
    geoJSON_1_zona.coordinates[0][0][0]
    ];
  map.value.setView(sectorCenter, 14);
  for(var i = 0; i < zonas_alta_densidad.value.length; i++){
    const geoJSON = wellknown.parse(zonas_alta_densidad.value[i].geom)
    L.geoJSON(geoJSON).addTo(map.value);
  }
  view_map.value = true
};

onMounted(async () => {
  await getZonas()
    if (zonas_alta_densidad.value.length > 0) {
    await initMap();
  }
})
</script>

<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h5 text-center font-weight-bold ">
        Zonas con densidad mayor a 100 pedidos/km²
      </v-card-title>


      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="error" class="text-center red--text">
          error
        </div>

        <div v-else>
          <div>
            <div>
              <v-container>
                <v-row>
                  <v-col cols="12" md="12">
                    <div id="map" style="height: 500px; margin-top: 16px; border-radius: 8px;"></div>
                  </v-col>
                </v-row>
              </v-container>
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
    name: "Query8",
};
</script>

<style scoped>
.v-table {
  width: 100%;
  margin-top: 1rem;
}

th, td {
  padding: 12px;
}

th {
  background-color: #f5f5f5;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #fafafa;
}

tr:hover {
  background-color: #f0f0f0;
}
</style>