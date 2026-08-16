<script setup>
import { LogOut, X } from '@lucide/vue'
import { useRoute, useRouter } from 'vue-router'
import { navigationItems } from '../modules'
import { session } from '../session'
import { toast } from '../toast'

defineProps({ open: Boolean })
const emit = defineEmits(['close'])
const route = useRoute()
const router = useRouter()

const destinations = {
  catalog: '/catalogo',
  applications: '/mis-postulaciones',
}

function isActive(item) {
  if (item.active) return route.path === '/inicio'
  return destinations[item.action] === route.path
}

function selectItem(item) {
  if (item.upcoming) {
    toast.show(`${item.label} estará disponible próximamente.`, {
      eyebrow: 'MÓDULO EN PREPARACIÓN',
    })
  } else if (item.active) {
    router.push('/inicio')
  } else if (destinations[item.action]) {
    router.push(destinations[item.action])
  }
  emit('close')
}

function logout() {
  session.logout()
  router.push('/ingresar')
}
</script>

<template>
  <Transition name="overlay">
    <button v-if="open" class="sidebar-overlay" aria-label="Cerrar menú" @click="$emit('close')"></button>
  </Transition>
  <aside class="app-sidebar" :class="{ 'app-sidebar--open': open }">
    <div class="app-sidebar__mobile-heading">
      <strong>MENÚ</strong>
      <button aria-label="Cerrar menú" @click="$emit('close')"><X :size="22" /></button>
    </div>
    <p class="app-sidebar__label">ESPACIO ESTUDIANTE</p>
    <nav aria-label="Navegación principal">
      <button
        v-for="item in navigationItems"
        :key="item.label"
        class="sidebar-link"
        :class="{ 'sidebar-link--active': isActive(item) }"
        @click="selectItem(item)"
      >
        <component :is="item.icon" :size="20" stroke-width="2.2" />
        <span>{{ item.label }}</span>
        <small v-if="item.upcoming">PRONTO</small>
      </button>
    </nav>
    <div class="app-sidebar__support">
      <span>¿NECESITAS AYUDA?</span>
      <strong>soporte.pgt@usach.cl</strong>
    </div>
    <button class="sidebar-logout" @click="logout">
      <LogOut :size="19" />
      CERRAR SESIÓN
    </button>
  </aside>
</template>
