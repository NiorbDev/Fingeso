import { getTopicById, topics } from './topics'

const API_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api'
const USE_API = import.meta.env.VITE_USE_API === 'true'
const APPLICATIONS_KEY = 'pgt-demo-applications'

async function request(path, options = {}) {
  const response = await fetch(`${API_URL}${path}`, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  })
  return response.json()
}

function readLocalApplications() {
  return JSON.parse(sessionStorage.getItem(APPLICATIONS_KEY) ?? '[]')
}

function writeLocalApplications(applications) {
  sessionStorage.setItem(APPLICATIONS_KEY, JSON.stringify(applications))
}

export const api = {
  async login(email, password) {
    if (USE_API) return request('/auth/login', { method: 'POST', body: JSON.stringify({ email, password }) })
    await new Promise((resolve) => setTimeout(resolve, 650))
    if (email.trim().toLowerCase() !== 'estudiante@usach.cl' || password !== 'usach2026') {
      throw new Error('El correo o la clave no coinciden con la cuenta de demostración.')
    }
    return {
      id: 1,
      name: 'Martina Rojas Soto',
      firstName: 'Martina',
      email: 'estudiante@usach.cl',
      role: 'Estudiante',
      initials: 'MR',
      career: 'Ingeniería Civil Informática',
      progress: 72,
    }
  },
  async listTopics() {
    return USE_API ? request('/topics') : topics
  },
  async getTopic(id) {
    if (USE_API) return request(`/topics/${id}`)
    return getTopicById(id)
  },
  async createApplication(payload) {
    if (USE_API) return request('/applications', { method: 'POST', body: JSON.stringify(payload) })

    const topic = getTopicById(payload.topicId)
    const existing = readLocalApplications()
    if (existing.some((application) => application.topic.id === topic.id && application.state === 'PENDIENTE')) {
      throw new Error('Ya tienes una postulación pendiente para este tema.')
    }
    const application = {
      id: Math.floor(260000 + Math.random() * 10000),
      state: 'PENDIENTE',
      createdAt: new Date().toISOString(),
      modality: payload.modality,
      message: payload.message,
      members: payload.members,
      topic,
    }
    writeLocalApplications([application, ...existing])
    return application
  },
  async listApplications(studentId) {
    return USE_API ? request(`/applications/student/${studentId}`) : readLocalApplications()
  },
}
