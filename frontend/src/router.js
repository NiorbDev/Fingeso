import { createRouter, createWebHistory } from 'vue-router'
import { session } from './session'
import LoginView from './views/LoginView.vue'
import DashboardView from './views/DashboardView.vue'
import CatalogView from './views/CatalogView.vue'
import TopicDetailView from './views/TopicDetailView.vue'
import ApplicationView from './views/ApplicationView.vue'
import ApplicationsView from './views/ApplicationsView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: () => (session.isAuthenticated.value ? '/inicio' : '/ingresar'),
    },
    {
      path: '/ingresar',
      name: 'login',
      component: LoginView,
      meta: { guestOnly: true },
    },
    {
      path: '/inicio',
      name: 'dashboard',
      component: DashboardView,
      meta: { requiresAuth: true },
    },
    {
      path: '/catalogo',
      name: 'catalog',
      component: CatalogView,
      meta: { requiresAuth: true },
    },
    {
      path: '/temas/:id',
      name: 'topic-detail',
      component: TopicDetailView,
      meta: { requiresAuth: true },
    },
    {
      path: '/postular/:id',
      name: 'application',
      component: ApplicationView,
      meta: { requiresAuth: true },
    },
    {
      path: '/mis-postulaciones',
      name: 'applications',
      component: ApplicationsView,
      meta: { requiresAuth: true },
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/inicio',
    },
  ],
  scrollBehavior: () => ({ top: 0 }),
})

router.beforeEach((to) => {
  if (to.meta.requiresAuth && !session.isAuthenticated.value) return '/ingresar'
  if (to.meta.guestOnly && session.isAuthenticated.value) return '/inicio'
  return true
})

export default router
