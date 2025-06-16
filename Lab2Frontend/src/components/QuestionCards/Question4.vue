<template>
  <div class="question-card">
    <h2>Consulta 4: Punto de entrega más lejano</h2>
    
    <div class="form-group">
      <label for="empresa">Seleccione una empresa:</label>
      <select 
        id="empresa" 
        v-model="currentSelectedEmpresaId"
        class="form-control"
      >
        <option value="">Seleccione una empresa</option>
        <option 
          v-for="empresa in empresas" 
          :key="empresa.empresaId" 
          :value="empresa.empresaId"
        >
          {{ empresa.empresaId }} - {{ empresa.nombre }}
        </option>
      </select>
    </div>

    <div v-if="loading" class="loading">
      Cargando...
    </div>

    <div v-if="error" class="error">
      {{ error }}
    </div>

    <div v-if="coordenada" class="result">
      <h3>Punto de entrega más lejano:</h3>
      <p>Latitud: {{ coordenada.latitude }}</p>
      <p>Longitud: {{ coordenada.longitude }}</p>
      <div id="map" style="height: 400px; margin-top: 16px;"></div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch, computed } from 'vue'
import { getAllEmpresas, getEntregaMasLejana } from '@/api/empresas'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css' // Importamos los estilos de Leaflet

// Importar y configurar los íconos por defecto de Leaflet
import markerIcon from 'leaflet/dist/images/marker-icon.png'
import markerShadow from 'leaflet/dist/images/marker-shadow.png'

const DefaultIcon = L.icon({
  iconUrl: markerIcon,
  shadowUrl: markerShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  shadowSize: [41, 41]
});

L.Marker.prototype.options.icon = DefaultIcon;

export default {
  name: 'Question4',
  setup() {
    const empresas = ref([])
    const selectedEmpresaId = ref(null)
    const coordenada = ref(null)
    const loading = ref(false)
    const error = ref(null)
    let map = null
    let marker = null

    // Computed property for v-model to ensure proper type handling and debugging
    const currentSelectedEmpresaId = computed({
      get() {
        return selectedEmpresaId.value
      },
      set(newValue) {
        console.log('Computed setter: selectedEmpresaId received:', newValue)
        // Ensure it's a number for IDs or null for the empty option
        selectedEmpresaId.value = newValue === '' ? null : Number(newValue)
      }
    })

    const loadEmpresas = async () => {
      try {
        const data = await getAllEmpresas()
        console.log('Empresas cargadas:', data)
        empresas.value = data
        if (data.length > 0) {
          console.log('Tipo de empresaId para la primera empresa:', typeof data[0].empresaId);
        }
      } catch (err) {
        error.value = 'Error al cargar las empresas'
        console.error('Error cargando empresas:', err)
      }
    }

    const getEntregaMasLejanaData = async () => {
      console.log('getEntregaMasLejanaData ejecutada. ID seleccionado:', selectedEmpresaId.value)
      if (!selectedEmpresaId.value) {
        coordenada.value = null
        return
      }

      loading.value = true
      error.value = null
      try {
        console.log('Buscando entrega más lejana para empresa ID:', selectedEmpresaId.value)
        const data = await getEntregaMasLejana(selectedEmpresaId.value)
        console.log('Datos recibidos:', data)
        coordenada.value = data
      } catch (err) {
        error.value = 'Error al obtener la entrega más lejana'
        console.error('Error obteniendo entrega más lejana:', err)
      } finally {
        loading.value = false
      }
    }

    onMounted(() => {
      console.log('Componente montado')
      loadEmpresas()
    })

    // Watch selectedEmpresaId to trigger data fetching
    watch(selectedEmpresaId, (newValue) => {
      console.log('selectedEmpresaId ha cambiado a:', newValue);
      if (newValue !== null && newValue !== '') {
        getEntregaMasLejanaData();
      } else {
        coordenada.value = null;
        if (marker) {
            map.removeLayer(marker);
            marker = null;
        }
        if (map) {
            map.setView([-33.45, -70.68], 13); 
        }
      }
    }, { immediate: true });

    watch(coordenada, (newCoordenada) => {
      if (newCoordenada && newCoordenada.latitude && newCoordenada.longitude) {
        if (!map) {
          map = L.map('map').setView([newCoordenada.latitude, newCoordenada.longitude], 13)
          L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
          }).addTo(map)
        } else {
          map.setView([newCoordenada.latitude, newCoordenada.longitude], 13)
        }

        if (marker) {
          map.removeLayer(marker)
        }

        marker = L.marker([newCoordenada.latitude, newCoordenada.longitude]).addTo(map)
          .bindPopup('Punto de Entrega Más Lejano').openPopup()
      } else if (map && !newCoordenada) {
        if (marker) {
          map.removeLayer(marker)
          marker = null
        }
        map.setView([-33.45, -70.68], 13) // Coordenadas de Santiago de Chile como ejemplo
      }
    })

    return {
      empresas,
      selectedEmpresaId,
      coordenada,
      loading,
      error,
      getEntregaMasLejana: getEntregaMasLejanaData,
      currentSelectedEmpresaId // Expose computed property
    }
  }
}
</script>

<style scoped>
#map {
  width: 100%;
}

.question-card {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  margin: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-control {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-top: 5px;
}

.loading {
  color: #666;
  font-style: italic;
}

.error {
  color: #dc3545;
  margin: 10px 0;
}

.result {
  margin-top: 20px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 4px;
}

.result h3 {
  margin-top: 0;
  color: #333;
}

.result p {
  margin: 5px 0;
}
</style>
