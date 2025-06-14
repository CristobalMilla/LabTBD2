export const getCurrentPosition = () => {
  return new Promise((resolve, reject) => {
    if (!navigator.geolocation) {
      return reject(new Error("La geolocalización no está soportada por el navegador."));
    }
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
};
