<script setup>
import { X, Zap } from '@lucide/vue'
import { toast } from '../toast'
</script>

<template>
  <Transition name="toast">
    <aside
      v-if="toast.state.visible"
      class="app-toast"
      :class="`app-toast--${toast.state.tone}`"
      role="status"
      aria-live="polite"
    >
      <div class="app-toast__icon"><Zap :size="20" stroke-width="2.5" /></div>
      <div>
        <p class="app-toast__eyebrow">{{ toast.state.eyebrow }}</p>
        <p class="app-toast__message">{{ toast.state.message }}</p>
      </div>
      <button class="app-toast__close" aria-label="Cerrar notificación" @click="toast.hide">
        <X :size="19" />
      </button>
    </aside>
  </Transition>
</template>
