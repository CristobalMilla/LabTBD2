<script setup>
import { nextTick, onMounted, ref, watch } from 'vue'
import {getPedidosQueCruzanMasDe2Zonas} from '@/api/pedidos'
import { getAllByIds } from '@/api/zonasCobertura';
import wellknown from "wellknown";
import L from "leaflet";

const pedidos = ref([])
const loading = ref(true)
const error = ref(false)
const map = ref(null)
const view_map = ref(false)
const pedido_index = ref(-1)
const zonas = ref([])

const getpedidos = async () => {
  try {
    pedidos.value = await getPedidosQueCruzanMasDe2Zonas()
  } catch (error) {
    console.error('Error fetching pedidos:', error)
  } finally {
    loading.value = false
  }
};

const initMap = async () => {
  await nextTick();
  const sectorCenter = [-33.464467, -70.705074];
  map.value = L.map("map").setView(sectorCenter, 15);
  L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
    attribution: "&copy; OpenStreetMap contributors",
  }).addTo(map.value);
};

onMounted(async () => {
  await getpedidos()
})

const verMapa = async (index) => {
  pedido_index.value = index;
  view_map.value = true;
  zonas.value = []; // Limpiar zonas anteriores

  if (map.value) {
    map.value.remove(); // Eliminar el mapa anterior
  }

  await nextTick();
  await initMap();
  await getZonasCruzadas();
};

const getZonasCruzadas = async () => {
  if (pedido_index.value > -1) {
    zonas.value = await getAllByIds(pedidos.value[pedido_index.value]);
  }
};


watch(pedidos, async () => {
  if (pedidos.value.length > 0 && pedido_index.value > -1) {
    await getZonasCruzadas();
  }
})

const drawZonas = () => {
  const geoJSON_1_zona = wellknown.parse(zonas.value[0].geom)
  const sectorCenter = [
    geoJSON_1_zona.coordinates[0][0][1],
    geoJSON_1_zona.coordinates[0][0][0]
    ];
  map.value.setView(sectorCenter, 14);
  for(var i = 0; i < zonas.value.length; i++){
    const geoJSON = wellknown.parse(zonas.value[i].geom)
    L.geoJSON(geoJSON).addTo(map.value);
  }
  
}

watch(zonas, async () => {
  if(zonas.value.length > 0){
    await drawZonas();
  }
})


</script>

<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Pedidos que se estima que cruzen mas de 2 zonas de reparto
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
            <div v-if="view_map">
              <v-container>
                <v-row>
                  <v-col cols="12" md="12">
                    <v-btn color="red" variant="tonal" @click="view_map = false">
                      <v-icon left>mdi-close</v-icon>
                        Cerrar Mapa
                      </v-btn>
                    <div id="map" style="height: 400px; margin-top: 16px;"></div>
                  </v-col>
                </v-row>
              </v-container>
            </div>

            <v-table v-else-if="pedidos.length > 0">
              <thead>
                <tr>
                  <th class="text-center">Pedido</th>
                  <th class="text-center">Cliente</th>
                  <th class="text-center">Empresa</th>
                  <th class="text-center">Repartidor</th>
                  <th class="text-center">Zonas que cruza</th>
                  <th class="text-center">Ver Mapa</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(pedido, index) in pedidos" :key="index">
                    <td class="text-center">{{ pedido.pedido_id }}</td>
                    <td class="text-center">{{ pedido.cliente_id }}</td>
                    <td class="text-center">{{ pedido.empresa_id }}</td>
                    <td class="text-center">{{ pedido.repartidor_id }}</td>
                    <td class="text-center">{{ pedido.zonas_que_cruza }}</td>
                    <td class="text-center">
                      <v-btn color="primary" variant="outlined" @click="verMapa(index)">
                        <v-icon left>mdi-map</v-icon>
                      </v-btn>
                    </td>
                </tr>
              </tbody>
            </v-table>
            <div v-else class="text-center pa-4">
              No hay tareas completadas en ningún sector.
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
    name: "Query1",
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