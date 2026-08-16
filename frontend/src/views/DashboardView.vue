<script setup>
import { ArrowRight, CalendarClock, CheckCircle2, Clock3, Sparkles } from '@lucide/vue'
import { useRouter } from 'vue-router'
import AppShell from '../components/AppShell.vue'
import ModuleCard from '../components/ModuleCard.vue'
import StatusBadge from '../components/StatusBadge.vue'
import { dashboardModules } from '../modules'
import { topics } from '../topics'
import { session } from '../session'
import { toast } from '../toast'

const router = useRouter()
const featuredTopic = topics.find((topic) => topic.featured)

function selectModule(module) {
  if (module.upcoming) {
    toast.show(`${module.title} estará disponible próximamente.`, { eyebrow: 'MÓDULO EN PREPARACIÓN' })
    return
  }
  router.push(module.action === 'catalog' ? '/catalogo' : '/mis-postulaciones')
}
</script>

<template>
  <AppShell breadcrumb="INICIO">
    <section class="dashboard-hero">
      <div>
        <p class="eyebrow">ESPACIO ESTUDIANTE / SEMESTRE 2026-1</p>
        <h1>Hola, {{ session.state.user?.firstName }}.</h1>
        <p>Tu proceso de titulación comienza con una buena decisión.</p>
      </div>
      <div class="dashboard-hero__status">
        <span>ESTADO ACADÉMICO</span>
        <StatusBadge label="HABILITADA PARA POSTULAR" tone="lime" />
      </div>
    </section>

    <section class="quick-summary" aria-label="Resumen del proceso">
      <article>
        <span class="quick-summary__icon"><CheckCircle2 :size="22" /></span>
        <div><small>PERFIL ACADÉMICO</small><strong>COMPLETO</strong></div>
        <b>100%</b>
      </article>
      <article>
        <span class="quick-summary__icon quick-summary__icon--orange"><Clock3 :size="22" /></span>
        <div><small>POSTULACIONES ACTIVAS</small><strong>NINGUNA</strong></div>
        <b>00</b>
      </article>
      <article>
        <span class="quick-summary__icon quick-summary__icon--dark"><CalendarClock :size="22" /></span>
        <div><small>CIERRE DE CATÁLOGO</small><strong>28 AGO 2026</strong></div>
        <b>13D</b>
      </article>
    </section>

    <section class="section-block">
      <div class="section-heading">
        <div><p class="eyebrow">SERVICIOS DISPONIBLES</p><h2>¿Qué necesitas hacer?</h2></div>
        <span class="section-heading__count">08 MÓDULOS</span>
      </div>
      <div class="module-grid">
        <ModuleCard v-for="module in dashboardModules" :key="module.number" :module="module" @select="selectModule" />
      </div>
    </section>

    <section class="featured-topic">
      <div class="featured-topic__stamp"><Sparkles :size="25" /><span>DESTACADO<br />DE LA SEMANA</span></div>
      <div class="featured-topic__body">
        <p class="eyebrow">{{ featuredTopic.area }} · {{ featuredTopic.code }}</p>
        <h2>{{ featuredTopic.title }}</h2>
        <p>{{ featuredTopic.summary }}</p>
        <div><strong>{{ featuredTopic.professor }}</strong><span>{{ featuredTopic.totalSlots }} CUPOS DISPONIBLES</span></div>
      </div>
      <button class="featured-topic__action" @click="router.push(`/temas/${featuredTopic.id}`)">
        REVISAR TEMA <ArrowRight :size="23" />
      </button>
    </section>
  </AppShell>
</template>
