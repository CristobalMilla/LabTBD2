const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export async function getAllEmpresas() {
  const response = await fetch(`${API_BASE_URL}/api/empresas/all`);
  if (!response.ok) {
    throw new Error('Error al obtener las empresas');
  }
  return response.json();
}

export async function getEmpresaById(id) {
  const response = await fetch(`${API_BASE_URL}/api/empresas/${id}`);
  if (!response.ok) {
    throw new Error(`Error al obtener la empresa con ID ${id}`);
  }
  return response.json();
}

export async function updateEmpresa(id, empresaData) {
  const response = await fetch(`${API_BASE_URL}/api/empresas/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(empresaData),
  });
  if (!response.ok) {
    throw new Error('Error al actualizar la empresa');
  }
  return response.json();
}
