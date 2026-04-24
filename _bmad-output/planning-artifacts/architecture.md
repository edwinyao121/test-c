---
stepsCompleted:
  - step-01-init
  - step-02-context
  - step-03-starter
  - step-04-decisions
  - step-05-patterns
  - step-06-structure
  - step-07-validation
  - step-08-complete
workflowType: 'architecture'
lastStep: 8
status: 'complete'
completedAt: '2026-04-24'
project_name: 'test'
user_name: 'bmad'
date: '2026-04-24'
inputDocuments:
  - _bmad-output/planning-artifacts/prd.md
---

# Architecture Decision Document

_This document builds collaboratively through step-by-step discovery. Sections are appended as we work through each architectural decision together._

## Project Context Analysis

### Requirements Overview

**Functional Requirements:**
28 条功能需求，分为 7 个能力区域：用户账户管理、商品浏览与发现、购物车管理、交易与支付、订单履约、个人中心、实时通知。

**Non-Functional Requirements:**
- 性能：首屏加载 ≤ 2 秒，API 响应 ≤ 500ms
- 安全：全站 HTTPS、密码加密存储、微信支付合规
- 可扩展：支持 ≥ 1000 并发，CDN 静态资源加速
- 可靠性：核心购物流程 99.9% 可用性

**Scale & Complexity:**
- Primary domain: Web App（SPA）+ API Backend
- Complexity level: Low
- Estimated architectural components: 6-8 core modules

### Technical Constraints & Dependencies

- SPA 前端架构（Vue 3）
- WebSocket/SSE 实时通知
- 微信支付 JSAPI 集成
- 移动端优先响应式设计

### Cross-Cutting Concerns Identified

- 支付安全与合规
- 实时订单状态推送
- SEO 搜索引擎优化
- 多设备购物车同步

## Starter Template Evaluation

### Primary Technology Domain

**Web Application (SPA) + Backend API**

### 技术栈选择

**前端技术栈：**

| 组件 | 选择 | 说明 |
|------|------|------|
| 框架 | Vue 3 + Composition API | 现代化、轻量级 |
| 构建工具 | Vite | 快速、热更新 |
| UI 组件库 | Element Plus | Vue 3 兼容 |
| 状态管理 | Pinia | Vue 3 官方推荐 |
| 路由 | Vue Router 4 | SPA 路由 |
| HTTP 客户端 | Axios | API 请求 |
| CSS | SCSS | 样式预处理 |

**后端技术栈：**

| 组件 | 选择 | 说明 |
|------|------|------|
| Framework | Spring Boot 3.x | 最新 LTS |
| 语言 | Java 17+ | 长期支持 |
| ORM | Spring Data JPA | 数据库访问 |
| 安全 | Spring Security + JWT | 认证授权 |
| 数据库 | MySQL 8.0 | 你的选择 |
| 构建 | Maven | 依赖管理 |
| API 文档 | SpringDoc OpenAPI 3 | Swagger UI |
| 数据库迁移 | Flyway | SQL 脚本管理 |

### 部署架构

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│   浏览器    │────▶│   Nginx     │────▶│ Spring Boot │
│  (用户端)   │     │  (反向代理)  │     │   Backend   │
└─────────────┘     └─────────────┘     └──────┬──────┘
                                               │
                                        ┌──────▼──────┐
                                        │    MySQL    │
                                        │   Database  │
                                        └─────────────┘
```

### 初始化命令

**前端（Vue 3 + Vite + Element Plus）：**
```bash
npm create vite@latest frontend -- --template vue
cd frontend
npm install element-plus @element-plus/icons-vue pinia vue-router axios sass
```

**后端（Spring Boot）：**
```bash
curl -s https://start.spring.io/starter.zip \
  -d type=maven-project \
  -d language=java \
  -d bootVersion=3.2.0 \
  -d baseDir=backend \
  -d groupId=com.catstore \
  -d artifactId=api \
  -d name=catstore-api \
  -d packageName=com.catstore.api \
  | unzip -d backend
