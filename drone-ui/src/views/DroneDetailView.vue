<template>
  <div>
    <AppNavbar />
    <div class="page-container">
      <p v-if="error" class="alert-modern danger">{{ error }}</p>

      <div v-if="loading" class="loading-row">
        <span class="loading-spinner"></span> 加载详情…
      </div>

      <template v-else-if="drone">
        <div class="page-header-row">
          <div>
            <h1 class="page-title">{{ drone.name }}</h1>
            <p class="page-subtitle">设备编号 #{{ drone.id }} · 最后更新 {{ formatTime(drone.updatedAt) }}</p>
          </div>
          <div class="header-actions">
            <router-link class="btn-ghost" to="/">← 返回列表</router-link>
            <router-link class="btn-primary-modern" :to="'/drones/' + drone.id + '/edit'">编辑设备</router-link>
          </div>
        </div>

        <div class="info-grid">
          <div class="info-item">
            <div class="label">设备名称</div>
            <div class="value">{{ drone.name }}</div>
          </div>
          <div class="info-item">
            <div class="label">型号</div>
            <div class="value">{{ drone.model || '—' }}</div>
          </div>
          <div class="info-item">
            <div class="label">序列号</div>
            <div class="value">{{ drone.serialNo || '—' }}</div>
          </div>
          <div class="info-item">
            <div class="label">备注</div>
            <div class="value">{{ drone.remark || '—' }}</div>
          </div>
        </div>

        <div class="page-card">
          <div class="json-header">
            <h3>AI 智能属性</h3>
            <span class="ai-badge">AI Generated</span>
          </div>
          <pre class="json-block">{{ prettyJson(drone.attributesJson) }}</pre>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import AppNavbar from '../components/AppNavbar.vue'
import { getDrone } from '../api/drone'

const props = defineProps({ id: { type: [String, Number], required: true } })
const drone = ref(null)
const error = ref('')
const loading = ref(true)

function formatTime(t) {
  if (!t) return '—'
  return String(t).replace('T', ' ').slice(0, 19)
}

function prettyJson(raw) {
  if (!raw) return '{}'
  try {
    return JSON.stringify(JSON.parse(raw), null, 2)
  } catch {
    return raw
  }
}

onMounted(async () => {
  try {
    drone.value = (await getDrone(props.id)).data
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
})
</script>

<style scoped>
.page-header-row {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.page-header-row .page-subtitle {
  margin-bottom: 0;
}

.header-actions {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.json-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.json-header h3 {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
}

.ai-badge {
  background: linear-gradient(135deg, #4f46e5, #06b6d4);
  color: #fff;
  font-size: 0.7rem;
  font-weight: 700;
  padding: 0.25rem 0.65rem;
  border-radius: 20px;
  letter-spacing: 0.04em;
  text-transform: uppercase;
}

.loading-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 3rem;
  justify-content: center;
  color: var(--text-muted);
}
</style>
