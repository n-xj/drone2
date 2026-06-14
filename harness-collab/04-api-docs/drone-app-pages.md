# 无人机管理 — 页面与路由

基于 Thymeleaf 的页面式接口（非纯 REST API）。

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/` | 重定向到 `/drone` |
| GET | `/login` | 登录页 |
| POST | `/login` | 提交用户名/密码，登录成功后跳转列表 |
| GET | `/logout` | Shiro 登出 |
| GET | `/drone` | 列表（可带 `?name=` 过滤） |
| GET | `/drone/new` | 新建表单 |
| POST | `/drone` | 创建（生成 AI 属性后保存） |
| GET | `/drone/{id}/edit` | 编辑 |
| POST | `/drone/{id}` | 更新 |
| POST | `/drone/{id}/delete` | 删除 |
| GET | `/drone/{id}` | 详情 |

**文档同步**：与 `drone-app` 中 `DroneController`、模板路径一致。
