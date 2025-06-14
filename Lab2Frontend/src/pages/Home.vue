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
      <v-btn variant="text" @click="goToTaskDetails" class="mr-2" prepend-icon="mdi-clipboard-text">
        Detalles de Tareas
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

        <!-- Se elimina la sección de tareas y se reemplaza por el mapa de Calles -->
        <v-card class="mt-8" elevation="2">
          <v-card-title class="d-flex align-center pa-6 bg-grey-lighten-4">
            <v-icon size="28" color="primary" class="mr-3">mdi-map</v-icon>
            <span class="text-h5">Mapa de comuna Estación Central</span>
          </v-card-title>
          <v-card-text class="pa-0">
            <div id="map" style="height: 500px;"></div>
          </v-card-text>
        </v-card>

      </v-container>
    </v-main>
  </v-container>
</template>

<script>
import { logoutUser } from "@/services/auth";
import NotificationBadge from '@/components/NotificationBadge.vue';

export default {
  name: 'HomePage',
  components: {
    NotificationBadge,
  },
  data() {
    return {
      nickname: 'Usuario',
      map: null,
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
  methods: {
    logout() {
      logoutUser();
      this.$router.push('/login')
    },
    goToTaskDetails() {
      this.$router.push('/taskdetails')
    },
    initMap() {
      // Inicializa el mapa centrado en unas coordenadas (ajusta según tu necesidad)
      this.map = L.map('map').setView([-33.47, -70.70], 13);
      L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png", {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
      }).addTo(this.map);
    },
    async fetchCalles() {
      try {
        const response = await fetch("http://localhost:8080/api/calles/all");
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
          // Convierte la cadena WKT (LINESTRING) en un arreglo de coordenadas para Leaflet:
          const latLngs = this.parseLineStringWKT(calle.geomWkt);
          if (latLngs.length > 0) {
            L.polyline(latLngs, { color: 'blue' }).addTo(this.map);
          }
        }
      });
    },
    parseLineStringWKT(wkt) {
      // Se espera un formato: "LINESTRING(lng lat,lng lat,...)"
      if (!wkt.startsWith("LINESTRING(")) return [];
      const coordsText = wkt.substring(11, wkt.length - 1);
      const points = coordsText.split(",").map(pair => {
        const [lng, lat] = pair.trim().split(" ").map(Number);
        return [lat, lng]; // Leaflet usa [lat, lng]
      });
      return points;
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
    // Inicializa el mapa y luego carga las calles
    this.initMap();
    this.fetchCalles();
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

@media (max-width: 600px) {
  .actions-section {
    flex-direction: column;
  }

  .actions-section .v-btn {
    width: 100%;
  }
}
</style>