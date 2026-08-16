<script setup>
import { Search, SlidersHorizontal, X } from '@lucide/vue'
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import AppShell from '../components/AppShell.vue'
import EmptyState from '../components/EmptyState.vue'
import TopicCard from '../components/TopicCard.vue'
import { areas, professors } from '../topics'
import { api } from '../api'

const router = useRouter()
const topicList = ref([])
const loading = ref(true)
const query = ref('')
const selectedArea = ref('')
const selectedProfessor = ref('')
const filtersOpen = ref(false)

onMounted(async () => {
  topicList.value = await api.listTopics()
  loading.value = false
})

const filteredTopics = computed(() => {
  const search = query.value.trim().toLowerCase()
  return topicList.value.filter((topic) => {
    const matchesSearch = !search || `${topic.title} ${topic.summary} ${topic.professor} ${topic.area}`.toLowerCase().includes(search)
    const matchesArea = !selectedArea.value || topic.area === selectedArea.value
    const matchesProfessor = !selectedProfessor.value || topic.professor === selectedProfessor.value
    return matchesSearch && matchesArea && matchesProfessor
  })
})

const activeFilterCount = computed(() => [selectedArea.value, selectedProfessor.value].filter(Boolean).length)

function clearFilters() {
  query.value = ''
  selectedArea.value = ''
  selectedProfessor.value = ''
}
</script>

<template>
  <AppShell breadcrumb="CATÁLOGO DE TEMAS">
    <section class="page-hero page-hero--catalog">
      <div>
        <p class="eyebrow">PERIODO DE BÚSQUEDA ABIERTO · HASTA EL 28 AGO</p>
        <h1>Encuentra un tema<br /><em>que valga la pena.</em></h1>
      </div>
      <div class="page-hero__number"><span>PROPUESTAS</span><strong>{{ String(topicList.length).padStart(2, '0') }}</strong></div>
    </section>

    <section class="catalog-toolbar">
      <label class="catalog-search">
        <Search :size="22" />
        <input v-model="query" type="search" placeholder="BUSCAR POR TEMA, PROFESOR O PALABRA CLAVE" />
        <button v-if="query" aria-label="Limpiar búsqueda" @click="query = ''"><X :size="19" /></button>
      </label>
      <button class="filter-toggle" :class="{ 'filter-toggle--active': filtersOpen }" @click="filtersOpen = !filtersOpen">
        <SlidersHorizontal :size="20" /> FILTRAR
        <span v-if="activeFilterCount">{{ activeFilterCount }}</span>
      </button>
    </section>

    <Transition name="filter-panel">
      <section v-if="filtersOpen" class="filter-panel">
        <label><span>ÁREA DE INTERÉS</span><select v-model="selectedArea"><option value="">TODAS LAS ÁREAS</option><option v-for="area in areas" :key="area">{{ area }}</option></select></label>
        <label><span>PROFESOR GUÍA</span><select v-model="selectedProfessor"><option value="">TODOS LOS PROFESORES</option><option v-for="professor in professors" :key="professor">{{ professor }}</option></select></label>
        <button class="button button--paper" @click="clearFilters">LIMPIAR TODO</button>
      </section>
    </Transition>

    <section class="catalog-results">
      <div class="catalog-results__heading">
        <p><strong>{{ filteredTopics.length }}</strong> TEMAS DISPONIBLES</p>
        <span>ORDENADOS POR FECHA DE PUBLICACIÓN</span>
      </div>
      <div v-if="loading" class="loading-grid"><span></span><span></span><span></span></div>
      <div v-else-if="filteredTopics.length" class="topic-list">
        <TopicCard v-for="(topic, index) in filteredTopics" :key="topic.id" :topic="topic" :index="index" @open="router.push(`/temas/${topic.id}`)" />
      </div>
      <EmptyState v-else @clear="clearFilters" />
    </section>
  </AppShell>
</template>
