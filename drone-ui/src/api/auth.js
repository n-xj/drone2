import { apiFetch } from './http'

/** 登录：POST /api/auth/login，成功后后端写入会话 Cookie */
export function login(username, password) {
  return apiFetch('/api/auth/login', {
    method: 'POST',
    body: JSON.stringify({ username, password })
  })
}

/** 登出：POST /api/auth/logout，销毁 Shiro 会话 */
export function logout() {
  return apiFetch('/api/auth/logout', { method: 'POST' })
}

/** 获取当前登录用户：GET /api/auth/me（路由守卫也使用） */
export function fetchMe() {
  return apiFetch('/api/auth/me')
}

/*这个文件 = 前端登录相关的接口请求工具
专门负责：登录、登出、查当前用户
和后端 /api/auth/... 接口对接！*/