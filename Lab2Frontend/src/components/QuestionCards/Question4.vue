<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios';

const promedioDistancia = ref(null);
const loading = ref(false);
const error = ref(null);

const getPromedioDistancia = async () => {
    try {
        loading.value = true;
        error.value = null;
        const usuario = JSON.parse(localStorage.getItem("user"));
        
        if (!usuario?.token) {
            throw new Error("No se encontró el token de autenticación");
        }

        if (!usuario?.id_usuario) {
            throw new Error("No se encontró el ID del usuario");
        }
        
        const response = await axios.get(`http://localhost:8000/api/tareas/promedio-distancia/${usuario.id_usuario}`, {
            headers: {
                Authorization: `Bearer ${usuario.token}`,
            },
        });
        
        if (response.data && (response.data.promedio_distancia !== undefined || response.data.error)) {
            if (response.data.error) {
                error.value = response.data.error;
            } else {
                promedioDistancia.value = response.data.promedio_distancia;
            }
        } else {
            error.value = "No se encontraron datos para calcular el promedio";
        }
    } catch (err) {
        console.error('Error:', err);
        let mensajeError = 'Error al calcular el promedio de distancia';
        
        if (err.response) {
            switch (err.response.status) {
                case 404:
                    mensajeError = 'No se encontró el recurso solicitado. Error 404.';
                    break;
                case 401:
                    mensajeError = 'No está autorizado para realizar esta acción';
                    break;
                case 403:
                    mensajeError = 'No tiene permisos para realizar esta acción';
                    break;
                case 500:
                    mensajeError = 'Error interno del servidor. Error 500.';
                    break;
                default:
                    mensajeError = err.response.data?.message || 'Error al procesar la solicitud';
            }
        } else if (err.request) {
            mensajeError = 'No se pudo conectar con el servidor';
        } else {
            mensajeError = err.message || 'Error desconocido';
        }
        
        error.value = mensajeError;
        promedioDistancia.value = null;
    } finally {
        loading.value = false;
    }
};

const formatDistance = (distance) => {
    if (distance === null) return "No hay datos";
    return (distance / 1000).toFixed(2) + " km";
};

onMounted(() => {
    getPromedioDistancia();
});
</script>

<template>
    <v-container>
        <v-card class="mx-auto" max-width="800">
            <v-card-title class="text-h6 text-center">
                Promedio de distancia de tareas completadas
            </v-card-title>

            <v-card-text>
                <div v-if="loading" class="d-flex justify-center">
                    <v-progress-circular indeterminate color="primary"></v-progress-circular>
                </div>

                <div v-else-if="error" class="text-center red--text">
                    {{ error }}
                </div>

                <div v-else-if="promedioDistancia !== null" class="text-center">
                    <v-table>
                        <thead>
                            <tr>
                                <th class="text-center">Promedio de Distancia</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td class="text-center text-h5">{{ formatDistance(promedioDistancia) }}</td>
                            </tr>
                        </tbody>
                    </v-table>
                </div>
            </v-card-text>
        </v-card>
    </v-container>
</template>

<script>
export default {
    name: "Question4",
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
</style>
