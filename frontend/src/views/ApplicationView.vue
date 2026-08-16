<script setup>
import { ArrowLeft, ArrowRight, Check, CircleAlert, Plus, Send, Trash2, UsersRound, X } from '@lucide/vue'
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppShell from '../components/AppShell.vue'
import { api } from '../api'
import { session } from '../session'

const route = useRoute()
const router = useRouter()
const topic = ref(null)
const modality = ref('INDIVIDUAL')
const message = ref('')
const accepted = ref(false)
const confirmationOpen = ref(false)
const submitting = ref(false)
const submitError = ref('')
const attempted = ref(false)
const members = reactive([{ name: '', email: '' }])

onMounted(async () => {
  topic.value = await api.getTopic(route.params.id)
  if (!topic.value) router.replace('/catalogo')
})

const validMembers = computed(() => members.filter((member) => member.name.trim() && member.email.trim()))
const totalApplicants = computed(() => 1 + (modality.value === 'GROUP' ? validMembers.value.length : 0))
const availableSlots = computed(() => topic.value ? topic.value.totalSlots - topic.value.reservedSlots : 0)
const messageCount = computed(() => message.value.length)
const formIsValid = computed(() => {
  const groupValid = modality.value === 'INDIVIDUAL' || validMembers.value.length >= 1
  return groupValid && message.value.trim().length >= 20 && accepted.value && totalApplicants.value <= availableSlots.value
})

function addMember() {
  if (members.length < 3) members.push({ name: '', email: '' })
}

function removeMember(index) {
  members.splice(index, 1)
}

function requestConfirmation() {
  attempted.value = true
  if (formIsValid.value) confirmationOpen.value = true
}

async function confirmApplication() {
  submitting.value = true
  submitError.value = ''
  try {
    const application = await api.createApplication({
      studentId: session.state.user.id,
      topicId: topic.value.id,
      modality: modality.value,
      message: message.value.trim(),
      members: [
        { name: session.state.user.name, email: session.state.user.email },
        ...(modality.value === 'GROUP' ? validMembers.value : []),
      ],
    })
    confirmationOpen.value = false
    await router.push({ path: '/mis-postulaciones', query: { enviada: application.id } })
  } catch (error) {
    submitError.value = error.message
  } finally {
    submitting.value = false
  }
}
</script>

