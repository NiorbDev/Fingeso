<script setup>
import { ref } from 'vue'
import AppSidebar from './AppSidebar.vue'
import InstitutionalHeader from './InstitutionalHeader.vue'
import { toast } from '../toast'

defineProps({ breadcrumb: { type: String, default: 'INICIO' } })
const menuOpen = ref(false)

function showNotifications() {
  toast.show('Tienes 2 novedades. Las notificaciones detalladas estarán disponibles próximamente.', {
    eyebrow: 'BANDEJA DE NOTIFICACIONES',
  })
}
</script>

<template>
  <div class="app-shell">
    <InstitutionalHeader
      :breadcrumb="breadcrumb"
      @toggle-menu="menuOpen = !menuOpen"
      @open-notifications="showNotifications"
    />
    <div class="app-shell__body">
      <AppSidebar :open="menuOpen" @close="menuOpen = false" />
      <main class="app-main">
        <slot />
      </main>
    </div>
    <footer class="app-footer">
      <strong>PGT / USACH</strong>
      <span>Departamento de Ingeniería Informática · 2026</span>
      <span>Plataforma académica de demostración</span>
    </footer>
  </div>
</template>
