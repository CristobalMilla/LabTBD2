<script setup>
import {onMounted, ref} from 'vue'
import { getClientesQueBuscaronPeroNoCompraron } from '@/api/navegacion_usuarios';

const loading = ref(true)
const errorFetching = ref(false)
const clienteQueBuscaronPeroNoCompraron = ref([])

const getClientes = async () => {
  try {
    clienteQueBuscaronPeroNoCompraron.value = await getClientesQueBuscaronPeroNoCompraron();
  } catch (error) {
    console.error('Error fetching clientes:', error)
    errorFetching.value = true
  } finally {
    loading.value = false
  }
};

onMounted(async () => {
  await getClientes();
})

</script>

<template>
  <div>
    <v-card class="mx-auto" max-width="800">
      <v-card-title class="text-h6 text-center">
        Clientes que buscaron productos pero no compraron
      </v-card-title>
      <v-card-text>
        <div v-if="loading" class="d-flex justify-center">
          <v-progress-circular indeterminate color="primary"></v-progress-circular>
        </div>

        <div v-else-if="errorFetching" class="text-center red--text">
          Error al cargar los clientes
        </div>

        <div v-else>
          <div>
            <v-table v-if="clienteQueBuscaronPeroNoCompraron.length > 0">
              <thead>
                <tr>
                  <th class="text-center">Id</th>
                  <th class="text-center">Cliente</th>
                  <th class="text-center">Productos Buscados</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(cliente, index) in clienteQueBuscaronPeroNoCompraron" :key="index">
                    <td class="text-center">{{ cliente.cliente_id }}</td>
                    <td class="text-center">{{ cliente.nombre_cliente }}</td>
                    <td class="text-center">
                    <ul class="productos-lista-centrada">
                      <li v-for="(producto, idx) in cliente.productos_buscados" :key="idx">
                        {{ producto }}
                      </li>
                    </ul>
                  </td>
                </tr>
              </tbody>
            </v-table>
            <div v-else class="text-center pa-4">
              No hay busquedas de clientes
            </div>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </div>
</template>

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
.productos-lista-centrada {
  list-style: none; /* quita los bullets (puedes usar 'disc' si prefieres) */
  padding: 0;
  margin: 0;
  text-align: center; /* centra el texto de los <li> */
}

.productos-lista-centrada li {
  margin: 4px 0;
  font-size: 0.95rem;
}
</style>