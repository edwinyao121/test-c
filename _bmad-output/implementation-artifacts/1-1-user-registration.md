# Story 1.1: 用户注册

Status: review

## Story

As a 访客,
I want 注册新账户（手机号+验证码）,
so that 我可以成为系统用户

## Acceptance Criteria

### AC1: 发送验证码

**Given** 访客在注册页面
**When** 输入有效手机号并点击发送验证码
**Then** 系统发送验证码到手机
**And** 验证码输入框可用

### AC2: 完成注册

**Given** 访客收到验证码
**When** 输入正确验证码和密码
**Then** 系统创建新账户
**And** 自动登录并跳转到首页

## Tasks / Subtasks

- [x] Task 1: 初始化后端 Spring Boot 项目 (AC: All)
   - [x] Subtask 1.1: 使用 Spring Initializr 创建后端项目
   - [x] Subtask 1.2: 配置 pom.xml 添加依赖 (Spring Security, JWT, MySQL, Flyway, SpringDoc)
   - [x] Subtask 1.3: 配置 application.yml (数据库, JWT, 服务器端口)
- [x] Task 2: 创建数据库迁移脚本 (AC: All)
   - [x] Subtask 2.1: 创建 V1__create_user_table.sql
   - [x] Subtask 2.2: 创建 V2__create_verification_code_table.sql
- [x] Task 3: 实现后端 Entity 和 Repository (AC: All)
   - [x] Subtask 3.1: 创建 User.java 实体类
   - [x] Subtask 3.2: 创建 UserRepository.java
   - [x] Subtask 3.3: 创建 VerificationCode.java 实体类
   - [x] Subtask 3.4: 创建 VerificationCodeRepository.java
- [x] Task 4: 实现 DTO/VO 和工具类 (AC: All)
   - [x] Subtask 4.1: 创建 ApiResponse.java 统一响应
   - [x] Subtask 4.2: 创建 SendCodeRequest.java
   - [x] Subtask 4.3: 创建 RegisterRequest.java
   - [x] Subtask 4.4: 创建 UserVO.java
   - [x] Subtask 4.5: 创建 JwtUtil.java
   - [x] Subtask 4.6: 创建 BusinessException.java
   - [x] Subtask 4.7: 创建 GlobalExceptionHandler.java
- [x] Task 5: 实现 Service 层 (AC: All)
   - [x] Subtask 5.1: 创建 UserService.java 接口
   - [x] Subtask 5.2: 创建 UserServiceImpl.java 实现
- [x] Task 6: 实现 Controller 层 (AC: All)
   - [x] Subtask 6.1: 创建 UserController.java
- [x] Task 7: 配置 Spring Security (AC: All)
   - [x] Subtask 7.1: 创建 SecurityConfig.java
   - [x] Subtask 7.2: 配置 JWT 过滤器 (JwtAuthenticationFilter)
- [x] Task 8: 初始化前端 Vue 项目 (AC: All)
   - [x] Subtask 8.1: 创建 Vite + Vue 3 项目
   - [x] Subtask 8.2: 安装依赖 (Element Plus, Pinia, Vue Router, Axios, SCSS)
- [x] Task 9: 实现前端 API 和 Store (AC: All)
   - [x] Subtask 9.1: 创建 api/user.js
   - [x] Subtask 9.2: 创建 stores/user.js
   - [x] Subtask 9.3: 配置 Axios 拦截器 (utils/request.js)
- [x] Task 10: 实现注册页面 (AC: All)
   - [x] Subtask 10.1: 创建 views/auth/Register.vue
   - [x] Subtask 10.2: 配置路由
- [x] Task 11: 编写单元测试 (AC: All)
   - [x] Subtask 11.1: UserServiceTest.java
   - [x] Subtask 11.2: JwtUtilTest.java

## Technical Requirements

### Backend (Spring Boot 3.x + Java 17)

**Project Structure:**
```
backend/src/main/java/com/catstore/api/
├── controller/UserController.java
├── service/UserService.java
├── service/impl/UserServiceImpl.java
├── repository/UserRepository.java
├── entity/User.java
├── dto/
│   ├── RegisterRequest.java
│   └── SendCodeRequest.java
├── vo/
│   └── UserVO.java
├── config/
│   ├── SecurityConfig.java
│   └── JwtConfig.java
├── exception/
│   ├── BusinessException.java
│   └── GlobalExceptionHandler.java
└── util/
    └── JwtUtil.java
```

**Database Migration (Flyway):**
```
backend/src/main/resources/db/migration/
├── V1__create_user_table.sql
```

**API Endpoints:**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/users/send-code | 发送验证码 |
| POST | /api/users/register | 用户注册 |

**Request/Response Formats:**
```json
// POST /api/users/send-code
// Request: {"phone": "13800138000"}
// Response: {"code": 200, "message": "success", "data": null}

// POST /api/users/register
// Request: {"phone": "13800138000", "code": "123456", "password": "xxx"}
// Response: {"code": 200, "message": "success", "data": {"token": "xxx", "refreshToken": "xxx", "user": {...}}}
```

**JWT Configuration:**
- Access Token 有效期: 30 分钟
- Refresh Token 有效期: 7 天
- 密码加密: BCrypt

