<script setup>
import {
  ArrowLeft,
  ArrowRight,
  Check,
  Clock3,
  Mail,
  MapPin,
  UserRound,
  UsersRound,
} from '@lucide/vue'
import { computed, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppShell from '../components/AppShell.vue'
import StatusBadge from '../components/StatusBadge.vue'
import { api } from '../api'

const route = useRoute()
const router = useRouter()
const topic = ref(null)
const loading = ref(true)

onMounted(async () => {
  topic.value = await api.getTopic(route.params.id)
  loading.value = false
  if (!topic.value) router.replace('/catalogo')
})

const availableSlots = computed(() => topic.value ? topic.value.totalSlots - topic.value.reservedSlots : 0)
</script>

<template>
  <AppShell breadcrumb="CATÁLOGO / DETALLE DEL TEMA">
    <div v-if="loading" class="detail-loading"><span></span><span></span></div>
    <template v-else-if="topic">
      <button class="back-link" @click="router.push('/catalogo')"><ArrowLeft :size="19" /> VOLVER AL CATÁLOGO</button>

      <section class="topic-detail-hero">
        <div class="topic-detail-hero__index"><span>TEMA</span><strong>{{ String(topic.id).padStart(2, '0') }}</strong></div>
        <div class="topic-detail-hero__content">
          <div class="topic-detail-hero__meta">
            <StatusBadge :label="topic.area" tone="dark" />
            <span>{{ topic.code }}</span>
          </div>
          <h1>{{ topic.title }}</h1>
          <p>{{ topic.summary }}</p>
          <div class="topic-detail-hero__tags">
            <span><UsersRound :size="18" /> {{ availableSlots }} CUPOS DISPONIBLES</span>
            <span><UserRound :size="18" /> {{ topic.modality }}</span>
            <span><Clock3 :size="18" /> {{ topic.dedication }}</span>
          </div>
        </div>
      </section>

      <div class="topic-detail-layout">
        <div class="topic-detail-main">
          <section class="detail-section">
            <div class="detail-section__number">01</div>
            <div><p class="eyebrow">CONTEXTO DEL PROYECTO</p><h2>Sobre este tema</h2><p class="detail-copy">{{ topic.description }}</p></div>
          </section>
          <section class="detail-section">
            <div class="detail-section__number">02</div>
            <div><p class="eyebrow">RESULTADOS ESPERADOS</p><h2>Objetivos</h2><ul class="check-list"><li v-for="objective in topic.objectives" :key="objective"><Check :size="18" />{{ objective }}</li></ul></div>
          </section>
          <section class="detail-section">
            <div class="detail-section__number">03</div>
            <div><p class="eyebrow">ANTES DE POSTULAR</p><h2>Requisitos del estudiante</h2><ul class="check-list check-list--orange"><li v-for="requirement in topic.requirements" :key="requirement"><Check :size="18" />{{ requirement }}</li></ul></div>
          </section>
          <section class="detail-section">
            <div class="detail-section__number">04</div>
            <div><p class="eyebrow">HERRAMIENTAS SUGERIDAS</p><h2>Tecnologías</h2><div class="technology-list"><span v-for="technology in topic.technologies" :key="technology">{{ technology }}</span></div></div>
          </section>
        </div>

        <aside class="topic-detail-aside">
          <section class="professor-card">
            <p class="eyebrow eyebrow--light">PROFESOR GUÍA</p>
            <div class="professor-card__avatar">{{ topic.professor.split(' ').slice(-1)[0].slice(0, 1) }}</div>
            <h2>{{ topic.professor }}</h2>
            <p>{{ topic.professorDepartment }}</p>
            <a :href="`mailto:${topic.professorEmail}`"><Mail :size="17" /> {{ topic.professorEmail }}</a>
          </section>
          <section class="facts-card">
            <div><MapPin :size="20" /><span><small>LUGAR DE TRABAJO</small><strong>{{ topic.location }}</strong></span></div>
            <div><Clock3 :size="20" /><span><small>DEDICACIÓN ESTIMADA</small><strong>{{ topic.dedication }}</strong></span></div>
            <div><UsersRound :size="20" /><span><small>MODALIDAD</small><strong>{{ topic.modality }}</strong></span></div>
          </section>
          <section class="apply-card">
            <div><span>DISPONIBILIDAD</span><strong>{{ availableSlots }}/{{ topic.totalSlots }}</strong></div>
            <p>Tu perfil cumple las condiciones para enviar una solicitud.</p>
            <button class="button button--primary" @click="router.push(`/postular/${topic.id}`)">POSTULAR A ESTE TEMA <ArrowRight :size="21" /></button>
            <small>La postulación quedará pendiente de revisión por el profesor.</small>
          </section>
        </aside>
      </div>
    </template>
  </AppShell>
</template>
