import { reactive } from 'vue'

// Estado sencillo para mostrar avisos globales, como “Próximamente”.

const state = reactive({
  visible: false,
  message: '',
  eyebrow: 'PGT INFORMA',
  tone: 'info',
  timer: null,
})

export const toast = {
  state,
  show(message, options = {}) {
    window.clearTimeout(state.timer)
    state.message = message
    state.eyebrow = options.eyebrow ?? 'PGT INFORMA'
    state.tone = options.tone ?? 'info'
    state.visible = true
    state.timer = window.setTimeout(() => {
      state.visible = false
    }, options.duration ?? 3200)
  },
  hide() {
    window.clearTimeout(state.timer)
    state.visible = false
  },
}
