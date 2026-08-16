<script setup>
import { ArrowUpRight, LockKeyhole } from '@lucide/vue'

defineProps({ module: { type: Object, required: true } })
defineEmits(['select'])
</script>

<template>
  <button
    class="module-card"
    :class="[`module-card--${module.accent}`, { 'module-card--upcoming': module.upcoming }]"
    @click="$emit('select', module)"
  >
    <span class="module-card__number">{{ module.number }}</span>
    <span class="module-card__icon"><component :is="module.icon" :size="31" stroke-width="1.9" /></span>
    <span v-if="module.upcoming" class="module-card__flag"><LockKeyhole :size="13" /> PRÓXIMAMENTE</span>
    <strong>{{ module.title }}</strong>
    <span class="module-card__description">{{ module.description }}</span>
    <span class="module-card__cta">
      {{ module.upcoming ? 'CONOCER MÓDULO' : 'INGRESAR' }}
      <ArrowUpRight :size="18" />
    </span>
  </button>
</template>
