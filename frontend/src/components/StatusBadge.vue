<script setup>
defineProps({ label: { type: String, required: true }, tone: { type: String, default: 'teal' } })
</script>

<template>
  <span class="status-badge" :class="`status-badge--${tone}`">
    <i></i>{{ label }}
  </span>
</template>

