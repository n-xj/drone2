<template>
  <div>
    <AppNavbar />
    <div class="page-container">
      <div class="page-header-row">
        <div>
          <h1 class="page-title">设备管理</h1>
          <p class="page-subtitle">查看、搜索与管理所有无人机档案</p>
        </div>
        <router-link class="btn-primary-modern" to="/drones/new">+ 登记新设备</router-link>
      </div>

      <p v-if="message" class="alert-modern success">{{ message }}</p>
      <p v-if="error" class="alert-modern danger">{{ error }}</p>

      <div class="stat-grid">
        <div class="stat-card">
          <div class="label">设备总数</div>
          <div class="value">{{ drones.length }}</div>
        </div>
        <div class="stat-card">
          <div class="label">当前筛选</div>
          <div class="value filter-val">{{ searchName || '全部' }}</div>
        </div>
      </div>

      <div class="page-card">
        <form class="search-bar" @submit.prevent="load">
          <input
            v-model="searchName"
            class="form-control-modern"
            placeholder="按名称搜索设备…"
          />
          <button class="btn-ghost" type="submit" :disabled="loading">搜索</button>
          <button class="btn-ghost" type="button" @click="resetSearch">重置</button>
        </form>

        <div v-if="loading" class="loading-row">
          <span class="loading-spinner"></span> 加载中…
        </div>

        <table v-else class="data-table">
          <thead>
            <tr>
              <th>编号</th>
              <th>设备名称</th>
              <th>型号</th>
              <th>最近更新</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="d in drones" :key="d.id">
              <td><span class="id-badge">#{{ d.id }}</span></td>
              <td><strong>{{ d.name }}</strong></td>
              <td>{{ d.model || '—' }}</td>
              <td class="text-muted">{{ formatTime(d.updatedAt) }}</td>
              <td>
                <div class="actions">
                  <router-link class="btn-ghost btn-sm" :to="'/drones/' + d.id">详情</router-link>
                  <router-link class="btn-accent btn-sm" :to="'/drones/' + d.id + '/edit'">编辑</router-link>
                  <button class="btn-danger-soft btn-sm" type="button" @click="remove(d)">删除</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>

        <div v-if="!loading && drones.length === 0" class="empty-state">
          <div class="icon">📭</div>
          <p>暂无设备数据，点击右上角登记第一台无人机</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import AppNavbar from '../components/AppNavbar.vue'
import { deleteDrone, listDrones } from '../api/drone'

const drones = ref([])
const searchName = ref('')
const message = ref('')
const error = ref('')
const loading = ref(false)

function formatTime(t) {
  if (!t) return '—'
  return String(t).replace('T', ' ').slice(0, 19)
}

async function load() {
  loading.value = true
  error.value = ''
  try {
    const res = await listDrones(searchName.value || undefined)
    drones.value = res.data || []
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}

function resetSearch() {
  searchName.value = ''
  load()
}

async function remove(d) {
  if (!confirm('确定删除「' + d.name + '」？此操作不可撤销。')) return
  try {
    await deleteDrone(d.id)
    message.value = '已成功删除「' + d.name + '」'
    await load()
  } catch (e) {
    error.value = e.message
  }
}

onMounted(load)
</script>

<style scoped>
.page-header-row {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 0.5rem;
}

.page-header-row .page-subtitle {
  margin-bottom: 0;
}

.stat-card .filter-val {
  font-size: 1.1rem;
  color: var(--text);
  font-weight: 600;
}

.loading-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 2rem;
  justify-content: center;
  color: var(--text-muted);
}

.id-badge {
  background: #eef2ff;
  color: var(--primary);
  padding: 0.2rem 0.55rem;
  border-radius: 6px;
  font-size: 0.82rem;
  font-weight: 600;
}

.text-muted {
  color: var(--text-muted);
  font-size: 0.85rem;
}

.btn-sm {
  padding: 0.3rem 0.7rem !important;
  font-size: 0.8rem !important;
}
</style>
