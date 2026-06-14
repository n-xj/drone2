# drone-app（无人机信息管理）

基于 Spring Boot 2.2 + Thymeleaf + MyBatis + Druid + Shiro 的示例模块：SQLite 为默认库，可选 MySQL Profile；支持无人机 CRUD 与 AI 扩展属性（Mock 或 HTTP）。

## 需求与设计（工作区统一入口）

| 文档 | 路径 |
|------|------|
| 产品说明 | [harness-collab/01-product-specs/drone-management-spec.md](../harness-collab/01-product-specs/drone-management-spec.md) |
| 技术设计 | [harness-collab/02-design-docs/drone-management-design.md](../harness-collab/02-design-docs/drone-management-design.md) |
| 页面与路由说明 | [harness-collab/04-api-docs/drone-app-pages.md](../harness-collab/04-api-docs/drone-app-pages.md) |

## 本地运行

```bash
# 从仓库根目录，使用 baseplatform 自带的 Maven Wrapper（若本机未装 mvn）
baseplatform/mvnw.cmd -f drone-app/pom.xml spring-boot:run
```

默认端口 **8080**，SQLite 数据文件默认 **`./data/drone.db`**（可通过环境变量 `DRONE_DB_PATH` 覆盖）。

### 演示登录

账户见 `src/main/resources/shiro.ini`（示例：`admin` / `secret`）。

### MySQL

```bash
java -jar target/drone-app-*.jar --spring.profiles.active=mysql
```

需先创建库并配置用户密码，详见 `src/main/resources/application-mysql.yml` 注释。

### AI 属性生成

在 `application.yml` 或通过环境变量覆盖：

- `app.ai.mode`：`mock`（默认）或 `http`
- `app.ai.http-url`：HTTP 模式下的 POST 地址；为空时退回 Mock 并打日志
- `app.ai.http-bearer`：可选 Bearer Token

## 构建与质量门禁

```bash
baseplatform/mvnw.cmd -f drone-app/pom.xml clean verify -Pharness-new
```
包含 Checkstyle、单元/集成测试、SpotBugs、JaCoCo 行覆盖率（≥80%）。

## 目录说明（简述）

| 路径 | 说明 |
|------|------|
| `src/main/java` | 分层：`controller` / `service` / `repository` / `domain` / `config` / `interceptor` / `exception` |
| `src/main/resources` | `application*.yml`、`mybatis`、`schema-*.sql`、Shiro Ini、静态与模板 |

