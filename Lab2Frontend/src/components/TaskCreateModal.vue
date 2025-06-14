<template>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="600px"
  >
    <v-card>
      <v-card-title class="text-h5 bg-primary text-white pa-4">
        <span>Crear Nueva Tarea</span>
        <v-spacer></v-spacer>
        <v-btn
          icon="mdi-close"
          variant="text"
          @click="closeModal"
          color="white"
        ></v-btn>
      </v-card-title>

      <v-card-text class="pt-4">
        <v-form ref="form" v-model="valid" @submit.prevent="handleSubmit">
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="formData.titulo"
                  label="Título"
                  :rules="[v => !!v || 'El título es requerido']"
                  required
                  variant="outlined"
                  density="comfortable"
                ></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-textarea
                  v-model="formData.descripcion"
                  label="Descripción"
                  :rules="[v => !!v || 'La descripción es requerida']"
                  required
                  variant="outlined"
                  rows="3"
                  auto-grow
                ></v-textarea>
              </v-col>

              <v-col cols="12">
                <v-text-field
                  v-model="formData.fecha_vencimiento"
                  label="Fecha de Vencimiento"
                  type="date"
                  :rules="[v => !!v || 'La fecha de vencimiento es requerida']"
                  required
                  variant="outlined"
                  :min="getCurrentDate()"
                ></v-text-field>
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="formData.id_usuario"
                  label="ID Usuario"
                  type="number"
                  :rules="[
                    v => !!v || 'El ID de usuario es requerido',
                    v => v > 0 || 'El ID debe ser positivo'
                  ]"
                  required
                  variant="outlined"
                  min="1"
                ></v-text-field>
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="formData.id_sector"
                  label="ID Sector"
                  type="number"
                  :rules="[
                    v => !!v || 'El ID de sector es requerido',
                    v => v > 0 || 'El ID debe ser positivo'
                  ]"
                  required
                  variant="outlined"
                  min="1"
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>

      <v-card-actions class="pa-4">
        <v-spacer></v-spacer>
        <v-btn
          color="grey-darken-1"
          variant="text"
          @click="closeModal"
        >
          Cancelar
        </v-btn>
        <v-btn
          color="primary"
          @click="handleSubmit"
          :loading="loading"
          :disabled="!valid || loading"
        >
          Crear Tarea
        </v-btn>
      </v-card-actions>

      <!-- Snackbar para mensajes de error -->
      <v-snackbar
        v-model="showError"
        color="error"
        timeout="3000"
      >
        {{ errorMessage }}
        <template v-slot:actions>
          <v-btn
            color="white"
            variant="text"
            @click="showError = false"
          >
            Cerrar
          </v-btn>
        </template>
      </v-snackbar>
    </v-card>
  </v-dialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { createTask } from '@/api/tasks'

const props = defineProps({
  isVisible: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['close', 'task-created'])

const dialog = computed({
  get: () => props.isVisible,
  set: (value) => {
    if (!value) emit('close')
  }
})

const form = ref(null)
const valid = ref(false)
const loading = ref(false)
const showError = ref(false)
const errorMessage = ref('')

const formData = ref({
  titulo: '',
  descripcion: '',
  fecha_vencimiento: '',
  id_usuario: '',
  id_sector: ''
})

const getCurrentDate = () => {
  const today = new Date()
  return today.toISOString().split('T')[0]
}

const resetForm = () => {
  if (form.value) {
    form.value.reset()
  }
  formData.value = {
    titulo: '',
    descripcion: '',
    fecha_vencimiento: '',
    id_usuario: '',
    id_sector: ''
  }
}

const closeModal = () => {
  resetForm()
  emit('close')
}

const handleSubmit = async () => {
  if (!valid.value) return

  loading.value = true
  try {
    const response = await createTask({
      ...formData.value,
      estado: 'Pendiente'
    })
    emit('task-created', response.data)
    closeModal()
  } catch (error) {
    console.error('Error creating task:', error)
    errorMessage.value = error.response?.data?.message || 'Error al crear la tarea'
    showError.value = true
  } finally {
    loading.value = false
  }
}

watch(() => props.isVisible, (newValue) => {
  if (newValue) {

    const usuario = JSON.parse(localStorage.getItem('user'))
    if (usuario?.id_usuario) {
      formData.value.id_usuario = usuario.id_usuario
    }
  }
})
</script>

<style scoped>
.v-card-title {
  position: relative;
}

.v-card-title .v-btn {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
}
</style>