<template>
  <AppShell breadcrumb="CATÁLOGO / POSTULAR">
    <template v-if="topic">
      <button class="back-link" @click="router.push(`/temas/${topic.id}`)"><ArrowLeft :size="19" /> VOLVER AL DETALLE</button>

      <section class="application-heading">
        <div><p class="eyebrow">CU04 · POSTULAR A TEMA DE TESIS</p><h1>Prepara tu postulación.</h1><p>Revisa los datos antes de enviar. La solicitud no podrá editarse mientras esté pendiente.</p></div>
        <div class="application-heading__topic"><span>TEMA SELECCIONADO</span><strong>{{ topic.code }}</strong><p>{{ topic.title }}</p></div>
      </section>

      <div class="application-layout">
        <form class="application-form" @submit.prevent="requestConfirmation">
          <section class="form-step">
            <div class="form-step__index"><span>01</span><i></i></div>
            <div class="form-step__content">
              <p class="eyebrow">COMPOSICIÓN</p><h2>¿Cómo desarrollarás la tesis?</h2>
              <div class="modality-grid">
                <label :class="{ 'modality-card--selected': modality === 'INDIVIDUAL' }">
                  <input v-model="modality" type="radio" value="INDIVIDUAL" />
                  <span class="modality-card__check"><Check :size="17" /></span>
                  <strong>INDIVIDUAL</strong><p>Postulas únicamente con tu perfil académico.</p><small>01 INTEGRANTE</small>
                </label>
                <label :class="{ 'modality-card--selected': modality === 'GROUP' }">
                  <input v-model="modality" type="radio" value="GROUP" />
                  <span class="modality-card__check"><Check :size="17" /></span>
                  <strong>GRUPAL</strong><p>El equipo debe estar previamente conformado.</p><small>02 INTEGRANTES</small>
                </label>
              </div>

              <Transition name="filter-panel">
                <div v-if="modality === 'GROUP'" class="member-panel">
                  <div class="member-panel__heading"><div><p class="eyebrow">INTEGRANTES DEL EQUIPO</p><span>Incluye a quienes participarán contigo.</span></div><button v-if="members.length < 3" type="button" @click="addMember"><Plus :size="18" /> AGREGAR</button></div>
                  <div v-for="(member, index) in members" :key="index" class="member-row">
                    <span>{{ String(index + 2).padStart(2, '0') }}</span>
                    <label><small>NOMBRE COMPLETO</small><input v-model="member.name" type="text" placeholder="Nombre del integrante" /></label>
                    <label><small>CORREO INSTITUCIONAL</small><input v-model="member.email" type="email" placeholder="nombre@usach.cl" /></label>
                    <button type="button" aria-label="Eliminar integrante" @click="removeMember(index)"><Trash2 :size="19" /></button>
                  </div>
                  <p v-if="attempted && validMembers.length < 1" class="inline-error"><CircleAlert :size="16" /> Agrega al menos un integrante con nombre y correo.</p>
                </div>
              </Transition>
            </div>
          </section>

          <section class="form-step">
            <div class="form-step__index"><span>02</span><i></i></div>
            <div class="form-step__content">
              <p class="eyebrow">MENSAJE AL PROFESOR</p><h2>Cuéntale por qué te interesa.</h2>
              <p class="form-help">Escribe una presentación breve. Puedes mencionar motivación, experiencia relacionada o lo que esperas aportar.</p>
              <label class="message-field">
                <textarea v-model="message" maxlength="600" placeholder="Ej.: Me interesa este tema porque…"></textarea>
                <span :class="{ 'message-field__count--warning': messageCount > 550 }">{{ messageCount }}/600</span>
              </label>
              <p v-if="attempted && message.trim().length < 20" class="inline-error"><CircleAlert :size="16" /> El mensaje debe tener al menos 20 caracteres.</p>
            </div>
          </section>

          <section class="form-step form-step--last">
            <div class="form-step__index"><span>03</span></div>
            <div class="form-step__content">
              <p class="eyebrow">CONFIRMACIÓN</p><h2>Declara y envía.</h2>
              <label class="consent-box" :class="{ 'consent-box--checked': accepted }">
                <input v-model="accepted" type="checkbox" />
                <span><Check :size="17" /></span>
                <p>Confirmo que la información ingresada es correcta y que, en caso de postulación grupal, el equipo ya está conformado.</p>
              </label>
              <p v-if="attempted && !accepted" class="inline-error"><CircleAlert :size="16" /> Debes confirmar la declaración antes de continuar.</p>
              <p v-if="totalApplicants > availableSlots" class="inline-error"><CircleAlert :size="16" /> El tema no tiene cupos suficientes para {{ totalApplicants }} integrantes.</p>
              <button class="button button--primary application-submit" type="submit">REVISAR Y ENVIAR <ArrowRight :size="21" /></button>
            </div>
          </section>
        </form>

        <aside class="application-summary">
          <p class="eyebrow eyebrow--light">RESUMEN DE SOLICITUD</p>
          <h2>{{ topic.title }}</h2>
          <dl>
            <div><dt>PROFESOR GUÍA</dt><dd>{{ topic.professor }}</dd></div>
            <div><dt>MODALIDAD</dt><dd>{{ modality === 'GROUP' ? 'GRUPAL' : 'INDIVIDUAL' }}</dd></div>
            <div><dt>INTEGRANTES</dt><dd>{{ totalApplicants }}</dd></div>
            <div><dt>ESTADO INICIAL</dt><dd><span>PENDIENTE</span></dd></div>
          </dl>
          <div class="application-summary__note"><UsersRound :size="21" /><p>Al enviar, el cupo quedará reservado mientras el profesor revisa tu solicitud.</p></div>
        </aside>
      </div>
    </template>

    <Transition name="modal">
      <div v-if="confirmationOpen" class="modal-backdrop" role="dialog" aria-modal="true" aria-labelledby="confirm-title">
        <section class="confirm-modal">
          <button class="confirm-modal__close" aria-label="Cerrar" @click="confirmationOpen = false"><X :size="22" /></button>
          <span class="confirm-modal__icon"><Send :size="31" /></span>
          <p class="eyebrow">ÚLTIMO PASO</p>
          <h2 id="confirm-title">¿Enviar esta postulación?</h2>
          <p>Se registrará con fecha y hora, el cupo quedará reservado y {{ topic.professor }} recibirá una notificación.</p>
          <div class="confirm-modal__topic"><span>{{ topic.code }}</span><strong>{{ topic.title }}</strong></div>
          <p v-if="submitError" class="form-error" role="alert">{{ submitError }}</p>
          <div class="confirm-modal__actions">
            <button class="button button--paper" :disabled="submitting" @click="confirmationOpen = false">VOLVER A REVISAR</button>
            <button class="button button--primary" :disabled="submitting" @click="confirmApplication">{{ submitting ? 'ENVIANDO…' : 'SÍ, ENVIAR' }} <Send :size="18" /></button>
          </div>
        </section>
      </div>
    </Transition>
  </AppShell>
</template>
