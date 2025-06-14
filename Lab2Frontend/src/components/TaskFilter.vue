<template>
  <div class="task-filter">
    <div class="filter-header">
      <h3>Filtrar Tareas</h3>
      <button @click="clearFilters" class="btn-clear">
        Limpiar Filtros
      </button>
    </div>

    <div class="filter-content">
      <!-- Filtro por palabras clave -->
      <div class="filter-group">
        <label for="search">Buscar por palabra clave:</label>
        <input
            id="search"
            v-model="filters.searchText"
            type="text"
            placeholder="Buscar en título o descripción..."
            class="filter-input"
        />
      </div>

      <!-- Filtro por estado -->
      <div class="filter-group">
        <label for="estado">Estado:</label>
        <select
            id="estado"
            v-model="filters.estado"
            class="filter-select"
        >
          <option value="">Todos los estados</option>
          <option value="pendiente">Pendiente</option>
          <option value="completada">Completada</option>
          <option value="fallida">Fallida</option>
        </select>
      </div>

      <!-- Indicadores de filtros activos -->
      <div v-if="hasActiveFilters" class="active-filters">
        <span class="active-filters-label">Filtros activos:</span>

        <span v-if="filters.searchText" class="filter-tag">
          <span>Búsqueda: "{{ filters.searchText }}"</span>
          <button @click="clearSearchFilter" class="remove-filter">×</button>
        </span>

        <span v-if="filters.estado" class="filter-tag">
          <span>Estado: {{ filters.estado }}</span>
          <button @click="clearEstadoFilter" class="remove-filter">×</button>
        </span>
      </div>

      <!-- Contador de resultados -->
      <div class="results-count">
        <p>
          <strong>{{ filteredCount }}</strong>
          {{ filteredCount === 1 ? 'tarea encontrada' : 'tareas encontradas' }}
          {{ totalCount > 0 ? `de ${totalCount} total` : '' }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskFilter',
  props: {
    tasks: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      filters: {
        searchText: '',
        estado: ''
      }
    }
  },
  computed: {
    hasActiveFilters() {
      return this.filters.searchText.trim() !== '' || this.filters.estado !== ''
    },
    filteredTasks() {
      let filtered = [...this.tasks]

      // Filtro por texto de búsqueda
      if (this.filters.searchText.trim() !== '') {
        const searchTerm = this.filters.searchText.toLowerCase().trim()
        filtered = filtered.filter(task => {
          const titulo = (task.titulo || '').toLowerCase()
          const descripcion = (task.descripcion || '').toLowerCase()
          return titulo.includes(searchTerm) || descripcion.includes(searchTerm)
        })
      }

      // Filtro por estado
      if (this.filters.estado !== '') {
        filtered = filtered.filter(task => task.estado && task.estado.toLowerCase() === this.filters.estado.toLowerCase());
      }

      return filtered
    },
    filteredCount() {
      return this.filteredTasks.length;
    },
    totalCount() {
      return this.tasks.length;
    }
  },
  watch: {

    filteredTasks(newFilteredTasks) {
      this.$emit('filtered-tasks', newFilteredTasks);
    }
  },
  mounted() {

    if (this.tasks.length > 0) {
        this.$emit('filtered-tasks', this.filteredTasks);
    }
  },
  methods: {
    clearFilters() {
      this.filters.searchText = ''
      this.filters.estado = ''

    },
    clearSearchFilter() {
      this.filters.searchText = ''
    },
    clearEstadoFilter() {
      this.filters.estado = ''
    },
  }
}
</script>

<style scoped>
.task-filter {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.filter-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  flex-wrap: wrap;
  gap: 10px;
}

.filter-header h3 {
  margin: 0;
  color: #495057;
  font-size: 1.1em;
}

.btn-clear {
  background-color: #6c757d;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s ease;
}

.btn-clear:hover {
  background-color: #545b62;
}

.filter-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.filter-group {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.filter-group label {
  font-weight: 600;
  color: #495057;
  font-size: 14px;
}

.filter-input, .filter-select {
  padding: 8px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.filter-input:focus, .filter-select:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
}

.filter-select {
  background-color: white;
  cursor: pointer;
}

.active-filters {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
  padding: 12px;
  background-color: #e3f2fd;
  border-radius: 6px;
  border-left: 4px solid #2196f3;
}

.active-filters-label {
  font-weight: 600;
  color: #1976d2;
  font-size: 14px;
}

.filter-tag {
  display: inline-flex;
  align-items: center;
  background-color: #2196f3;
  color: white;
  padding: 4px 8px;
  border-radius: 16px;
  font-size: 12px;
  gap: 6px;
}

.remove-filter {
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  font-size: 16px;
  line-height: 1;
  padding: 0;
  width: 16px;
  height: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background-color 0.2s ease;
}

.remove-filter:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.results-count {
  padding: 8px 12px;
  background-color: #e8f5e8;
  border-radius: 4px;
  border-left: 3px solid #28a745;
}

.results-count p {
  margin: 0;
  color: #155724;
  font-size: 14px;
}

/* Responsive */
@media (max-width: 768px) {
  .filter-header {
    flex-direction: column;
    align-items: stretch;
  }

  .btn-clear {
    align-self: flex-end;
  }

  .active-filters {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-tag {
    justify-content: space-between;
  }
}

@media (min-width: 769px) {
  .filter-content {
    display: grid;
    grid-template-columns: 1fr 200px;
    gap: 16px;
    align-items: start;
  }

  .active-filters {
    grid-column: 1 / -1;
  }

  .results-count {
    grid-column: 1 / -1;
  }
}
</style>