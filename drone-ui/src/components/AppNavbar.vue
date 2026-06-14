<template>
  <header class="top-nav">
    <div class="nav-inner">
      <router-link class="brand" to="/">
        <span class="brand-icon">✈</span>
        <span class="brand-text">Drone<span class="accent">Hub</span></span>
      </router-link>
      <nav class="nav-links">
        <router-link class="nav-link" to="/" active-class="active">设备列表</router-link>
        <router-link class="nav-link" to="/drones/new" active-class="active">登记设备</router-link>
      </nav>
      <button class="btn-logout" type="button" @click="onLogout">退出登录</button>
    </div>
  </header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { logout } from '../api/auth'

const router = useRouter()

async function onLogout() {
  await logout()
  router.push('/login')
}
</script>

<style scoped>
.top-nav {
  background: linear-gradient(135deg, #1e1b4b 0%, #312e81 50%, #1e1b4b 100%);
  box-shadow: 0 4px 20px rgba(30, 27, 75, 0.4);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-inner {
  max-width: 1100px;
  margin: 0 auto;
  padding: 0 1.25rem;
  height: 60px;
  display: flex;
  align-items: center;
  gap: 2rem;
}

.brand {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  text-decoration: none;
  color: #fff;
  font-weight: 700;
  font-size: 1.15rem;
}

.brand-icon {
  font-size: 1.35rem;
}

.brand-text .accent {
  color: #818cf8;
}

.nav-links {
  display: flex;
  gap: 0.25rem;
  flex: 1;
}

.nav-link {
  color: rgba(255, 255, 255, 0.75);
  text-decoration: none;
  padding: 0.45rem 1rem;
  border-radius: 8px;
  font-size: 0.9rem;
  font-weight: 500;
  transition: background 0.15s, color 0.15s;
}

.nav-link:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.1);
}

.nav-link.active {
  color: #fff;
  background: rgba(129, 140, 248, 0.35);
}

.btn-logout {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: rgba(255, 255, 255, 0.9);
  padding: 0.4rem 1rem;
  border-radius: 8px;
  font-size: 0.85rem;
  cursor: pointer;
  transition: background 0.15s;
}

.btn-logout:hover {
  background: rgba(255, 255, 255, 0.18);
}
</style>