**Error Handling:**
| HTTP Status | 业务码 | 说明 |
|-------------|--------|------|
| 400 | 1001 | 手机号格式错误 |
| 400 | 1002 | 验证码错误/已过期 |
| 400 | 1003 | 手机号已注册 |
| 500 | 5000 | 服务器内部错误 |

### Frontend (Vue 3 + Element Plus)

**Project Structure:**
```
frontend/src/
├── api/user.js
├── views/auth/Register.vue
├── stores/user.js
└── router/index.js
```

**Component Requirements:**
- Register.vue: 手机号输入 + 验证码发送按钮 + 验证码输入 + 密码输入 + 注册按钮
- 使用 Element Plus Form 组件 + 表单验证
- 发送验证码按钮: 60 秒倒计时

**State Management (Pinia - user store):**
```javascript
// stores/user.js
{
  token: '',
  refreshToken: '',
  userInfo: {}
}
```

**API Integration:**
- Axios 拦截器自动注入 Token
- 统一错误处理

## Dev Notes

### Architecture Compliance

1. **必须遵循的规范:**
   - API 风格: RESTful + 复数名词
   - 响应格式: `{code, message, data}`
   - 数据库命名: 全小写 + 下划线 (如 `user_account`)
   - 代码命名: PascalCase (类) / camelCase (方法/变量)
   - 密码加密: BCrypt (不可逆)

2. **项目结构对齐:**
   - 后端标准三层架构: Controller → Service → Repository → Entity
   - 前端: api/views/stores/router/components 分层
   - 使用 Flyway SQL 脚本管理数据库迁移

3. **安全要求:**
   - 全站 HTTPS (部署时配置)
   - JWT Token 存储在本地，避免 XSS
   - 验证码有效期限制 (5分钟)

### Testing Requirements

1. **后端单元测试:**
   - Service 层测试覆盖率 ≥ 80%
   - JWT 生成/验证测试
   - 验证码逻辑测试

2. **前端组件测试:**
   - Register.vue 表单验证测试
   - API 错误处理测试

### References

- 用户注册流程: _bmad-output/planning-artifacts/epics.md#Epic-1-Story-1.1
- 架构规范: _bmad-output/planning-artifacts/architecture.md
- 技术栈: Vue 3 + Spring Boot 3 + MySQL + JWT
- API 响应格式: _bmad-output/planning-artifacts/architecture.md#API-Response-Formats
- 命名规范: _bmad-output/planning-artifacts/architecture.md#Naming-Patterns

## Dev Agent Record

### Agent Model Used

MiniMax-M2.7

### Debug Log References

### Completion Notes List

- 实现了完整的用户注册功能，包括后端和前端
- 后端使用 Spring Boot 3.2.0 + Spring Security + JWT
- 前端使用 Vue 3 + Element Plus + Pinia + Axios
- 验证码功能为模拟实现（打印到控制台），实际生产需集成短信网关
- 密码使用 BCrypt 加密存储
- JWT Access Token 30分钟有效，Refresh Token 7天有效
- 数据库使用 Flyway 进行迁移管理
- 单元测试覆盖 UserService 和 JwtUtil

### File List

**Backend (Java):**
- backend/pom.xml
- backend/src/main/java/com/catstore/api/CatstoreApiApplication.java
- backend/src/main/java/com/catstore/api/controller/UserController.java
- backend/src/main/java/com/catstore/api/service/UserService.java
- backend/src/main/java/com/catstore/api/service/impl/UserServiceImpl.java
- backend/src/main/java/com/catstore/api/repository/UserRepository.java
- backend/src/main/java/com/catstore/api/repository/VerificationCodeRepository.java
- backend/src/main/java/com/catstore/api/entity/User.java
- backend/src/main/java/com/catstore/api/entity/VerificationCode.java
- backend/src/main/java/com/catstore/api/dto/SendCodeRequest.java
- backend/src/main/java/com/catstore/api/dto/RegisterRequest.java
- backend/src/main/java/com/catstore/api/vo/ApiResponse.java
- backend/src/main/java/com/catstore/api/vo/UserVO.java
- backend/src/main/java/com/catstore/api/vo/LoginVO.java
- backend/src/main/java/com/catstore/api/config/SecurityConfig.java
- backend/src/main/java/com/catstore/api/config/JwtAuthenticationFilter.java
- backend/src/main/java/com/catstore/api/exception/BusinessException.java
- backend/src/main/java/com/catstore/api/exception/GlobalExceptionHandler.java
- backend/src/main/java/com/catstore/api/util/JwtUtil.java
- backend/src/main/resources/application.yml
- backend/src/main/resources/db/migration/V1__create_user_table.sql
- backend/src/main/resources/db/migration/V2__create_verification_code_table.sql
- backend/src/test/java/com/catstore/api/service/UserServiceTest.java
- backend/src/test/java/com/catstore/api/util/JwtUtilTest.java

**Frontend (Vue 3):**
- frontend/src/main.js
- frontend/src/App.vue
- frontend/src/router/index.js
- frontend/src/stores/user.js
- frontend/src/api/user.js
- frontend/src/utils/request.js
- frontend/src/views/auth/Register.vue

## Change Log

- 2026-04-24: 初始实现用户注册功能 (Story 1.1)
