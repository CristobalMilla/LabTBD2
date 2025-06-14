import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL;

const axiosInstance = axios.create({
  baseURL: `${baseURL}/api`,
  headers: {
    "Content-Type": "application/json",
    // La siguiente línea se actualizará mediante el interceptor
    // Authorization: `Bearer ${JSON.parse(localStorage.getItem("user"))?.token || ""}`,
  },
});

// Interceptor para incluir el token JWT
axiosInstance.interceptors.request.use((config) => {
  const userString = localStorage.getItem("user");
  if (userString) {
    const user = JSON.parse(userString);
    if (user && user.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
  }
  return config;
});

export default axiosInstance;
