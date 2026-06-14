# 无人机信息管理系统（设计摘）

## 分层与包

| 层级 | 包 |
|------|----|
| Web | `com.md.drone.controller` |
| 服务 | `com.md.drone.service` + `com.md.drone.service.impl` |
| 数据访问 | `com.md.drone.repository` + `com.md.drone.repository.impl`；MyBatis Mapper 放在 `com.md.drone.repository.mapper` |
| 领域 | `com.md.drone.domain` |
| 配置 / 横切 | `com.md.drone.config`、`com.md.drone.interceptor` |

## 多数据源

- 默认 `application.yml`：SQLite 路径可配置 `app.datasource.sqlite-path`。
- Profile `mysql` + `application-mysql.yml`：JDBC 连接 MySQL，使用 `schema-mysql.sql` 初始化表结构（与 `schema-sqlite.sql` 语义一致）。

## AI 属性生成

- `DroneAiAttributeService`：`mock` 模式（默认）生成本地 JSON；`http` 模式可对接任意 POST JSON 的端点，URL 与凭据可配置。

## 安全

- Shiro `IniRealm`（演示账号），Filter 链：`/static/**`、`/webjars/**`、`/login`（GET/POST 业务登录）匿名，其余 `authc`。

**状态**：与实现同步（`drone-app` 模块）
