<template>
  <div class="login-page">
    <div class="login-left">
      <div class="login-brand">
        <span class="logo">✈</span>
        <h1>DroneHub</h1>
        <p>无人机信息智能管理平台</p>
        <ul class="features">
          <li>设备全生命周期管理</li>
          <li>AI 自动生成设备属性</li>
          <li>SQLite / MySQL 双库支持</li>
        </ul>
      </div>
    </div>
    <div class="login-right">
      <div class="login-card">
        <h2>欢迎回来</h2>
        <p class="hint">登录以继续管理您的无人机设备</p>
        <p v-if="error" class="alert-modern danger">{{ error }}</p>
        <form @submit.prevent="onSubmit">
          <div class="field">
            <label>用户名</label>
            <input v-model="username" class="form-control-modern" placeholder="请输入用户名" required />
          </div>
          <div class="field">
            <label>密码</label>
            <input v-model="password" type="password" class="form-control-modern" placeholder="请输入密码" required />
          </div>
          <button class="btn-primary-modern btn-block" type="submit" :disabled="loading">
            <span v-if="loading" class="loading-spinner"></span>
            {{ loading ? '登录中…' : '进入系统' }}
          </button>
        </form>
        <p class="demo-hint">演示账号 <code>admin</code> / <code>secret</code></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { login } from '../api/auth'

const username = ref('admin')
const password = ref('')
const error = ref('')
const loading = ref(false)
const router = useRouter()
const route = useRoute()

async function onSubmit() {
  error.value = ''
  loading.value = true
  try {
    await login(username.value, password.value)
    router.replace(route.query.redirect || '/')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
}

.login-left {
  flex: 1;
  background: linear-gradient(145deg, #1e1b4b 0%, #4338ca 60%, #06b6d4 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  color: #fff;
}

.login-brand {
  max-width: 380px;
}

.logo {
  font-size: 3rem;
  display: block;
  margin-bottom: 1rem;
}

.login-brand h1 {
  font-size: 2.25rem;
  font-weight: 800;
  margin: 0 0 0.5rem;
  letter-spacing: -0.03em;
}

.login-brand > p {
  opacity: 0.85;
  margin: 0 0 2rem;
  font-size: 1.05rem;
}

.features {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  padding: 0.5rem 0;
  padding-left: 1.5rem;
  position: relative;
  opacity: 0.9;
  font-size: 0.95rem;
}

.features li::before {
  content: '✓';
  position: absolute;
  left: 0;
  color: #67e8f9;
  font-weight: bold;
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background: var(--bg);
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: var(--surface);
  border-radius: var(--radius);
  box-shadow: var(--shadow-lg);
  padding: 2.25rem;
  border: 1px solid var(--border);
}

.login-card h2 {
  margin: 0 0 0.35rem;
  font-size: 1.5rem;
  font-weight: 700;
}

.hint {
  color: var(--text-muted);
  font-size: 0.9rem;
  margin: 0 0 1.5rem;
}

.field {
  margin-bottom: 1.1rem;
}

.field label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
  color: var(--text);
}

.btn-block {
  width: 100%;
  padding: 0.65rem;
  margin-top: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.demo-hint {
  margin: 1.5rem 0 0;
  text-align: center;
  font-size: 0.82rem;
  color: var(--text-muted);
}

.demo-hint code {
  background: #f1f5f9;
  padding: 0.15rem 0.4rem;
  border-radius: 4px;
  font-size: 0.8rem;
}

@media (max-width: 768px) {
  .login-page {
    flex-direction: column;
  }
  .login-left {
    padding: 2rem 1.5rem;
  }
  .login-brand h1 {
    font-size: 1.75rem;
  }
  .features {
    display: none;
  }
}
</style>
