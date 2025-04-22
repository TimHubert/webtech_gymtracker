import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Tracker from '../views/Tracker.vue'
import Stats from '../views/Stats.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/tracker', name: 'Tracker', component: Tracker },
  { path: '/stats', name: 'Stats', component: Stats },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
