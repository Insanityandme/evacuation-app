import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '../views/TabsPage.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/UsersManager'
      },
      {
        path: 'UsersManager',
        component: () => import('@/views/UsersManagerPage.vue'),
      },
      {
        path: 'UsersManager/edit/:id',
        component: () => import('@/views/EditUserPage.vue')
      },
      {
        path: 'UsersManager/add',
        component: () => import('@/views/AddUserPage.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginPage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
