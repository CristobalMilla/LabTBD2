import axiosInstance from "./axiosInstance";
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL

export const getZonasCobertura = async () => {
  const response = await axiosInstance.get("/zonasCobertura/all");
  return response.data;
};

export const getByClienteId = async (cliente_id) => {
  const response = await axiosInstance.get(`/zonasCobertura/${cliente_id}`);
  return response.data;
};

export const isClientInZonaCobertura = async (cliente_id) => {
  const response = await axiosInstance.get(`/isClientInZonaCobertura/${cliente_id}`);
  return response.data;
};