```

## Core Architectural Decisions

### Decision Priority Analysis

**Critical Decisions (Block Implementation):**
- 技术栈确定（Vue 3 + Spring Boot 3 + MySQL）
- 认证方式（JWT Token）
- API 风格（RESTful）
- 数据库迁移（Flyway）

**Important Decisions (Shape Architecture):**
- 前端状态管理（Pinia 模块化）
- API 文档（SpringDoc OpenAPI 3）
- 实时通知方案

**Deferred Decisions (Post-MVP):**
- Redis 缓存
- 消息队列
- CDN 静态资源加速

### Data Architecture

| 决策 | 选择 | 版本 |
|------|------|------|
| 数据库 | MySQL 8.0 | 8.0+ |
| ORM 框架 | Spring Data JPA | - |
| 数据库迁移 | Flyway | 最新稳定版 |
| 迁移方式 | SQL 脚本优先 | - |

### Authentication & Security

| 决策 | 选择 | 说明 |
|------|------|------|
| 认证方式 | JWT Token | Access + Refresh Token |
| 令牌有效期 | Access: 30min, Refresh: 7d | - |
| 密码加密 | BCrypt | 不可逆哈希 |
| 传输安全 | HTTPS | 全站强制 |
| 微信支付合规 | 不存储敏感支付信息 | - |

### API & Communication Patterns

| 决策 | 选择 | 说明 |
|------|------|------|
| API 风格 | RESTful | 资源导向 |
| API 文档 | SpringDoc OpenAPI 3 | Swagger UI |
| 错误处理 | 统一错误响应格式 | {code, message, data} |
| 认证头 | Authorization: Bearer {token} | - |

### Frontend Architecture

| 决策 | 选择 | 说明 |
|------|------|------|
| 状态管理 | Pinia 模块化 | user, product, cart, order |
| 路由守卫 | Vue Router 导航守卫 | 权限控制 |
| HTTP 请求 | Axios 拦截器 | Token 自动注入 |
| 表单验证 | Element Plus 内置 + 自定义 | - |

### Infrastructure & Deployment

| 决策 | 选择 | 说明 |
|------|------|------|
| 部署平台 | Linux 服务器 | 物理机/虚拟机 |
| Web 服务器 | Nginx | 反向代理 + 静态资源 |
| 前端部署 | Nginx 托管 | SPA 单页应用 |
| 后端部署 | Jar 包运行 | java -jar |

### Decision Impact Analysis

**Implementation Sequence:**
1. 项目初始化（前端 + 后端）
2. 数据库设计与迁移
3. 用户模块（注册/登录/JWT）
4. 商品模块（列表/详情）
5. 购物车模块
6. 订单模块
7. 支付集成
8. 实时通知（WebSocket/SSE）

**Cross-Component Dependencies:**
- JWT 认证 → 所有 API 调用都需要
- 用户模块 → 订单、个人中心依赖
- 商品模块 → 购物车、订单依赖
- 微信支付 → 订单模块集成

## Implementation Patterns & Consistency Rules

### Naming Patterns

**数据库命名：**

| 类型 | 规则 | 示例 |
|------|------|------|
| 表名 | 全小写 + 下划线 | `user_account` |
| 列名 | 全小写 + 下划线 | `phone_number` |
| 主键 | `id` | 自增主键 |
| 外键 | `{表名}_id` | `user_id` |
| 索引 | `idx_{表名}_{列名}` | `idx_user_phone` |
| 唯一约束 | `uq_{表名}_{列名}` | `uq_user_phone` |

**API 命名：**

| 类型 | 规则 | 示例 |
|------|------|------|
| 端点 | RESTful + 复数名词 | `/api/users`, `/api/products` |
| 路径参数 | `{id}` | `/api/users/{id}` |
| 查询参数 | camelCase | `pageSize`, `currentPage` |
| HTTP 方法 | GET查/POST增/PUT改/DELETE删 | - |

**代码命名：**

| 类型 | 规则 | 示例 |
|------|------|------|
| 前端组件 | PascalCase | `UserCard.vue` |
| 前端变量 | camelCase | `userInfo` |
| 前端 API 方法 | camelCase | `getUserList` |
| 后端类 | PascalCase | `UserController` |
| 后端方法 | camelCase | `getUserById` |
| 常量 | UPPER_SNAKE | `MAX_PAGE_SIZE` |

### Project Structure

**后端（Spring Boot）：**
```
backend/
├── src/main/java/com/catstore/api/
│   ├── controller/          # Controller 层
│   ├── service/             # Service 接口
│   ├── service/impl/       # Service 实现
│   ├── repository/         # JPA Repository
│   ├── entity/             # 实体类
│   ├── dto/                # 数据传输对象
│   ├── vo/                 # 视图对象
│   ├── config/             # 配置类
│   ├── exception/          # 异常处理
│   └── util/               # 工具类
├── src/main/resources/
│   ├── db/migration/       # Flyway 迁移脚本
│   └── application.yml
└── pom.xml
```

**前端（Vue 3）：**
```
frontend/
├── src/
│   ├── api/                # API 请求
│   ├── components/         # 公共组件
│   ├── views/             # 页面组件
│   ├── router/            # 路由配置
│   ├── stores/            # Pinia 状态管理
│   ├── styles/            # 全局样式
│   └── utils/             # 工具函数
└── package.json
```

### API Response Formats

**统一响应结构：**
```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

