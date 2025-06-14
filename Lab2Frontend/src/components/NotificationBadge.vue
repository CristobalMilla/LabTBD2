<template>
  <v-menu offset-y :close-on-content-click="false" eager>
    <template v-slot:activator="{ props }">
      <v-btn icon v-bind="props">
        <v-badge
          :content="upcomingTasksCount"
          color="error"
          overlap
          v-if="upcomingTasksCount > 0 && !isLoadingInitial"
        >
          <v-icon>mdi-bell-outline</v-icon>
        </v-badge>
        <v-progress-circular
          indeterminate
          size="24"
          v-else-if="isLoadingInitial"
        ></v-progress-circular>
        <v-icon v-else>mdi-bell-outline</v-icon>
      </v-btn>
    </template>

    <v-card :loading="isLoadingDetails" max-width="350">
      <v-list dense max-height="300px" class="overflow-y-auto">
        <v-list-subheader class="font-weight-bold"
          >Tareas Próximas a Vencer</v-list-subheader
        >
        <div v-if="!isLoadingInitial && !isLoadingDetails">
          <v-list-item
            v-for="task in upcomingTasksDetails"
            :key="task.id_tarea"
          >
            <v-list-item-title class="font-weight-medium">{{
              task.titulo
            }}</v-list-item-title>
            <v-list-item-subtitle
              >Vence: {{ formatDate(task.fecha_vencimiento) }}
            </v-list-item-subtitle>
            <v-divider class="my-1" v-if="upcomingTasksDetails.length > 1"></v-divider>
          </v-list-item>

          <v-list-item v-if="upcomingTasksDetails.length === 0 && !isLoadingInitial && !isLoadingDetails">
            <v-list-item-title class="text-center text-grey py-4">
              <v-icon size="32" class="d-block mx-auto mb-2"
                >mdi-check-circle-outline</v-icon
              >
              No tienes tareas próximas a vencer.
            </v-list-item-title>
          </v-list-item>
        </div>
      </v-list>
       <div v-if="isLoadingDetails" class="pa-4 text-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
          <p class="mt-2 text-caption">Cargando detalles...</p>
        </div>
    </v-card>
  </v-menu>
</template>

<script>
import axios from "axios"; // O tu cliente HTTP configurado

export default {
  name: "NotificationBadge",
  data() {
    return {
      upcomingTasksCount: 0,
      upcomingTasksDetails: [],
      isLoadingInitial: true, // Para la carga inicial del contador del badge
      isLoadingDetails: false, // Para cuando se abre el menú y se refrescan datos (opcional)
      userId: null,
      token: null,
    };
  },
  methods: {
    async fetchUpcomingTasks(isInitialLoad = false) {
      if (!this.userId || !this.token) {
        if (isInitialLoad) this.isLoadingInitial = false;
        console.warn(
          "NotificationBadge: User ID o Token no encontrado en localStorage."
        );
        return;
      }

      if (isInitialLoad) {
        this.isLoadingInitial = true;
      } else {
        // Si se quisiera refrescar al abrir el menú
        this.isLoadingDetails = true;
      }

      try {
        const response = await axios.get(
          `http://localhost:8000/api/tareas/proximasAVencer/${this.userId}`,
          {
            headers: { Authorization: `Bearer ${this.token}` },
          }
        );
        this.upcomingTasksDetails = response.data || [];
        this.upcomingTasksCount = this.upcomingTasksDetails.length;
      } catch (error) {
        console.error(
          "NotificationBadge: Error al obtener tareas próximas a vencer:",
          error.response ? error.response.data : error.message
        );
        this.upcomingTasksDetails = [];
        this.upcomingTasksCount = 0;
      } finally {
        if (isInitialLoad) {
          this.isLoadingInitial = false;
        } else {
          this.isLoadingDetails = false;
        }
      }
    },
    formatDate(dateString) {
      if (!dateString) return "Fecha no especificada";
      const date = new Date(dateString);
      if (isNaN(date.getTime())) return "Fecha inválida";
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
      };
      return date.toLocaleDateString("es-ES", options);
    },
    loadCredentials() {
      const storedUser = JSON.parse(localStorage.getItem("user"));
      const storedToken = localStorage.getItem("token");

      console.log("Stored User:", storedUser);
      console.log("Stored Token:", storedToken);

      if (storedUser && storedUser.id_usuario) {
        this.userId = storedUser.id_usuario;
      }

      if (storedToken) {
        this.token = storedToken;
      }
      
      console.log("Loaded userId:", this.userId);
      console.log("Loaded token:", this.token);

      if (!this.userId || !this.token) {
         this.isLoadingInitial = false; 
      }
    },
  },
  created() {
    this.loadCredentials();
    if (this.userId && this.token) {
      this.fetchUpcomingTasks(true); // Carga inicial para el badge
    }
  },
};
</script>

<style scoped>
.overflow-y-auto {
  overflow-y: auto;
}
</style>