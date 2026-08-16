<script setup>
import { Bell, ChevronDown, Mail, Menu } from '@lucide/vue'
import { session } from '../session'
import PgtWordmark from './PgtWordmark.vue'

defineProps({ breadcrumb: { type: String, default: 'INICIO' } })
defineEmits(['toggle-menu', 'open-notifications'])
</script>

<template>
  <header class="institutional-header">
    <div class="institutional-header__top">
      <button class="top-icon-button" aria-label="Abrir menú" @click="$emit('toggle-menu')">
        <Menu :size="23" />
      </button>
      <p class="institutional-header__crumb">
        UNIVERSIDAD DE SANTIAGO DE CHILE <b>/</b> <strong>{{ breadcrumb }}</strong>
      </p>
      <div class="institutional-header__actions">
        <button class="top-action" aria-label="Mensajes">
          <Mail :size="19" />
          <span>1</span>
        </button>
        <button class="top-action" aria-label="Notificaciones" @click="$emit('open-notifications')">
          <Bell :size="19" />
          <span>2</span>
        </button>
        <button class="user-chip">
          <span class="user-chip__avatar">{{ session.state.user?.initials }}</span>
          <span class="user-chip__name">{{ session.state.user?.name }}</span>
          <ChevronDown :size="15" />
        </button>
      </div>
    </div>

    <div class="institutional-header__banner">
      <div class="institutional-header__dots" aria-hidden="true"></div>
      <PgtWordmark inverse />
      <div class="institutional-header__title">
        <span>PLATAFORMA</span>
        <strong>GESTIÓN DE TESISTAS</strong>
      </div>
      <p class="institutional-header__semester">PRIMER SEMESTRE <b>2026</b></p>
    </div>
  </header>
</template>
