import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import TabsPage from '../views/TabsPage.vue';
import { StorageService } from "@/services/storage.service";

//const store = new StorageService();
//const userData = await store.read('user');
//let role = "";

//if (userData !== null) {
 // const userDataParsed = JSON.parse(userData.value!);
 // role = userDataParsed.roles[0];
//}

const routes: Array<RouteRecordRaw> = [
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
        component: () => import('@/views/HomePageDeputyLeader.vue'),
        //meta: { authorize: role },
      },
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
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/homedl'
      },
      {
        path: 'homedl',
        component: () => import('@/views/HomePageDeputyLeader.vue')
      },
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
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/homeel'
      },
      {
        path: 'homedl',
        component: () => import('@/views/HomePageEvacLeader.vue')
      },
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
    path: '/tabs/',
    component: TabsPage,
    children: [
      {
        path: '',
        redirect: '/tabs/homeuser'
      },
      {
        path: 'homeuser',
        component: () => import('@/views/HomePageUser.vue')
      },
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

/*
router.beforeEach((to, from, next) => {
  console.log('beforeEach');

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

    next();

})
*/

export default router
