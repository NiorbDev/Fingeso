<script setup>
import { ArrowRight, BookOpenText, Check, Clock3, FileCheck2, Send, UserRound } from '@lucide/vue'
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppShell from '../components/AppShell.vue'
import StatusBadge from '../components/StatusBadge.vue'
import { api } from '../api'
import { session } from '../session'

const route = useRoute()
const router = useRouter()
const applications = ref([])
const loading = ref(true)

onMounted(async () => {
  applications.value = await api.listApplications(session.state.user.id)
  loading.value = false
})

const formatDate = (value) => new Intl.DateTimeFormat('es-CL', { dateStyle: 'long', timeStyle: 'short' }).format(new Date(value))
</script>

<template>
  <AppShell breadcrumb="MIS POSTULACIONES">
    <section v-if="route.query.enviada" class="success-banner">
      <span><Check :size="30" /></span>
      <div><p class="eyebrow">POSTULACIÓN REGISTRADA</p><h2>Tu solicitud fue enviada correctamente.</h2><p>El profesor guía fue notificado. Puedes seguir el estado desde esta página.</p></div>
      <strong>#{{ route.query.enviada }}</strong>
    </section>

    <section class="page-hero page-hero--applications">
      <div><p class="eyebrow">TRAZABILIDAD DE SOLICITUDES</p><h1>Mis postulaciones.</h1><p>Revisa cada solicitud y su avance dentro del proceso de selección.</p></div>
      <div class="page-hero__number"><span>ACTIVAS</span><strong>{{ String(applications.length).padStart(2, '0') }}</strong></div>
    </section>

    <div v-if="loading" class="detail-loading"><span></span><span></span></div>
    <section v-else-if="!applications.length" class="applications-empty">
      <span><BookOpenText :size="44" /></span>
      <p class="eyebrow">AÚN NO HAY SOLICITUDES</p>
      <h2>Tu próximo tema puede estar en el catálogo.</h2>
      <p>Explora propuestas publicadas por profesores y elige la que mejor se conecte con tus intereses.</p>
      <button class="button button--primary" @click="router.push('/catalogo')">EXPLORAR CATÁLOGO <ArrowRight :size="20" /></button>
    </section>

    <section v-else class="applications-list">
      <article v-for="application in applications" :key="application.id" class="application-card">
        <header>
          <div><p class="eyebrow">SOLICITUD #{{ application.id }}</p><h2>{{ application.topic.title }}</h2></div>
          <StatusBadge :label="application.state" tone="orange" />
        </header>
        <div class="application-card__meta">
          <span><UserRound :size="18" /><small>PROFESOR GUÍA</small><strong>{{ application.topic.professor }}</strong></span>
          <span><Clock3 :size="18" /><small>FECHA DE ENVÍO</small><strong>{{ formatDate(application.createdAt) }}</strong></span>
          <span><FileCheck2 :size="18" /><small>MODALIDAD</small><strong>{{ application.modality === 'GROUP' ? 'GRUPAL' : 'INDIVIDUAL' }}</strong></span>
        </div>
        <div class="application-card__timeline">
          <div class="timeline-step timeline-step--done"><span><Send :size="16" /></span><strong>SOLICITUD ENVIADA</strong><small>COMPLETADO</small></div>
          <i></i>
          <div class="timeline-step timeline-step--current"><span><Clock3 :size="16" /></span><strong>REVISIÓN DEL PROFESOR</strong><small>EN CURSO</small></div>
          <i></i>
          <div class="timeline-step"><span><Check :size="16" /></span><strong>DECISIÓN</strong><small>PENDIENTE</small></div>
        </div>
        <footer><p><strong>MENSAJE ENVIADO</strong> “{{ application.message }}”</p><button @click="router.push(`/temas/${application.topic.id}`)">VER TEMA <ArrowRight :size="17" /></button></footer>
      </article>
      <button class="button button--dark applications-list__more" @click="router.push('/catalogo')">EXPLORAR MÁS TEMAS <ArrowRight :size="19" /></button>
    </section>
  </AppShell>
</template>
