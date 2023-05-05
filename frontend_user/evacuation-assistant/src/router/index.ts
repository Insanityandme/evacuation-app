import {createRouter, createWebHistory} from '@ionic/vue-router';
import {RouteRecordRaw} from 'vue-router';
import TabsPage from '../views/TabsPage.vue';
import {StorageService} from "@/services/storage.service";

const store = new StorageService();
let role = "";

async function getRole() {
    // Call the read method to retrieve the user data
    const userData = await store.read('user');

    if (userData !== null) {
        const userDataParsed = JSON.parse(userData.value!);
        // console.log(userData);
        role = userDataParsed.roles[0];
        console.log("setting role data...");
    }
    return role
}

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/login/',
    },
    {
        path: '',
        redirect: () => {
            return `/tabs/home/${role}`
        }
    },
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


            //User  (only Settings-tab)
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
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


/*
router.beforeEach(async (to, from, next) => {
    const userData = await store.read('user');
    if (userData !== null) {
        const userDataParsed = JSON.parse(userData.value!);
        const userRole = userDataParsed.roles[0];
        const routeRole = to.meta.role;
        if (routeRole && routeRole !== userRole) {
            return next('/');
        }
    }
    next();
});
*/


export default router
