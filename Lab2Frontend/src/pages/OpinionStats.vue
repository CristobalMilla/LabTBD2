<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="$router.push('/clientes')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>Estadísticas de Opiniones por Hora</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-card elevation="2">
          <v-card-title class="pa-4">
            Puntuación Promedio y Cantidad de Opiniones
          </v-card-title>
          <v-card-text class="pa-4">
            <div v-if="loading" class="text-center">
              <v-progress-circular indeterminate color="primary"></v-progress-circular>
            </div>
            <Bar v-else-if="chartData.labels.length" :data="chartData" :options="chartOptions" />
            <div v-else class="text-center">
              No hay datos para mostrar.
            </div>
          </v-card-text>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { Bar } from 'vue-chartjs';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js';
import { getOpinionStatsPorHora } from '@/api/opiniones';

// Registrar los componentes de Chart.js
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

const loading = ref(true);
const chartData = ref({
  labels: [],
  datasets: []
});

const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false,
  scales: {
    y: {
      beginAtZero: true
    }
  }
});

onMounted(async () => {
  try {
    const stats = await getOpinionStatsPorHora();
    if (stats && stats.length > 0) {
      // Mapear los datos para el gráfico
      const labels = stats.map(s => `${String(s.hour).padStart(2, '0')}:00`);
      const averageScores = stats.map(s => s.averageScore);
      const counts = stats.map(s => s.count);

      chartData.value = {
        labels,
        datasets: [
          {
            label: 'Puntuación Promedio',
            backgroundColor: '#f87979',
            data: averageScores
          },
          {
            label: 'Cantidad de Opiniones',
            backgroundColor: '#42A5F5',
            data: counts
          }
        ]
      };
    }
  } catch (error) {
    console.error("Error al obtener estadísticas de opiniones:", error);
    alert("No se pudieron cargar las estadísticas.");
  } finally {
    loading.value = false;
  }
});
</script>