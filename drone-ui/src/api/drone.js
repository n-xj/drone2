import { apiFetch } from './http'

/** 列表查询，name 可选，对应后端模糊搜索 */
export function listDrones(name) {
  const qs = name ? '?name=' + encodeURIComponent(name) : ''
  return apiFetch('/api/drones' + qs)
}

/** 按 ID 获取详情 */
export function getDrone(id) {
  return apiFetch('/api/drones/' + id)
}

/** 新建：后端会自动调用 AI 生成 attributesJson */
export function createDrone(payload) {
  return apiFetch('/api/drones', { method: 'POST', body: JSON.stringify(payload) })
}

/** 更新：payload.regenerateAttributes 为 true 时重新生成 AI 属性 */
export function updateDrone(id, payload) {
  return apiFetch('/api/drones/' + id, { method: 'PUT', body: JSON.stringify(payload) })
}

/** 按 ID 删除 */
export function deleteDrone(id) {
  return apiFetch('/api/drones/' + id, { method: 'DELETE' })
}
