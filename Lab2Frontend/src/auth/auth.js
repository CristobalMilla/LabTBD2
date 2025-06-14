import axiosInstance from "./axiosInstance";

export const login = async (credentials) => {
  const response = await axiosInstance.post("/auth/usuarios/login", credentials);
  return response.data;
};

export const register = async (userData) => {
  const response = await axiosInstance.post("/auth/usuarios/register", userData);
  return response.data;
};
