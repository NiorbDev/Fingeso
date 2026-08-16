<script setup>
import { ArrowRight, MapPin, UsersRound } from '@lucide/vue'
import { computed } from 'vue'
import StatusBadge from './StatusBadge.vue'

const props = defineProps({ topic: { type: Object, required: true }, index: { type: Number, default: 0 } })
defineEmits(['open'])

const availableSlots = computed(() => props.topic.totalSlots - props.topic.reservedSlots)
</script>

<template>
  <article class="topic-card">
    <div class="topic-card__rail">
      <span>{{ String(index + 1).padStart(2, '0') }}</span>
      <small>{{ topic.code }}</small>
    </div>
    <div class="topic-card__content">
      <div class="topic-card__meta">
        <StatusBadge :label="topic.area" tone="teal" />
        <span>PUBLICADO {{ topic.publishedAt }}</span>
      </div>
      <h2>{{ topic.title }}</h2>
      <p>{{ topic.summary }}</p>
      <div class="topic-card__professor">
        <span class="topic-card__avatar">{{ topic.professor.split(' ').slice(-1)[0].slice(0, 1) }}</span>
        <span><small>PROFESOR GUÍA</small><strong>{{ topic.professor }}</strong></span>
      </div>
      <div class="topic-card__facts">
        <span><UsersRound :size="17" /> {{ availableSlots }} DE {{ topic.totalSlots }} CUPOS</span>
        <span><MapPin :size="17" /> {{ topic.modality }}</span>
      </div>
    </div>
    <button class="topic-card__open" :aria-label="`Ver tema ${topic.title}`" @click="$emit('open', topic)">
      <span>VER TEMA</span>
      <ArrowRight :size="24" />
    </button>
  </article>
</template>
