import { computed, reactive } from 'vue'
import { api } from './api'

const storedUser = sessionStorage.getItem('pgt-user')

const state = reactive({
  user: storedUser ? JSON.parse(storedUser) : null,
  isLoading: false,
})

export const session = {
  state,
  isAuthenticated: computed(() => Boolean(state.user)),
  async login(email, password) {
    state.isLoading = true
    state.user = await api.login(email, password)
    sessionStorage.setItem('pgt-user', JSON.stringify(state.user))
    state.isLoading = false
  },
  logout() {
    state.user = null
    sessionStorage.removeItem('pgt-user')
  },
}
