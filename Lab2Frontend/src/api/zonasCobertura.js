import axiosInstance from "./axiosInstance";
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

export const getZonasCobertura = async () => {
  const response = await axiosInstance.get("/zonasCobertura/all");
  return response.data;
};

export const getByClienteId = async (cliente_id) => {
  const response = await axiosInstance.get(`/zonasCobertura/getByClienteId/${cliente_id}`);
  return response.data;
};

export const isClientInZonaCobertura = async (cliente_id) => {
  const response = await axiosInstance.get(`/isClientInZonaCobertura/${cliente_id}`);
  return response.data;
};

export async function getAllByIds(ids) {
  const response = await fetch(`${API_BASE_URL}/api/zonasCobertura/byListIds`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(ids)});
  if (!response.ok) {
    throw new Error('Error al obtener la zona');
  }
  return response.json();
}

// 7. 1era extra
export const obtenerZonaDeCliente = async (clienteId) => {
  const response = await axiosInstance.get(`/zonasCobertura/zona-cliente/${clienteId}`);
  return response.data;
};