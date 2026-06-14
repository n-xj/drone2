/**
 * 统一 HTTP 请求封装。
 * 所有 API 调用都经此函数，统一处理 Cookie、JSON 解析与后端 AjaxResult 格式。
 */
export async function apiFetch(url, options = {}) {
  const headers = {
    Accept: 'application/json',
    ...(options.body ? { 'Content-Type': 'application/json' } : {}),
    ...options.headers
  }

  let res
  try {
    // credentials: 'include' 携带 Shiro 会话 Cookie（JSESSIONID）
    res = await fetch(url, { credentials: 'include', ...options, headers })
  } catch {
    throw new Error('无法连接后端，请确认后端已启动（默认 8081 端口）')
  }

  const text = await res.text()
  let body
  try {
    body = text ? JSON.parse(text) : {}
  } catch {
    throw new Error('后端响应异常 (HTTP ' + res.status + ')，请检查 vite.config.js 中的代理端口是否与后端一致')
  }

  // HTTP 401：Shiro 拦截未认证请求
  if (res.status === 401) {
    throw new Error(body.msg || '未登录或会话已过期')
  }

  // 后端约定：code === 200 表示业务成功
  if (body.code !== 200) {
    throw new Error(body.msg || '请求失败 (code=' + body.code + ')')
  }

  return body // { code, msg, data }
}
