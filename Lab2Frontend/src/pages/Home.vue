<template>
  <v-container fluid class="dashboard pa-0">
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title class="d-flex align-center">
        <v-btn variant="text" @click="$router.push('/dashboard')" class="d-flex align-center">
          <v-icon size="24" class="mr-2">mdi-view-dashboard</v-icon>
          Consultas
        </v-btn>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn variant="text" @click="goToEmpresasDetails" class="mr-2" prepend-icon="mdi-clipboard-text">
        Empresas
      </v-btn>
      <notification-badge class="mr-2" />
      <v-btn @click="logout" variant="text" prepend-icon="mdi-logout">
        Cerrar Sesión
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container class="py-8">
        <div class="welcome-section">
          <h2 class="d-flex align-center mb-2">
            <v-icon size="32" color="primary" class="mr-2">mdi-account</v-icon>
            Bienvenido, {{ nickname }}
          </h2>
          <p class="date d-flex align-center text-grey">
            <v-icon size="20" class="mr-2">mdi-calendar</v-icon>
            {{ currentDate }}
          </p>
        </div>

        <v-card class="mt-8" elevation="2">
          <v-card-title class="d-flex align-center pa-6 bg-grey-lighten-4">
            <v-icon size="28" color="primary" class="mr-3">mdi-map</v-icon>
            <span class="text-h5">Mapa de comuna Estación Central</span>
          </v-card-title>
          <v-card-text class="pa-0">
            <div id="map" style="height: 500px;"></div>
          </v-card-text>
          <v-card-actions class="pa-4">
            <v-checkbox
              v-model="showCalles"
              label="Mostrar Calles"
              color="primary"
              hide-details
              class="mr-4"
            ></v-checkbox>
            <v-checkbox
              v-model="showVertices"
              label="Mostrar Vértices"
              color="secondary"
              hide-details
            ></v-checkbox>
          </v-card-actions>
        </v-card>

      </v-container>
    </v-main>
  </v-container>
</template>

<script>
import { logoutUser } from "@/services/auth";
import NotificationBadge from '@/components/NotificationBadge.vue';
// import L from 'leaflet'; // Asegúrate de que Leaflet esté disponible
// import 'leaflet/dist/leaflet.css';

