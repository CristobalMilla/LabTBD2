const API = import.meta.env.VITE_API_BASE_URL;

export async function getAllRepartidores() {
  const res = await fetch(`${API}/api/repartidores/`);
  if (!res.ok) throw new Error('No se pudieron cargar los repartidores');
  return res.json(); // Array de RepartidorEntity
}

export async function getDistanciaMensual(id) {
  const res = await fetch(`${API}/api/repartidores/${id}/distancia-mensual`);
  if (!res.ok) throw new Error('No se pudo obtener la distancia mensual');
  return res.json(); // un número (Double)
}