**分页响应：**
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "records": [...],
    "total": 100,
    "current": 1,
    "size": 10
  }
}
```

**错误响应：**
```json
{
  "code": 400,
  "message": "参数错误",
  "data": null
}
```

### Error Handling

**后端异常分类：**

| 异常类型 | HTTP 状态码 | 说明 |
|---------|-------------|------|
| 业务异常 | 400 | 参数校验失败 |
| 未授权 | 401 | Token 无效/过期 |
| 禁止访问 | 403 | 无权限 |
| 资源不存在 | 404 | 数据不存在 |
| 服务器错误 | 500 | 内部错误 |

### Enforcement Guidelines

**All AI Agents MUST:**

1. 遵循上述命名规范
2. 使用统一 API 响应格式
3. 使用 Flyway 管理数据库迁移
4. 使用 Pinia 模块化状态管理
5. 使用 Axios 拦截器处理 Token
6. 所有 Controller 加 `@RestController`
7. 所有 Entity 加 JPA 注解

## Project Structure & Boundaries

### Complete Project Directory Structure

```
catstore/                          # 项目根目录
├── frontend/                      # 前端应用
│   ├── public/
│   ├── src/
│   │   ├── api/                  # API 请求层
│   │   ├── components/           # 公共组件
│   │   ├── views/                # 页面组件
│   │   ├── router/               # 路由配置
│   │   ├── stores/               # Pinia 状态管理
│   │   ├── styles/               # 全局样式
│   │   └── utils/               # 工具函数
│   ├── .env
│   ├── vite.config.js
│   └── package.json
│
├── backend/                      # 后端应用
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/catstore/api/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   ├── vo/
│   │   │   │   ├── config/
│   │   │   │   ├── exception/
│   │   │   │   └── util/
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── db/migration/
│   │   └── test/
│   └── pom.xml
│
├── docs/
├── _bmad-output/
├── docker-compose.yml
├── nginx.conf
└── README.md
```

### Requirements to Structure Mapping

| 功能需求 (FR) | 前端目录 | 后端包 |
|--------------|---------|--------|
| FR1-4 用户账户管理 | views/auth, stores/user | controller/UserService |
| FR5-8 商品浏览发现 | views/product, stores/product | controller/ProductService |
| FR9-13 购物车管理 | views/cart, stores/cart | controller/CartService |
| FR14-19 交易与支付 | views/order | controller/OrderService |
| FR20-23 订单履约 | views/order, stores/order | controller/OrderService |
| FR24-26 个人中心 | views/user, stores/user | controller/UserService |
| FR27-28 实时通知 | 暂无 MVP | WebSocket (Post-MVP) |

## Architecture Validation Results

### Coherence Validation ✅

**Decision Compatibility:**
- Vue 3 + Element Plus ✓ 兼容
- Spring Boot 3 + JWT + MySQL ✓ 兼容
- 所有命名规范一致 ✓

**Pattern Consistency:**
- RESTful API 风格 ✓
- 统一响应格式 ✓
- Flyway 迁移规范 ✓

**Structure Alignment:**
- 前端按视图/组件/状态分 ✓
- 后端标准三层架构 ✓

### Requirements Coverage Validation ✅

| FR 类别 | 架构支持 |
|---------|---------|
| FR1-4 用户账户 | UserController + UserService ✓ |
| FR5-8 商品浏览 | ProductController + ProductService ✓ |
| FR9-13 购物车 | CartController + CartService ✓ |
| FR14-19 交易支付 | OrderController + WechatPay ✓ |
| FR20-23 订单履约 | OrderController ✓ |
| FR24-26 个人中心 | UserController ✓ |
| FR27-28 实时通知 | WebSocket (Post-MVP) |

### Implementation Readiness Validation ✅

- 技术栈完整 ✓
- 命名规范明确 ✓
- 项目结构完整 ✓
- 集成点清晰 ✓

### Gap Analysis Results

**无严重缺口。** 实时通知（FR27-28）可 Post-MVP 实现。

### Architecture Completeness Checklist

**✅ Requirements Analysis**

- [x] Project context thoroughly analyzed
- [x] Scale and complexity assessed
- [x] Technical constraints identified
- [x] Cross-cutting concerns mapped

**✅ Architectural Decisions**

- [x] Critical decisions documented with versions
- [x] Technology stack fully specified
- [x] Integration patterns defined
- [x] Performance considerations addressed

**✅ Implementation Patterns**

- [x] Naming conventions established
- [x] Structure patterns defined
- [x] Communication patterns specified
- [x] Process patterns documented

**✅ Project Structure**

- [x] Complete directory structure defined
- [x] Component boundaries established
- [x] Integration points mapped
- [x] Requirements to structure mapping complete

### Architecture Readiness Assessment

**Overall Status:** READY FOR IMPLEMENTATION ✅

**Confidence Level:** 高

---

## 🎉 Architecture Complete!

恭喜你完成了 **catstore 电商项目** 的架构设计文档！

**文档产出：**
- 项目上下文分析
- 技术栈选择（Vue 3 + Spring Boot 3 + MySQL）
- 核心架构决策（认证、API、安全）
- 实现模式规范（命名、响应格式、错误处理）
- 完整项目结构
- 架构验证结果

**文档位置：** `_bmad-output/planning-artifacts/architecture.md`

---

**下一步建议：**

| 选项 | 说明 |
|------|------|
| **[CE] Create Epics and Stories** | 拆解史诗和用户故事 |
| **[CU] Create UX Design** | 创建 UX 设计方案 |
| **[IR] Check Implementation Readiness** | 验证 PRD 和架构完整性 |
| **开始开发** | 使用 `bmad-quick-dev` 开始实现 |