export default {
  name: 'HomePage',
  components: {
    NotificationBadge,
  },
  data() {
    return {
      nickname: 'Usuario',
      map: null,
      callesLayer: null,
      verticesLayer: null,
      allVerticesData: [], // Para almacenar los datos de los vértices
      showCalles: true,
      showVertices: true,
    }
  },
  computed: {
    currentDate() {
      return new Date().toLocaleDateString('es-ES', {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    }
  },
  watch: {
    showCalles(newValue) {
      if (this.map && this.callesLayer) {
        if (newValue) {
          this.map.addLayer(this.callesLayer);
        } else {
          this.map.removeLayer(this.callesLayer);
        }
      }
    },
    showVertices(newValue) {
      if (this.map && this.verticesLayer) {
        if (newValue) {
          this.map.addLayer(this.verticesLayer);
          this.renderVerticesOnMap(); // Re-render para aplicar visibilidad y radio actual
        } else {
          this.map.removeLayer(this.verticesLayer);
        }
      }
    }
  },
  methods: {
    logout() {
      logoutUser();
      this.$router.push('/login')
    },
    goToEmpresasDetails() {
      this.$router.push('/empresasdetails')
    },
    initMap() {
      this.map = L.map('map').setView([-33.455, -70.685], 13);
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);

      this.callesLayer = L.layerGroup();
      this.verticesLayer = L.layerGroup();

      if (this.showCalles) {
        this.map.addLayer(this.callesLayer);
      }
      if (this.showVertices) {
        this.map.addLayer(this.verticesLayer);
      }

      // Escuchar el evento zoomend para re-renderizar los vértices
      this.map.on('zoomend', this.handleMapZoom);
    },
    handleMapZoom() {
      if (this.showVertices && this.verticesLayer && this.map.hasLayer(this.verticesLayer)) {
        this.renderVerticesOnMap();
      }
    },
    async fetchCalles() {
      if (!this.callesLayer) return;
      this.callesLayer.clearLayers(); 

      try {
        const apiUrl = `${import.meta.env.VITE_API_BASE_URL}/api/calles/all`;
        const response = await fetch(apiUrl);
        if (!response.ok) {
          throw new Error('Error al obtener las calles');
        }
        const calles = await response.json();
        this.renderCallesOnMap(calles);
      } catch (error) {
        console.error("Error en fetchCalles:", error);
      }
    },
    renderCallesOnMap(calles) {
      calles.forEach(calle => {
        if (calle.geomWkt) {
          const latLngs = this.parseLineStringWKT(calle.geomWkt);
          if (latLngs.length > 0) {
            L.polyline(latLngs, { color: 'blue', weight: 2 }).addTo(this.callesLayer);
          }
        }
      });
    },
    parseLineStringWKT(wkt) {
      if (!wkt || !wkt.startsWith("LINESTRING(")) return [];
      const coordsText = wkt.substring(11, wkt.length - 1);
      const points = coordsText.split(",").map(pair => {
        const [lng, lat] = pair.trim().split(" ").map(Number);
        return [lat, lng]; 
      });
      return points;
    },
    async fetchVertices() {
      try {
        const apiUrl = `${import.meta.env.VITE_API_BASE_URL}/api/calles-vertices/all`;
        const response = await fetch(apiUrl);
        if (!response.ok) {
          throw new Error('Error al obtener los vértices');
        }
        this.allVerticesData = await response.json(); // Almacenar los datos
        if (this.showVertices) {
            this.renderVerticesOnMap(); // Renderizar inicialmente
        }
      } catch (error) {
        console.error("Error en fetchVertices:", error);
        this.allVerticesData = []; // Asegurar que sea un array en caso de error
      }
    },
    getDynamicRadius() {
      if (!this.map) return 1; // Radio por defecto si el mapa no está listo
      const zoom = this.map.getZoom();
      if (zoom < 12) {
        return 0.1; // Muy pequeño cuando está muy alejado
      } else if (zoom < 14) {
        return 0.1;
      } else if (zoom < 16) {
        return 0.6;
      } else {
        return 2; // Más grande cuando está más cerca
      }
    },
    renderVerticesOnMap() {
      if (!this.verticesLayer || !this.map) return;
      this.verticesLayer.clearLayers(); // Limpiar la capa antes de re-dibujar

      const currentRadius = this.getDynamicRadius();

      this.allVerticesData.forEach(vertice => {
        if (vertice.theGeomWkt) {
          const latLngArray = this.parsePointWKT(vertice.theGeomWkt);
          if (latLngArray && latLngArray.length > 0) {
            const latLng = latLngArray[0]; 
            L.circleMarker(latLng, { 
              color: 'red', 
              radius: currentRadius, // Usar el radio dinámico
              fillColor: '#f03', 
              fillOpacity: 0.8 
            }).addTo(this.verticesLayer);
          }
        }
      });
    },
    parsePointWKT(wkt) {
      if (!wkt || !wkt.startsWith("POINT(")) return null;
      const coordsText = wkt.substring(6, wkt.length - 1);
      const [lng, lat] = coordsText.trim().split(" ").map(Number);
      if (isNaN(lng) || isNaN(lat)) return null;
      return [[lat, lng]]; 
    }
  },
  mounted() {
    const userString = localStorage.getItem("user");
    if (userString) {
      const storedUser = JSON.parse(userString);
      if (storedUser?.nickname) {
        this.nickname = storedUser.nickname;
      }
    }
    this.initMap();
    this.fetchCalles();
    this.fetchVertices();
  },
  beforeUnmount() {
    if (this.map) {
      this.map.off('zoomend', this.handleMapZoom); // Eliminar el listener
      this.map.remove();
      this.map = null;
    }
  }
}
</script>

<style scoped>
.dashboard {
  min-height: 100vh;
  background: #f0f2f5;
}

.welcome-section h2 {
  font-size: 2rem;
  font-weight: 600;
  margin: 0;
}

.date {
  font-size: 1.1rem;
}

#map {
  width: 100%;
  z-index: 0; 
}


@media (max-width: 600px) {
  .actions-section {
    flex-direction: column;
  }

  .actions-section .v-btn {
    width: 100%;
  }
  .v-card-actions {
    flex-direction: column;
    align-items: flex-start;
  }
  .v-card-actions .v-checkbox {
    margin-right: 0 !important;
    margin-bottom: 8px;
  }
}
</style>