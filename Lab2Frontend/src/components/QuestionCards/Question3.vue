<script setup>
import { ref, watch, onMounted, nextTick } from 'vue'
import axios from 'axios'

const sectorConMasTareasCompletadas = ref([])
const map = ref(null)
const SectorCargado = ref(false)

const getSector = async () => {
  try {
    const usuario = JSON.parse(localStorage.getItem("user"))

    const response = await axios.get("http://localhost:8000/api/tareas/sectorMasCompletadas/" + usuario.id_usuario, {
      headers: {
        Authorization: `Bearer ${usuario.token}`,
      },
    })
    if(response.data != ""){
        sectorConMasTareasCompletadas.value = response.data;
        SectorCargado.value = true;
    }
  } catch (error) {
    console.error("Error obteniendo sector", error)
  }
}

const initMap = async () => {
    await nextTick();

    const usuario_local = JSON.parse(localStorage.getItem("user"));
    const response = await axios.get("http://localhost:8000/api/usuarios/" + usuario_local.id_usuario, {
        headers: {
        Authorization: `Bearer ${usuario_local.token}`,
        },
    });
    const usuario = response.data;
    const center = [usuario.ubicacion.coordinates[1], usuario.ubicacion.coordinates[0]];

    map.value = L.map("map").setView(center, 15);

    L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: "&copy; OpenStreetMap contributors",
    }).addTo(map.value);

    if (SectorCargado.value) {
        const sectorCenter = [
        sectorConMasTareasCompletadas.value.ubicacion.coordinates[0][0][1],
        sectorConMasTareasCompletadas.value.ubicacion.coordinates[0][0][0],
        ];
        map.value.setView(sectorCenter, 15);

        const geoJSON = {
        type: "Feature",
        geometry: {
            type: "Polygon",
            coordinates: [sectorConMasTareasCompletadas.value.ubicacion.coordinates[0]],
        },
        properties: {},
        };
        L.geoJSON(geoJSON).addTo(map.value);
    }
};

onMounted(async () => {
  await getSector()
})

watch(sectorConMasTareasCompletadas, async () => {
    await initMap()
  
})
</script>
    <template>
    <v-container>
        <v-row>
        <v-col v-if="SectorCargado" cols="12" md="4">
            <div class="text-h4">
                Sector: {{sectorConMasTareasCompletadas.id_sector}}
            </div>
        </v-col> 
        <v-col v-else>
            <div class="text-h4">
                No hay sectores cerca con tareas completadas
            </div>
        </v-col>
        <v-col cols="12" md="8">
            mapa: 
            <div id="map" class="rounded mb-2"></div>
        </v-col>
        </v-row>
    </v-container>
    </template>

    <script>
    export default {
        name: "Query3",
    };
    </script>

    <style>
    table {
        width: 100%;
        margin-top: 1rem;
    }
    td,
    th {
        border: 1px solid black;
    }
    #map {
    height: 400px;
    width: 100%;
    border: 1px solid black;
    }

    </style>