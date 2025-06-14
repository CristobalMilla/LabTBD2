import axiosTasks from "./axiosTask";
import axiosInstance from "./axiosInstance";

export const getTasks = async () => {
  const response = await axiosTasks.get("/tareas");
  return response.data;
};

export const getTaskById = async (taskId) => {
  const response = await axiosTasks.get(`/tareas/${taskId}`);
  return response.data; // Asegúrate de retornar los datos
}

export const createTask = async (taskData) => {
  const response = await axiosTasks.post("/tareas", taskData);
  return response.data;
};

// Puedes incluir consultas específicas para las preguntas del punto 6:
export const getTasksBySector = async (sectorId) => {
  const response = await axiosTasks.get(`/statistics/tasks-by-sector/${sectorId}`);
  return response.data;
};

//Pregunta 7

export const getAllTasksPerUserPerSector = async () => {
  const response = await axiosTasks.get("/tareas/allTareasPerUserPerSector");
  return response.data;
};

//Pregunta 8

export const getSectorMostCompletedByUser = async () => {
    const usuario_local = JSON.parse(localStorage.getItem("user"));
  const response = await axiosInstance.get("/tareas/SectorMostCompletedByUser/" + usuario_local.id_usuario);
  return response.data;
};

//Pregunta 9

export const getAverageCompletedDistance = async () => {
  const usuario_local = JSON.parse(localStorage.getItem("user"));
  const response = await axiosInstance.get("/tareas/AverageCompletedUser/" + usuario_local.id_usuario);
  return response.data;
};

export const getAverageDistance = async (userId) => {
    try {
        const response = await axiosTasks.get(`/tareas/promedio-distancia/${userId}`);
        return response.data;
    } catch (error) {
        console.error('Error al obtener el promedio de distancia:', error);
        throw error;
    }
};

export const markTaskAsDone = async (taskId) => {
  // Corregir el endpoint para que coincida con el backend
  const response = await axiosTasks.put(`/tareas/${taskId}/complete`);
  return response.data;
}

export const updateTask = async (taskId, taskData) => {
  const response = await axiosTasks.put(`/tareas/${taskId}`, taskData);
  return response.data;
};

export const deleteTask = async (taskId) => {
  const response = await axiosTasks.delete(`/tareas/${taskId}`);
  return response.data;
};

// Asegúrate de que esta función esté presente y exportada
export const getUserTasks = async (userId) => {
  const response = await axiosTasks.get(`/tareas/usuario/${userId}`);
  return response.data;
};


