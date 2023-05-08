import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '../views/deputy_leader/TabsPage.vue'

const routes: Array<RouteRecordRaw> = [
<<<<<<< Updated upstream
  {
    path: '/',
    redirect: '/login/',
  },
  {
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/home'
      },
      {
        path: 'home',
        component: () => import('@/views/deputy_leader/HomePage.vue')
      },
      {
        path: 'tab2',
        component: () => import('@/views/deputy_leader/Tab2Page.vue')
      },
      {
        path: 'tab3',
        component: () => import('@/views/deputy_leader/Tab3Page.vue')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/deputy_leader/LoginPage.vue')
  },
=======
    {
        path: '/',
        redirect: '/login/',
    },
    /*
    {
        path: '/tabs/homex§',
        redirect: () => {
            return `/tabs/home/${role}`
        }
    },

     */
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginPage.vue')
    },
    {
        path: '/tabs/',
        component: TabsPage,
        children: [
            {
                path: 'home/deputyleader',
                component: () => import('@/views/deputyleader/HomePageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'home/evacleader',
                component: () => import('@/views/evacleader/HomePageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'home/user',
                component: () => import('@/views/user/HomePageUser.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },


            // Deputy Leader
            {
                path: 'tab2/deputyleader',
                component: () => import('@/views/deputyleader/Tab2PageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab3/deputyleader',
                component: () => import('@/views/deputyleader/Tab3PageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab4/deputyleader',
                component: () => import('@/views/deputyleader/Tab4PageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_DEPUTYLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },

            //Evacuation Leader
            {
                path: 'tab2/evacleader',
                component: () => import('@/views/evacleader/Tab2PageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab3/evacleader',
                component: () => import('@/views/evacleader/Tab3PageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab4/evacleader',
                component: () => import('@/views/evacleader/Tab4PageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_EVACLEADER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },


            // User
            {
                path: 'tab2/user',
                component: () => import('@/views/user/Tab2PageUser.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab3/user',
                component: () => import('@/views/user/Tab3PageUser.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
            {
                path: 'tab4/user',
                component: () => import('@/views/user/Tab4PageUser.vue'),
                meta: {role: 'ROLE_USER'},
                beforeEnter: async (to, from, next) => {
                    await getRole();
                    // Check if the user is authenticated and has the required role
                    if (role === 'ROLE_USER') {
                        next();
                    } else {
                        // Redirect to login page if the user is not authenticated or does not have the required role
                        next('/login');
                    }
                }
            },
        ]
    },
>>>>>>> Stashed changes
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
