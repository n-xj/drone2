<template>
  <div>
    <AppNavbar />
    <div class="page-container">
      <div class="page-header-row">
        <div>
          <h1 class="page-title">{{ isEdit ? '编辑设备' : '登记新设备' }}</h1>
          <p class="page-subtitle">
            {{ isEdit ? '修改设备信息，可选择重新生成 AI 属性' : '填写基本信息，保存后 AI 将自动生成设备属性' }}
          </p>
        </div>
        <router-link class="btn-ghost" to="/">← 返回列表</router-link>
      </div>

      <div class="page-card form-card">
        <p v-if="error" class="alert-modern danger">{{ error }}</p>
        <form @submit.prevent="onSubmit">
          <div v-if="isEdit" class="ai-toggle">
            <input id="regen" v-model="form.regenerateAttributes" type="checkbox" />
            <label for="regen">
              <strong>重新生成 AI 属性</strong>
              <span>勾选后保存时将调用 AI 服务覆盖现有属性数据</span>
            </label>
          </div>

          <div class="form-grid">
            <div class="field">
              <label>设备名称 <span class="required">*</span></label>
              <input v-model="form.name" class="form-control-modern" placeholder="如：巡检一号" required />
            </div>
            <div class="field">
              <label>型号</label>
              <input v-model="form.model" class="form-control-modern" placeholder="如：DJI Mavic 3" />
            </div>
            <div class="field">
              <label>序列号</label>
              <input v-model="form.serialNo" class="form-control-modern" placeholder="设备唯一序列号" />
            </div>
            <div class="field field-full">
              <label>备注</label>
              <textarea v-model="form.remark" class="form-control-modern" rows="3" placeholder="可选备注信息…" />
            </div>
          </div>

          <div class="form-actions">
            <button class="btn-primary-modern" type="submit" :disabled="submitting">
              <span v-if="submitting" class="loading-spinner"></span>
              {{ submitting ? '提交中…' : (isEdit ? '保存修改' : '创建设备') }}
            </button>
            <router-link class="btn-ghost" to="/">取消</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppNavbar from '../components/AppNavbar.vue'
import { createDrone, getDrone, updateDrone } from '../api/drone'

const props = defineProps({ id: { type: [String, Number], default: null } })
const route = useRoute()
const router = useRouter()
const isEdit = computed(() => route.path.endsWith('/edit'))
const error = ref('')
const submitting = ref(false)

const form = reactive({
  name: '',
  model: '',
  serialNo: '',
  remark: '',
  regenerateAttributes: false
})

onMounted(async () => {
  if (!isEdit.value) return
  try {
    const d = (await getDrone(props.id)).data
    form.name = d.name || ''
    form.model = d.model || ''
    form.serialNo = d.serialNo || ''
    form.remark = d.remark || ''
  } catch (e) {
    error.value = e.message
  }
})

async function onSubmit() {
  error.value = ''
  submitting.value = true
  try {
    const payload = { ...form }
    const res = isEdit.value
      ? await updateDrone(props.id, payload)
      : await createDrone(payload)
    router.push('/drones/' + res.data.id)
  } catch (e) {
    error.value = e.message
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.page-header-row {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 1.25rem;
}

.page-header-row .page-subtitle {
  margin-bottom: 0;
}

.form-card {
  max-width: 720px;
}

.ai-toggle {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  background: #eef2ff;
  border: 1px solid #c7d2fe;
  border-radius: var(--radius-sm);
  padding: 1rem 1.25rem;
  margin-bottom: 1.5rem;
}

.ai-toggle input {
  margin-top: 0.25rem;
  accent-color: var(--primary);
}

.ai-toggle label {
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  cursor: pointer;
}

.ai-toggle label span {
  font-size: 0.82rem;
  color: var(--text-muted);
  font-weight: 400;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.25rem;
}

.field-full {
  grid-column: 1 / -1;
}

.field label {
  display: block;
  font-size: 0.85rem;
  font-weight: 600;
  margin-bottom: 0.4rem;
}

.required {
  color: #dc2626;
}

.form-actions {
  display: flex;
  gap: 0.75rem;
  margin-top: 1.75rem;
  padding-top: 1.5rem;
  border-top: 1px solid var(--border);
}

.form-actions .btn-primary-modern {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

@media (max-width: 600px) {
  .form-grid {
    grid-template-columns: 1fr;
  }
}
</style>
