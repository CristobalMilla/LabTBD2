import { login, register } from "@/auth/auth";
import { saveToken } from "./jwt";

export const loginUser = async (credentials) => {
    try {
        const data = await login(credentials);
        saveToken(data.token); // Guarda el JWT en `localStorage`

        // Crea un objeto `user` con los datos del usuario
        const user = {
            token: data.token,
            id_usuario: data.id_usuario,
            nickname: data.nickname,
            role: data.role
        };

        // Guarda en localStorage
        localStorage.setItem("user", JSON.stringify(user));

        // Imprime en consola
        console.log("LocalStorage.\nUser logged in:", user);

        // Devuelve el usuario
        return data.user;

    } catch (error) {
        console.error("Error en loginUser:", error);

        let errorMsg = "Error desconocido.";
        let errorCode = null;

        // Error por falta de conexión o CORS
        if (error.code === "ERR_NETWORK") {
            errorMsg = "No se puede conectar con el servidor. ¿Está corriendo el backend?";
        } else if (error.response) {
            // Error del backend con respuesta HTTP
            errorCode = error.response.status;
            const msg = error.response.data?.message || "";

            switch (errorCode) {
                case 400:
                    errorMsg = "Datos de inicio de sesión inválidos.";
                    break;
                case 401:
                    errorMsg = msg.includes("contraseña")
                        ? "Contraseña incorrecta."
                        : "Contraseña incorrecta. Verifique sus credenciales.";
                    break;
                case 404:
                    errorMsg = "El usuario no existe. Por favor, verifique el nombre de usuario.";
                    break;
                default:
                    errorMsg = msg || "Error en el servidor. Por favor, intente más tarde.";
            }
        } 

        // Crear un error personalizado con información adicional
        const customError = new Error(errorMsg);
        customError.code = errorCode;
        customError.originalError = error;
        throw customError;
    }
};


export const logoutUser = () => {
  localStorage.removeItem("user"); // Elimina el usuario del localStorage
};

export const registerUser = async (userData) => {
    try {
        const data = await register(userData);
        if (data.token) {
            saveToken(data.token);
        }

        // Crea un objeto `user` con los datos del usuario
        const user = {
            token: data.token,
            id_usuario: data.id_usuario,
            nickname: data.nickname,
            role: data.role
        };

        // Guarda en localStorage
        localStorage.setItem("user", JSON.stringify(user));

        // Imprime en consola
        console.log("LocalStorage.\nUser registered:", user);

        // Devuelve el usuario
        return { success: true, user: data.user };

    } catch (error) {
        console.error("Error en RegisterUser:", error);

        let errorMsg = "Error desconocido.";
        let errorCode = null;

        if (error.response) {
            errorCode = error.response.status;
            const msg = error.response.data?.message || "";

            switch (errorCode) {
                case 400:
                    errorMsg = msg.includes("Faltan campos")
                        ? "Debe completar todos los campos requeridos."
                        : "Datos de registro inválidos.";
                    break;
                case 409:
                    errorMsg = "Este nombre de usuario ya está registrado. Por favor, elige otro.";
                    break;
                case 422:
                    errorMsg = "Los datos proporcionados no son válidos. Verifica la información ingresada.";
                    break;
                default:
                    errorMsg = msg || "Error en el servidor. Por favor, intente más tarde.";
            }
        } else if (error.code === "ERR_NETWORK") {
            errorMsg = "No se puede conectar con el servidor. Verifique su conexión a internet.";
        } else if (error.message) {
            errorMsg = error.message;
        }

        // Crear un error personalizado con información adicional
        const customError = new Error(errorMsg);
        customError.code = errorCode;
        customError.originalError = error;
        throw customError;
    }
};
