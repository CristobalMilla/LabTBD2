import axiosInstance from "./axiosInstance";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

export async function getPedidosQueCruzanMasDe2Zonas() {
  const response = await fetch(`${API_BASE_URL}/api/pedidos/pedidos-que-cruzan-mas-de-2-zonas`);
  if (!response.ok) {
    throw new Error('Error al obtener los pedidos');
  }
  return response.json();
}

export async function getFrecuenciaRutas(repartidorId) {
  const response = await axiosInstance.get(`/pedidos/frecuencia-rutas/repartidor/${repartidorId}`);
  return response.data;
}

