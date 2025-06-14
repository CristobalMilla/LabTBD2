const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export async function getAllVertices() {
  console.log(`Fetching vertices from ${API_BASE_URL}/api/calles-vertices/all`);
  try {
    const response = await fetch(`${API_BASE_URL}/api/calles-vertices/all`);

    if (!response.ok) {
      const errorText = await response.text();
      console.error(`Error response: ${response.status} - ${errorText}`);
      throw new Error(`Error al obtener los vértices: ${response.status}`);
    }

    const data = await response.json();
    console.log(`Recibidos ${data.length} vértices del API`);

    // Validar algunos datos para debugging
    if (data.length > 0) {
      console.log("Muestra del primer vértice:", data[0]);
    }

    return data;
  } catch (error) {
    console.error("Error en getAllVertices:", error);
    throw error;
  }
}