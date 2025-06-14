<template>
  <v-container>
    <v-app-bar color="primary" dark elevation="2">
      <v-app-bar-title class="d-flex align-center">
        <v-btn variant="text" @click="$router.push('/home')" class="d-flex align-center">
          <v-icon size="24" class="mr-2">mdi-view-dashboard</v-icon>
          Home
        </v-btn>
      </v-app-bar-title>
      <v-spacer></v-spacer>
      <NotificationBadge class="mr-2" />
      <v-btn @click="logout" variant="text" prepend-icon="mdi-logout">
        Cerrar Sesión
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container class="task-details">
        <!-- Header Section -->
        <v-row class="mb-6">
          <v-col cols="12" class="d-flex justify-space-between align-center">
            <h1 class="text-h4">Lista de Tareas</h1>
            <v-btn
              color="primary"
              prepend-icon="mdi-plus"
              @click="openCreateModal"
              elevation="2"
            >
              Crear Tarea
            </v-btn>
          </v-col>
        </v-row>

        <!-- Filters Section -->
        <v-row v-if="!loading && tasks.length > 0">
          <v-col cols="12">
            <TaskFilter
              :tasks="tasks"
              @filtered-tasks="handleFilteredTasks"
              ref="taskFilter"
            />
          </v-col>
        </v-row>

        <!-- Loading State -->
        <v-row v-if="loading">
          <v-col cols="12" class="d-flex justify-center align-center pa-8">
            <v-progress-circular
              indeterminate
              color="primary"
              size="64"
            ></v-progress-circular>
          </v-col>
        </v-row>

        <!-- No Tasks State -->
        <v-row v-else-if="!loading && tasks.length === 0">
          <v-col cols="12">
            <v-alert
              type="info"
              variant="tonal"
              class="text-center"
            >
              No hay tareas disponibles
            </v-alert>
          </v-col>
        </v-row>

        <!-- No Filtered Tasks State -->
        <v-row v-else-if="!loading && filteredTasks !== null && filteredTasks.length === 0">
          <v-col cols="12">
            <v-alert
              type="warning"
              variant="tonal"
              class="text-center"
            >
              No se encontraron tareas que coincidan con los filtros aplicados
              <v-btn
                color="warning"
                variant="text"
                class="mt-2"
                @click="clearAllFilters"
              >
                Limpiar Filtros
              </v-btn>
            </v-alert>
          </v-col>
        </v-row>

        <!-- Task List -->
        <v-row v-else>
          <v-col cols="12">
            <div class="task-list">
              <TaskCard
                v-for="task in displayTasks"
                :key="task.id_tarea"
                :tarea="task"
                @task-updated="handleTaskUpdated"
                @task-completed="handleTaskCompleted"
                @task-deleted="handleTaskDeleted"
              />
            </div>
          </v-col>
        </v-row>

        <!-- Create Task Modal -->
        <TaskCreateModal
          :is-visible="showCreateModal"
          @close="closeCreateModal"
          @task-created="handleTaskCreated"
        />
      </v-container>
    </v-main>
  </v-container>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import TaskCard from "@/components/TaskCard.vue"
import TaskFilter from "@/components/TaskFilter.vue"
import TaskCreateModal from "@/components/TaskCreateModal.vue"
import NotificationBadge from '@/components/NotificationBadge.vue'
import { getTasks } from '../api/tasks'

const router = useRouter()
const authStore = useAuthStore()

const tasks = ref([])
const filteredTasks = ref(null)
const loading = ref(false)
const showCreateModal = ref(false)
const taskFilter = ref(null)


const displayTasks = computed(() => {
  if (filteredTasks.value !== null) {
    return filteredTasks.value;
  }
  return tasks.value;
})

const fetchTasks = async () => {
  loading.value = true
  filteredTasks.value = null;
  try {
    tasks.value = await getTasks()
  } catch (error) {
    console.error('Error fetching tasks:', error)
  } finally {
    loading.value = false
  }
}

const openCreateModal = () => {
  showCreateModal.value = true
}

const closeCreateModal = () => {
  showCreateModal.value = false
}

const handleTaskCreated = async (newTask) => {
  console.log('Nueva tarea creada:', newTask)
  await fetchTasks()
  if (taskFilter.value) {
    taskFilter.value.clearFilters()
  }
}

const handleTaskUpdated = (task) => {
  console.log('Tarea actualizada:', task)
}

const handleTaskCompleted = (taskId) => {
  console.log('Tarea completada:', taskId)
}

const handleFilteredTasks = (filtered) => {
  filteredTasks.value = filtered
}

const handleTaskDeleted = (taskId) => {
  console.log('Tarea eliminada:', taskId)
  tasks.value = tasks.value.filter(task => task.id_tarea !== taskId)
}

const clearAllFilters = () => {
  if (taskFilter.value) {
    taskFilter.value.clearFilters()
  }
}

const logout = () => {
  authStore.logout()
  router.push('/login')
}

onMounted(() => {
  fetchTasks()
})
</script>

<style scoped>
.task-details {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
}

.task-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.v-alert {
  margin: 20px 0;
}

h1 {
  color: var(--v-primary-base);
  font-weight: 500;
}
</style>
