import axiosInstance from "./axiosInstance";

export const getUserById = async (userId) => {
    const response = await axiosInstance.get(`/usuarios/${userId}`);
    return response.data;
};
