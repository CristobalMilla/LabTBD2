<template>
  <v-container fluid>
    <v-app-bar color="primary" dark>
      <v-btn icon @click="goBack"><v-icon>mdi-arrow-left</v-icon></v-btn>
      <v-toolbar-title>{{ isEdit ? 'Editar' : 'Crear' }} Opinión</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container class="py-8">
        <v-card elevation="2" class="pa-6">
          <v-form ref="form" lazy-validation>
            <v-textarea
              v-model="formData.comentarios"
              label="Comentario"
              required
            ></v-textarea>
            <v-text-field
              v-model.number="formData.puntuacion"
              label="Puntuación (0-10)"
              type="number"
              required
            ></v-text-field>
            <v-text-field
              v-model.number="formData.cliente_id"
              label="ID del Cliente"
              type="number"
              required
            ></v-text-field>
            <v-text-field
              v-model.number="formData.empresa_id"
              label="ID de la Empresa"
              type="number"
              required
            ></v-text-field>
          </v-form>
          <v-card-actions class="mt-6">
            <v-spacer />
            <v-btn color="primary" :loading="loading" @click="save">
              {{ isEdit ? 'Actualizar' : 'Crear' }}
            </v-btn>
            <v-btn text @click="goBack">Cancelar</v-btn>
          </v-card-actions>
        </v-card>
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getOpinionById, createOpinion, updateOpinion } from '@/api/opiniones';

const route = useRoute();
const router = useRouter();
const id = route.params.id;
const isEdit = Boolean(id);

const loading = ref(false);
const form = ref(null);
const formData = ref({
  comentarios: '',
  puntuacion: null,
  cliente_id: null,
  empresa_id: null,
});

function goBack() {
  router.push('/clientes');
}

async function loadOpinion() {
  if (!isEdit) return;
  loading.value = true;
  try {
    const opinion = await getOpinionById(id);
    formData.value = {
      opinion_id: opinion.opinion_id,
      comentarios: opinion.comentarios,
      puntuacion: opinion.puntuacion,
      cliente_id: opinion.cliente_id,
      empresa_id: opinion.empresa_id,
    };
  } catch (error) {
    console.error('Error cargando la opinión:', error);
    alert('No se pudo cargar la opinión.');
  } finally {
    loading.value = false;
  }
}

async function save() {
  const { valid } = await form.value.validate();
  if (!valid) return;

  loading.value = true;
  try {
    // Prepara el payload con los datos del formulario y añade la fecha actual en formato ISO
    const payload = {
      ...formData.value,
      fecha: new Date().toISOString(),
    };

    if (isEdit) {
      await updateOpinion(id, payload);
    } else {
      await createOpinion(payload);
    }
    goBack();
  } catch (error) {
    console.error('Error guardando la opinión:', error);
    alert('No se pudo guardar la opinión.');
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  if (isEdit) {
    loadOpinion();
  }
});
</script>