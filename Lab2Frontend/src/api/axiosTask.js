import axios from "axios";

const baseURL = import.meta.env.VITE_API_BASE_URL;

const axiosTasks = axios.create({
  baseURL: `${baseURL}/api`,
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor para incluir el token JWT si es necesario
axiosTasks.interceptors.request.use((config) => {
  const userString = localStorage.getItem("user");
  if (userString) {
    const user = JSON.parse(userString);
    if (user && user.token) {
      config.headers.Authorization = `Bearer ${user.token}`;
    }
  }
  return config;
});

export default axiosTasks;