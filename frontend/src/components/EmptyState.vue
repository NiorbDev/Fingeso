<script setup>
import { SearchX } from '@lucide/vue'
defineEmits(['clear'])
</script>

<template>
  <div class="empty-state">
    <SearchX :size="46" />
    <p class="eyebrow">SIN COINCIDENCIAS</p>
    <h2>No encontramos temas para esta búsqueda.</h2>
    <p>Prueba con otro nombre, profesor o área de interés.</p>
    <button class="button button--dark" @click="$emit('clear')">LIMPIAR FILTROS</button>
  </div>
</template>
