<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="$router.push('/pedidos')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-toolbar-title>Registrar Nuevo Pedido</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-row>
          <!-- Columna del Formulario -->
          <v-col cols="12" md="5">
            <v-card elevation="2" class="pa-6">
              <v-form ref="form" lazy-validation>
                <!-- Menús desplegables en lugar de campos de texto -->
                <v-autocomplete
                  v-model="formData.cliente_id"
                  :items="clientes"
                  item-title="nombre"
                  item-value="cliente_id"
                  label="Seleccionar Cliente"
                  :loading="loading.clientes"
                  required
                ></v-autocomplete>
                <v-autocomplete
                  v-model="formData.empresaId"
                  :items="empresas"
                  item-title="nombre"
                  item-value="empresaId"
                  label="Seleccionar Empresa"
                  :loading="loading.empresas"
                  required
                ></v-autocomplete>
                <v-autocomplete
                  v-model="formData.repartidor_id"
                  :items="repartidores"
                  item-title="nombre"
                  item-value="repartidor_id"
                  label="Seleccionar Repartidor"
                  :loading="loading.repartidores"
                  required
                ></v-autocomplete>
                
                <v-divider class="my-4"></v-divider>
                <h3 class="text-h6 mb-2">Productos del Pedido</h3>
                <div v-for="(item, index) in formData.productos" :key="index" class="d-flex align-center ga-2 mb-2">
                  <v-text-field v-model.number="item.producto_id" label="ID Producto" dense hide-details></v-text-field>
                  <v-text-field v-model.number="item.cantidad" label="Cantidad" dense hide-details></v-text-field>
                  <v-btn icon small @click="removeProduct(index)" v-if="formData.productos.length > 1">
                    <v-icon>mdi-minus</v-icon>
                  </v-btn>
                </div>
                <v-btn small @click="addProduct">Añadir Producto</v-btn>
              </v-form>
              <v-card-actions class="mt-6">
                <v-spacer />
                <v-btn color="primary" :loading="loading.submit" @click="submitPedido">
                  Registrar y Calcular Ruta
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>

          <!-- Columna del Mapa -->
          <v-col cols="12" md="7">
            <v-card elevation="2">
              <v-card-title class="pa-4">Ubicaciones y Ruta Estimada</v-card-title>
              <v-card-text>
                <div id="pedido-map" style="height: 500px; width: 100%; border: 1px solid #ccc;"></div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted, nextTick, watch } from 'vue';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import { createPedidoCompleto } from '@/api/pedidos';
import { getClientes } from '@/api/clientes';
import { getAllEmpresas } from '@/api/empresas';
import { getAllRepartidores } from '@/api/repartidores';

const loading = ref({
  clientes: false,
  empresas: false,
  repartidores: false,
  submit: false
});
const form = ref(null);
const pedidoRegistrado = ref(null);
let map = null;
let clienteMarker = null;
let empresaMarker = null;
let rutaLayer = null;

// Listas para los menús desplegables
const clientes = ref([]);
const empresas = ref([]);
const repartidores = ref([]);

const formData = ref({
  cliente_id: null,
  empresaId: null, // <-- CAMBIO 1: Usar camelCase aquí
  repartidor_id: null,
  estado: 'Pendiente',
  productos: [{ producto_id: null, cantidad: 1 }],
});

// Cargar datos para los menús al montar el componente
onMounted(async () => {
  initMap();
  
  loading.value.clientes = true;
  loading.value.empresas = true;
  loading.value.repartidores = true;

  try {
    const [clientesData, empresasData, repartidoresData] = await Promise.all([
      getClientes(),
      getAllEmpresas(),
      getAllRepartidores()
    ]);
    clientes.value = clientesData;
    empresas.value = empresasData;
    repartidores.value = repartidoresData;
  } catch (error) {
    console.error("Error cargando datos iniciales:", error);
    alert("No se pudieron cargar los datos para los menús desplegables.");
  } finally {
    loading.value.clientes = false;
    loading.value.empresas = false;
    loading.value.repartidores = false;
  }
});

