# 无人机信息管理系统（需求摘）

| 项 | 说明 |
|----|------|
| 技术栈 | Java 8、Spring Boot 2.2、Servlet 3、MyBatis 3.5、Shiro 1.7、Druid 1.2、Thymeleaf 3、Bootstrap 3.3.7、Maven 3 |
| 数据 | 默认 SQLite，可配置切换至 MySQL |
| 功能 | 无人机信息增删改查；**属性**由可配置的 AI 服务（HTTP 或 Mock）生成并持久化为 JSON 文本 |
| 安全 | Shiro 表单登录、会话认证 |
| 横切 | 独立包中的 MVC 请求日志拦截器，打印非敏感请求信息 |

## 已确认范围

- 初期使用 SQLite 文件库；通过 Spring Profile 使用 MySQL。
- 服务层、数据操作层均拆分为**接口包**与**实现包**。

**状态**：与实现同步（`drone-app` 模块）
