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
        path: '/tabs/',
        component: TabsPage,
        children: [
            /*
          {
            path: 'home',
            component: () => import('@/views/HomePageDeputyLeader.vue'),
            //meta: { authorize: role },
          }, */

            {
                path: 'home/deputyleader',
                component: () => import('@/views/HomePageDeputyLeader.vue'),
                meta: {role: 'ROLE_DEPUTYLEADER'},
            },
            {
                path: 'home/evacleader',
                component: () => import('@/views/HomePageEvacLeader.vue'),
                meta: {role: 'ROLE_EVACLEADER'},
            },
            {
                path: 'home/user',
                component: () => import('@/views/HomePageUser.vue'),
                meta: {role: 'ROLE_USER'},
            },


            /*
                        {
                            path: 'home',    // path: 'home/:role',
                            component: () => {
                                getRole().then((result) =>
                                {
                                    console.log(result);
                                    role = result;
                                });
                                console.log("hejsan");

                                switch (role) {
                                    case 'ROLE_DEPUTYLEADER':
                                        return import('@/views/HomePageDeputyLeader.vue')
                                    case 'ROLE_EVACLEADER':
                                        return import('@/views/HomePageEvacLeader.vue')
                                    case 'ROLE_USER':
                                        return import('@/views/HomePageUser.vue')
                                    default:
                                        return import('@/views/HomePage.vue')
                                }
                            }
                        },
                        */
/*
            {

                path: '',
                redirect: () => {
                    return `/tabs/home/${role}`
                }
            },

 */

            {
                path: 'tab2',
                component: () => import('@/views/Tab2Page.vue')
            },
            {
                path: 'tab3',
                component: () => import('@/views/Tab3Page.vue')
            }
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/LoginPage.vue')
    },
]


const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})


router.beforeEach((to, from, next) => {
    console.log('beforeEach');


    /*
     // redirect to login page if not logged in and trying to access a restricted page
      const { authorize } = to.meta;
      const currentUser = authenticationService.currentUserValue;

      if (authorize) {
          if (!currentUser) {
              // not logged in so redirect to login page with the return url
              return next({ path: '/login', query: { returnUrl: to.path } });
          }

          // check if route is restricted by role
          if (authorize.length && !authorize.includes(currentUser.role)) {
              // role not authorised so redirect to home page
              return next({ path: '/' });
          }
      }

     */

    next();

})


export default router