// Watcher para el cliente seleccionado
watch(() => formData.value.cliente_id, (newId) => {
  const cliente = clientes.value.find(c => c.cliente_id === newId);
  if (cliente && cliente.ubicacion) {
    const latLng = parsePointWKT(cliente.ubicacion);
    if (clienteMarker) {
      clienteMarker.setLatLng(latLng);
    } else {
      clienteMarker = L.marker(latLng, { title: 'Cliente' }).addTo(map);
    }
    clienteMarker.bindPopup(`<b>Cliente:</b> ${cliente.nombre}`).openPopup();
    map.panTo(latLng);
  }
});

// Watcher para la empresa seleccionada
watch(() => formData.value.empresaId, (newId) => { // <-- CAMBIO 2: Observar la propiedad camelCase
  const empresa = empresas.value.find(e => e.empresaId === newId); // <-- CAMBIO 3: Buscar usando camelCase
  if (empresa && empresa.ubicacion) {
    const latLng = parsePointWKT(empresa.ubicacion);
    if (empresaMarker) {
      empresaMarker.setLatLng(latLng);
    } else {
      empresaMarker = L.marker(latLng, { title: 'Empresa' }).addTo(map);
    }
    empresaMarker.bindPopup(`<b>Empresa:</b> ${empresa.nombre}`).openPopup();
    map.panTo(latLng);
  }
});

function addProduct() {
  formData.value.productos.push({ producto_id: null, cantidad: 1 });
}

function removeProduct(index) {
  formData.value.productos.splice(index, 1);
}

async function submitPedido() {
  const { valid } = await form.value.validate();
  if (!valid) return;

  loading.value.submit = true;
  pedidoRegistrado.value = null;
  if (rutaLayer) rutaLayer.remove();

  // Al enviar, el DTO del backend espera 'empresa_id', así que hacemos la conversión aquí.
  const dto = {
    cliente_id: formData.value.cliente_id,
    empresa_id: formData.value.empresaId, // <-- CAMBIO 4: Mapear de camelCase a snake_case
    repartidor_id: formData.value.repartidor_id,
    estado: formData.value.estado,
    productos: formData.value.productos.map(p => p.producto_id),
    cantidades: formData.value.productos.map(p => p.cantidad),
  };

  try {
    const result = await createPedidoCompleto(dto);
    pedidoRegistrado.value = result;
    alert(`Pedido registrado con ID: ${result.pedido_id}. Mostrando ruta...`);
    drawRoute();
  } catch (error) {
    console.error("Error al registrar el pedido:", error);
    alert("No se pudo registrar el pedido. Revise la consola para más detalles.");
  } finally {
    loading.value.submit = false;
  }
}

function initMap() {
  if (map) return;
  map = L.map('pedido-map').setView([-33.455, -70.685], 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
}

function drawRoute() {
  if (pedidoRegistrado.value?.ruta_estimada) {
    const latLngs = parseLineStringWKT(pedidoRegistrado.value.ruta_estimada);
    if (latLngs.length > 0) {
      rutaLayer = L.polyline(latLngs, { color: 'red', weight: 5 }).addTo(map);
      map.fitBounds(rutaLayer.getBounds());
    }
  }
}

function parsePointWKT(wkt) {
  if (!wkt || !wkt.toUpperCase().startsWith("POINT")) return null;
  const coordsStr = wkt.substring(wkt.indexOf('(') + 1, wkt.lastIndexOf(')'));
  const [lng, lat] = coordsStr.trim().split(' ').map(Number);
  return [lat, lng]; // Leaflet usa [lat, lng]
}

function parseLineStringWKT(wkt) {
  if (!wkt || !wkt.toUpperCase().startsWith("LINESTRING")) return [];
  const coordsStr = wkt.substring(wkt.indexOf('(') + 1, wkt.lastIndexOf(')'));
  return coordsStr.split(',').map(pair => {
    const [lng, lat] = pair.trim().split(' ').map(Number);
    return [lat, lng];
  });
}
</script>