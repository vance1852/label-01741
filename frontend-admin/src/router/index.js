import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', name: 'Dashboard', component: () => import('../views/Dashboard.vue'), meta: { title: '首页' } },
      { path: 'elder', name: 'Elder', component: () => import('../views/Elder.vue'), meta: { title: '老人管理' } },
      { path: 'worker', name: 'Worker', component: () => import('../views/Worker.vue'), meta: { title: '护工管理' } },
      { path: 'service', name: 'Service', component: () => import('../views/Service.vue'), meta: { title: '服务管理' } },
      { path: 'order', name: 'Order', component: () => import('../views/Order.vue'), meta: { title: '订单管理' } },
      { path: 'health', name: 'Health', component: () => import('../views/Health.vue'), meta: { title: '健康管理' } },
      { path: 'log', name: 'Log', component: () => import('../views/Log.vue'), meta: { title: '操作日志' } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
