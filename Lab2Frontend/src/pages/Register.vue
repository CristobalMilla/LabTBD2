<template>
  <v-container fluid class="register-container pa-0 fill-height">
    <v-row no-gutters style="min-height: 100vh;">
      <!-- Panel Izquierdo -->
      <v-col cols="12" md="6" class="gradient-background d-flex align-center">
        <v-container>
          <h1 class="text-h2 font-weight-bold text-white mb-4">¡Bienvenido!</h1>
          <h2 class="text-h5 font-weight-regular text-white">
            ¡Regístrate para administrar los pedidos!
          </h2>
        </v-container>
      </v-col>

      <!-- Panel Derecho -->
      <v-col cols="12" md="6" class="d-flex align-center justify-center">
        <v-card class="register-card mx-auto" max-width="600" elevation="8" rounded="lg">
          <v-card-item class="pb-0">
            <v-card-title class="text-h4 font-weight-bold text-primary mb-2 text-center">
              Crear una cuenta
            </v-card-title>
            <v-card-subtitle class="text-body-1 text-medium-emphasis text-center">
              Complete los campos para el registro
            </v-card-subtitle>
          </v-card-item>

          <v-card-text class="pt-4">
            <v-form @submit.prevent="handleRegister" v-model="isFormValid">
              <v-text-field
                v-model="rut"
                label="RUT"
                variant="outlined"
                :rules="[v => !!v || 'El RUT es requerido']"
                required
              ></v-text-field>

              <v-text-field
                v-model="nameParam"
                label="Nombre"
                variant="outlined"
                :rules="[v => !!v || 'El nombre es requerido']"
                required
              ></v-text-field>

              <v-text-field
                v-model="email"
                label="Email"
                variant="outlined"
                :rules="[v => !!v || 'El email es requerido']"
                required
              ></v-text-field>

              <v-text-field
                v-model="phone"
                label="Teléfono"
                variant="outlined"
                :rules="[v => !!v || 'El teléfono es requerido']"
                required
              ></v-text-field>

              <v-text-field
                v-model="birthdate"
                label="Fecha de Nacimiento"
                variant="outlined"
                type="date"
                :rules="[v => !!v || 'La fecha de nacimiento es requerida']"
                required
              ></v-text-field>

              <v-text-field
                v-model="password"
                label="Contraseña"
                :type="showPassword ? 'text' : 'password'"
                variant="outlined"
                :append-inner-icon="showPassword ? 'mdi-eye-off' : 'mdi-eye'"
                @click:append-inner="showPassword = !showPassword"
                :rules="[v => !!v || 'La contraseña es requerida']"
                required
              ></v-text-field>

              <v-select
                v-model="role"
                :items="roles"
                label="Rol"
                variant="outlined"
                required
              ></v-select>

              <v-alert v-if="error" type="error" variant="tonal" class="mt-4 mb-4">
                {{ error }}
              </v-alert>

              <v-btn
                type="submit"
                color="primary"
                size="large"
                block
                :loading="loading"
                :disabled="!isFormValid"
                class="mt-4"
              >
                Registrar
              </v-btn>

              <div class="text-center mt-6">
                <span class="text-medium-emphasis">¿Ya tienes una cuenta?</span>
                <v-btn variant="text" color="primary" class="ml-2" @click="$router.push('/login')">
                  Inicia sesión aquí
                </v-btn>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { registerUser } from "@/services/auth";

export default {
  name: "Register",
  data() {
    return {
      rut: "",
      nameParam: "",
      email: "",
      phone: "",
      birthdate: "",
      password: "",
      role: "", // Ejemplo: "TRABAJADOR"
      roles: ["TRABAJADOR", "ADMIN"], // Ajusta los roles disponibles según necesidad
      error: "",
      loading: false,
      showPassword: false,
      isFormValid: false
    };
  },
  methods: {
    async handleRegister() {
      if (!this.isFormValid) return;
      
      this.loading = true;
      this.error = "";

      const userData = {
        rut: this.rut,
        nameParam: this.nameParam,
        email: this.email,
        phone: this.phone,
        birthdate: this.birthdate,
        password: this.password,
        role: this.role
      };

      try {
        await registerUser(userData);
        this.$router.push({
          path: '/login',
          query: { registered: 'true' }
        });
      } catch (error) {
        console.error("Error en el registro:", error);
        this.error = error.message;
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.register-container {
  background-color: #f0f2f5;
}

.gradient-background {
  position: relative;
  overflow: hidden;
  background: radial-gradient(
    circle,
    #558ffa,    /* Azul claro */
    #4a5ab9,    /* Azul medio */
    #3ca6a6,    /* Verde agua */
    #58338a,    /* Morado medio */
    #2c747e,    /* Verde azulado */
    #5481ff     /* Azul brillante */
  );
  background-size: 300% 300%;
  animation: gradient 25s ease infinite;
}

@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

.gradient-background::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: radial-gradient(
    circle at center,
    rgba(255, 255, 255, 0.15) 0%,
    transparent 70%
  );
  animation: shine 10s ease-in-out infinite;
}

@keyframes shine {
  0%, 100% {
    opacity: 0.7;
  }
  50% {
    opacity: 0.3;
  }
}

.register-card {
  width: 100%;
  margin: 2rem;
  padding: 1rem;
  backdrop-filter: blur(8px);
}

#map {
  height: 250px;
  width: 100%;
  border: 1px solid rgba(0, 0, 0, 0.12);
  border-radius: 8px;
  margin-bottom: 8px;
}

@media (max-width: 600px) {
  .register-card {
    margin: 1rem;
    padding: 0.5rem;
  }
  
  #map {
    height: 200px;
  }
}
</style>
