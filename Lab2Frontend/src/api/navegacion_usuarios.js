import axiosInstance from "./axiosInstance";

export const getClientesQueBuscaronPeroNoCompraron = async () => {
  const response = await axiosInstance.get('/navegacion/buscaronPeroNoCompraron');
  return response.data;
}