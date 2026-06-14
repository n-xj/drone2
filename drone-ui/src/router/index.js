import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import DroneListView from '../views/DroneListView.vue'
import DroneDetailView from '../views/DroneDetailView.vue'
import DroneFormView from '../views/DroneFormView.vue'
import { fetchMe } from '../api/auth'

/*这个文件 = 前端页面导航控制器
决定：网址输什么 → 显示哪个页面
并且：没登录的人自动跳转到登录页！*/

// 路由表：URL 路径 → 页面组件
const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 History 模式（无 # 号）
  routes: [
    { path: '/login', component: LoginView, meta: { public: true } },  // 公开页，无需登录
    { path: '/', component: DroneListView },
    { path: '/drones/new', component: DroneFormView }, // 新建（与编辑共用同一组件）
    { path: '/drones/:id', component: DroneDetailView, props: true }, // :id 作为 props 传入
    { path: '/drones/:id/edit', component: DroneFormView, props: true } //编辑无人机
  ]
})

// 全局路由守卫：除 /login 外，访问前先向后端确认会话是否有效
router.beforeEach(async (to) => {
  if (to.meta.public) return true
  try {
    await fetchMe() // GET /api/auth/me
    return true
  } catch {
    // 未登录则跳转登录页，并记录原目标路径以便登录后跳回
    return { path: '/login', query: { redirect: to.fullPath } } //没登陆，强制登陆
  }
})

export default router


