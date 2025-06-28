<template>
  <v-card elevation="2">
    <v-card-title class="pa-4">
      Puntuación Promedio y Cantidad de Opiniones por Hora
    </v-card-title>
    <v-card-text class="pa-4">
      <div v-if="loading" class="text-center">
        <v-progress-circular indeterminate color="primary"></v-progress-circular>
      </div>
      <Bar v-else-if="chartData.labels.length" :data="chartData" :options="chartOptions" style="height: 400px;" />
      <div v-else class="text-center">
        No hay datos para mostrar.
      </div>
    </v-card-text>
  </v-card>
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
    // 1. Preparar los arrays para las 24 horas
    const fullLabels = Array.from({ length: 24 }, (_, i) => `${String(i).padStart(2, '0')}:00`);
    const fullAverageScores = Array(24).fill(0);
    const fullCounts = Array(24).fill(0);

    // 2. Obtener los datos de la API
    const stats = await getOpinionStatsPorHora();
    
    // 3. Poblar los arrays con los datos obtenidos
    if (stats && stats.length > 0) {
      stats.forEach(stat => {
        // El 'hour' del DTO se usa como índice
        if (stat.hour >= 0 && stat.hour < 24) {
          fullAverageScores[stat.hour] = stat.averageScore;
          fullCounts[stat.hour] = stat.count;
        }
      });
    }

    // 4. Asignar los datos completos al gráfico
    chartData.value = {
      labels: fullLabels,
      datasets: [
        {
          label: 'Puntuación Promedio',
          backgroundColor: '#f87979',
          data: fullAverageScores,
          yAxisID: 'y' // Asignar al eje Y principal
        },
        {
          label: 'Cantidad de Opiniones',
          backgroundColor: '#42A5F5',
          data: fullCounts,
          yAxisID: 'y1' // Asignar a un segundo eje Y
        }
      ]
    };

  } catch (error) {
    console.error("Error al obtener estadísticas de opiniones:", error);
    alert("No se pudieron cargar las estadísticas.");
  } finally {
    loading.value = false;
  }
});
</script>