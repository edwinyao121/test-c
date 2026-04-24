import { createRouter, createWebHistory } from 'vue-router';
import Register from '../views/auth/Register.vue';

const routes = [
  {
    path: '/register',
    name: 'Register',
    component: Register,
  },
  {
    path: '/',
    redirect: '/register',
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
