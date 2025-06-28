import axiosInstance from "./axiosInstance";

export const getOpinionById = async (id) => {
  const response = await axiosInstance.get(`/opiniones/${id}`);
  return response.data;
};

export const createOpinion = async (opinion) => {
  const response = await axiosInstance.post("/opiniones", opinion);
  return response.data;
};

export const updateOpinion = async (id, opinion) => {
  const response = await axiosInstance.put(`/opiniones/${id}`, opinion);
  return response.data;
};

export const getPromedioPuntuacionXEmpresa = async () => {
  const response = await axiosInstance.get('/opiniones/promedioXempresa');
  return response.data;
};

export const getOpinionStatsPorHora = async () => {
  const response = await axiosInstance.get('/opiniones/stats/por-hora');
  return response.data;
};