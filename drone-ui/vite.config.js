import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

// Vite 构建配置：开发服务器 + 反向代理到 Spring Boot 后端
export default defineConfig(({ mode }) => {
  // 从 .env.development 读取 VITE_API_TARGET，默认 8081
  const env = loadEnv(mode, process.cwd(), '')
  const apiTarget = env.VITE_API_TARGET || 'http://localhost:8080'

  return {
    plugins: [vue()],
    server: {
      port: 5173, // 前端开发地址 http://localhost:5173
      proxy: {
        // 浏览器请求 /api/* 时，转发到后端，避免跨域问题
        '/api': {
          target: apiTarget,
          changeOrigin: true,
          secure: false,
          configure: (proxy) => {
            // 登录成功后后端会 Set-Cookie；去掉 Domain 限制，让 Cookie 在 5173 端口生效
            proxy.on('proxyRes', (proxyRes) => {
              const cookies = proxyRes.headers['set-cookie']
              if (cookies) {
                proxyRes.headers['set-cookie'] = cookies.map((cookie) =>
                  cookie.replace(/;\s*Domain=[^;]+/i, '')
                )
              }
            })
          }
        }
      }
    }
  }
})
