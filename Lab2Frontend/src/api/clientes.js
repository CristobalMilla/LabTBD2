import axiosInstance from "./axiosInstance";
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

export const getClientes = async () => {
  const response = await axiosInstance.get("/clientes/all");
  return response.data;
};

export const clientesNoCercanosAEmpresas = async () => {
  const response = await axiosInstance.get("/clientes/clientesNoCercanosAEmpresas");
  return response.data;
};

export const obtenerZonaDeCliente = async (clienteId) => {
  const response = await axiosInstance.get(`/zonasCobertura/zona-cliente/${clienteId}`);
  return response.data;
};