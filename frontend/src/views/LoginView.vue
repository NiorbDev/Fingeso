<script setup>
import { ArrowRight, Eye, EyeOff, LockKeyhole, Mail, ShieldCheck } from '@lucide/vue'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PgtWordmark from '../components/PgtWordmark.vue'
import { session } from '../session'

const router = useRouter()
const email = ref('estudiante@usach.cl')
const password = ref('usach2026')
const showPassword = ref(false)

async function submit() {
  await session.login(email.value, password.value)
  await router.push('/inicio')
}
</script>

<template>
  <main class="login-page">
    <section class="login-page__brand">
      <div class="login-page__noise" aria-hidden="true"></div>
      <div class="login-page__brand-top">
        <PgtWordmark inverse />
        <p>UNIVERSIDAD DE SANTIAGO DE CHILE</p>
      </div>
      <div class="login-page__statement">
        <p class="eyebrow eyebrow--light">PLATAFORMA ACADÉMICA / 2026</p>
        <h1>TU TESIS.<br />TU PROCESO.<br /><em>UN SOLO LUGAR.</em></h1>
        <p class="login-page__lead">
          Explora temas, postula y sigue cada etapa de tu trabajo de titulación con claridad.
        </p>
      </div>
      <div class="login-page__ticker" aria-hidden="true">
        <span>POSTULA</span><i></i><span>AVANZA</span><i></i><span>CONSTRUYE</span>
      </div>
    </section>

    <section class="login-page__form-panel">
      <div class="login-page__form-top">
        <span>PGT / ACCESO</span>
        <span class="secure-label"><ShieldCheck :size="16" /> SITIO INSTITUCIONAL</span>
      </div>
      <form class="login-form" @submit.prevent="submit">
        <div>
          <p class="eyebrow">BIENVENIDA, BIENVENIDO</p>
          <h2>Ingresa a tu espacio de titulación.</h2>
          <p class="login-form__intro">Utiliza las credenciales de demostración para acceder como estudiante.</p>
        </div>

        <label class="field-label" for="email">CORREO INSTITUCIONAL</label>
        <div class="input-frame">
          <Mail :size="20" />
          <input id="email" v-model="email" type="email" autocomplete="username" required />
        </div>

        <label class="field-label" for="password">CONTRASEÑA</label>
        <div class="input-frame">
          <LockKeyhole :size="20" />
          <input
            id="password"
            v-model="password"
            :type="showPassword ? 'text' : 'password'"
            autocomplete="current-password"
            required
          />
          <button type="button" :aria-label="showPassword ? 'Ocultar clave' : 'Mostrar clave'" @click="showPassword = !showPassword">
            <EyeOff v-if="showPassword" :size="20" />
            <Eye v-else :size="20" />
          </button>
        </div>

        <button class="button button--primary button--login" :disabled="session.state.isLoading">
          <span>{{ session.state.isLoading ? 'VALIDANDO ACCESO…' : 'INGRESAR A LA PLATAFORMA' }}</span>
          <ArrowRight :size="22" />
        </button>

        <div class="demo-ticket">
          <span>CUENTA DEMO</span>
          <p><strong>estudiante@usach.cl</strong><br />Clave: usach2026</p>
        </div>
      </form>
      <p class="login-page__legal">Departamento de Ingeniería Informática · Universidad de Santiago de Chile</p>
    </section>
  </main>
</template>
