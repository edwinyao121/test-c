---
stepsCompleted:
  - step-01-validate-prerequisites
  - step-02-design-epics
  - step-03-create-stories
  - step-04-final-validation
workflowStatus: complete
completedAt: 2026-04-24
inputDocuments:
  - _bmad-output/planning-artifacts/prd.md
  - _bmad-output/planning-artifacts/architecture.md
---

# test - Epic Breakdown

## Overview

This document provides the complete epic and story breakdown for test, decomposing the requirements from the PRD, UX Design if it exists, and Architecture requirements into implementable stories.

## Requirements Inventory

### Functional Requirements

**用户账户管理 (FR1-4):**
- FR1: 访客可以注册新账户（手机号+验证码）
- FR2: 已注册用户可以登录（手机号+密码）
- FR3: 已登录用户可以退出登录
- FR4: 系统可以验证用户身份

**商品浏览与发现 (FR5-8):**
- FR5: 用户可以按分类浏览商品列表
- FR6: 用户可以搜索商品（关键词匹配）
- FR7: 用户可以筛选商品（按价格、库存状态）
- FR8: 用户可以查看商品详情（图文、规格、库存）

**购物车管理 (FR9-13):**
- FR9: 用户可以将商品加入购物车
- FR10: 用户可以修改购物车中商品数量
- FR11: 用户可以删除购物车中的商品
- FR12: 用户可以查看购物车商品列表
- FR13: 系统可以同步用户购物车状态

**交易与支付 (FR14-19):**
- FR14: 用户可以管理收货地址（新增、编辑、删除）
- FR15: 用户可以在结算时选择收货地址
- FR16: 用户可以确认订单信息
- FR17: 用户可以选择支付方式（微信支付）
- FR18: 用户可以完成支付流程
- FR19: 系统可以处理支付结果

**订单履约 (FR20-23):**
- FR20: 用户可以查看自己的订单列表
- FR21: 用户可以查看订单详情（商品、价格、物流）
- FR22: 系统可以推送订单状态更新通知
- FR23: 用户可以追踪订单物流状态

**个人中心 (FR24-26):**
- FR24: 用户可以查看个人中心页面
- FR25: 用户可以修改头像
- FR26: 用户可以修改基础个人信息

**实时通知 (FR27-28):**
- FR27: 系统可以向用户推送订单状态通知
- FR28: 系统可以向用户推送系统消息

### NonFunctional Requirements

**Performance:**
- NFR1: 首屏加载：≤ 2 秒（3G 网络）
- NFR2: 页面切换响应：≤ 200ms
- NFR3: API 响应时间：≤ 500ms
- NFR4: 搜索结果返回：≤ 1 秒

**Security:**
- NFR5: 全站 HTTPS 加密
- NFR6: Session 令牌有效期 ≤ 24 小时
- NFR7: 遵循微信支付安全规范，不存储敏感支付信息
- NFR8: 用户密码加密存储（不可逆哈希）

**Scalability:**
- NFR9: 支持 ≥ 1000 并发（初期）
- NFR10: 核心购物流程可用性 ≥ 99.9%
- NFR11: 系统恢复时间 ≤ 30 分钟（重大故障后）

**Integration:**
- NFR12: 微信支付：JSAPI 支付流程
- NFR13: 实时通知：WebSocket 或 SSE

### Additional Requirements

**技术栈（Architecture）:**
- 前端技术栈：Vue 3 + Vite + Element Plus + Pinia + Vue Router + Axios + SCSS
- 后端技术栈：Spring Boot 3.x + Java 17 + Spring Data JPA + Spring Security + JWT + MySQL + Maven + SpringDoc OpenAPI 3 + Flyway
- 部署架构：Linux 服务器 + Nginx 反向代理 + Jar 包运行

**初始化命令（Architecture）:**
- 前端：`npm create vite@latest frontend -- --template vue`
- 后端：`curl -s https://start.spring.io/starter.zip ... | unzip -d backend`

**API 规范（Architecture）:**
- API 风格：RESTful + 复数名词
- 统一响应格式：{code, message, data}
- 认证方式：JWT Token（Access: 30min, Refresh: 7d）
- 数据库迁移：Flyway SQL 脚本

**前端架构（Architecture）:**
- 路由守卫：Vue Router 导航守卫
- HTTP 请求：Axios 拦截器（Token 自动注入）
- 状态管理：Pinia 模块化（user, product, cart, order）

**错误处理（Architecture）:**
- 后端异常分类：400（业务）、401（未授权）、403（禁止）、404（不存在）、500（服务器错误）
- 全局异常处理器：GlobalExceptionHandler

### UX Design Requirements

无 UX 文档（跳过）

### FR Coverage Map

| FR | Epic | Story |
|----|------|-------|
| FR1-4 | Epic 1: 用户账户 | Story 1.1-1.4 |
| FR5-8 | Epic 2: 商品浏览 | Story 2.1-2.4 |
| FR9-13 | Epic 3: 购物车 | Story 3.1-3.5 |
| FR14-19 | Epic 4: 交易支付 | Story 4.1-4.6 |
| FR20-23 | Epic 5: 订单履约 | Story 5.1-5.4 |
| FR24-26 | Epic 6: 个人中心 | Story 6.1-6.3 |
| FR27-28 | Epic 7: 实时通知 | Post-MVP |

## Epic List

| Epic | 标题 | FRs |
|------|------|------|
| Epic 1 | 用户账户管理 | FR1-4 |
| Epic 2 | 商品浏览与发现 | FR5-8 |
| Epic 3 | 购物车管理 | FR9-13 |
| Epic 4 | 交易与支付 | FR14-19 |
| Epic 5 | 订单履约 | FR20-23 |
| Epic 6 | 个人中心 | FR24-26 |
| Epic 7 | 实时通知（Post-MVP） | FR27-28 |

## Epic 1: 用户账户管理

### Story 1.1: 用户注册

As a 访客,
I want 注册新账户（手机号+验证码）,
So that 我可以成为系统用户

**Acceptance Criteria:**

**Given** 访客在注册页面
**When** 输入有效手机号并点击发送验证码
**Then** 系统发送验证码到手机
**And** 验证码输入框可用

**Given** 访客收到验证码
**When** 输入正确验证码和密码
**Then** 系统创建新账户
**And** 自动登录并跳转到首页

### Story 1.2: 用户登录

As a 已注册用户,
I want 登录（手机号+密码）,
So that 我可以使用账户功能

**Acceptance Criteria:**

**Given** 已注册用户在登录页面
**When** 输入正确的手机号和密码
**Then** 系统验证成功并生成 JWT Token
**And** 跳转到首页或上一页

**Given** 登录失败（密码错误）
**When** 点击登录
**Then** 显示"手机号或密码错误"
**And** 保留用户输入的手机号

### Story 1.3: 用户退出登录

As a 已登录用户,
I want 退出登录,
So that 我可以切换账户或安全离开

**Acceptance Criteria:**

**Given** 已登录用户点击退出
**When** 确认退出
**Then** 清除本地 Token
**And** 跳转到首页

### Story 1.4: 身份验证

As a 系统,
I want 验证用户身份,
So that 保护用户数据和操作安全

**Acceptance Criteria:**

**Given** 用户访问受保护资源
**When** 请求带有有效 JWT Token
**Then** 允许访问

**Given** 用户访问受保护资源
**When** 请求无 Token 或 Token 过期
**Then** 返回 401 未授权
**And** 跳转到登录页

## Epic 2: 商品浏览与发现

### Story 2.1: 按分类浏览商品

As a 用户,
I want 按分类浏览商品列表,
So that 我可以快速找到目标品类的商品

**Acceptance Criteria:**

**Given** 用户在商品列表页
**When** 选择某个分类（如"猫粮"）
**Then** 显示该分类下的所有商品列表
**And** 高亮当前选中的分类

**Given** 分类下无商品
**Then** 显示"该分类暂无商品"

### Story 2.2: 搜索商品

As a 用户,
I want 搜索商品（关键词匹配）,
So that 我可以通过关键词找到特定商品

**Acceptance Criteria:**

**Given** 用户在搜索框输入关键词
**When** 点击搜索或按回车
**Then** 返回包含关键词的商品列表
**And** 响应时间 ≤ 1 秒（NFR4）

**Given** 搜索无结果
**Then** 显示"未找到相关商品"

### Story 2.3: 筛选商品

As a 用户,
I want 筛选商品（按价格、库存状态）,
So that 我可以缩小商品范围更快找到目标

**Acceptance Criteria:**

**Given** 用户在商品列表页
**When** 选择价格区间（如 0-100 元）
**Then** 显示符合条件的商品列表

**Given** 用户勾选"只看有货"
**Then** 只显示库存 > 0 的商品

**Given** 用户修改筛选条件
**Then** 商品列表实时更新

### Story 2.4: 查看商品详情

As a 用户,
I want 查看商品详情（图文、规格、库存）,
So that 我可以了解商品的完整信息

**Acceptance Criteria:**

**Given** 用户点击商品卡片
**When** 查看商品详情页
**Then** 显示商品图片、名称、价格、规格、库存

**Given** 商品有多种规格（如不同口味）
**Then** 显示规格选择器

**Given** 商品库存为 0
**Then** 显示"缺货"状态
**And** 按钮变为"到货通知"

## Epic 3: 购物车管理

### Story 3.1: 加入购物车

As a 用户,
I want 将商品加入购物车,
So that 我可以先选商品再统一结算

**Acceptance Criteria:**

**Given** 用户在商品详情页
**When** 选择规格并点击"加入购物车"
**Then** 商品添加到用户购物车
**And** 显示成功提示"已加入购物车"

**Given** 商品已存在于购物车
**When** 再次加入
**Then** 数量 +1

### Story 3.2: 修改商品数量

As a 用户,
I want 修改购物车中商品数量,
So that 我可以调整购买数量

**Acceptance Criteria:**

**Given** 用户在购物车页面
**When** 点击数量 + 或 -
**Then** 商品数量实时更新
**And** 实时更新商品小计

**Given** 商品数量减到 0
**Then** 弹出确认是否删除

### Story 3.3: 删除商品

As a 用户,
I want 删除购物车中的商品,
So that 我可以移除不想要的商品

**Acceptance Criteria:**

**Given** 用户在购物车页面
**When** 点击删除按钮
**Then** 商品从购物车移除
**And** 购物车列表刷新

### Story 3.4: 查看购物车列表

As a 用户,
I want 查看购物车商品列表,
So that 我可以确认已选商品

**Acceptance Criteria:**

**Given** 用户在购物车页面
**When** 页面加载
**Then** 显示所有已添加的商品
**And** 显示每个商品的小计和总计

**Given** 购物车为空
**Then** 显示"购物车是空的，去逛逛"

### Story 3.5: 同步购物车状态

As a 系统,
I want 同步用户购物车状态,
So that 用户在不同设备可以看到一致的购物车

**Acceptance Criteria:**

**Given** 用户添加商品到购物车
**When** 数据保存到后端
**Then** 用户在任何设备登录都能看到相同的购物车

**Given** 用户未登录
**When** 添加商品
**Then** 商品保存在本地存储
**And** 登录后合并到用户账户

## Epic 4: 交易与支付

### Story 4.1: 新增收货地址

As a 用户,
I want 新增收货地址,
So that 我可以指定收货地点

**Acceptance Criteria:**

**Given** 用户在地址管理页面
**When** 点击"新增地址"
**Then** 打开地址表单

**Given** 用户填写地址信息并保存
**When** 点击保存
**Then** 地址保存成功
**And** 显示在地址列表

### Story 4.2: 编辑收货地址

As a 用户,
I want 编辑收货地址,
So that 我可以修改已保存的地址

**Acceptance Criteria:**

**Given** 用户在地址列表
**When** 点击某个地址的编辑按钮
**Then** 打开编辑表单
**And** 填充现有地址信息

**Given** 用户修改地址并保存
**Then** 地址更新成功
**And** 列表刷新显示新信息

### Story 4.3: 删除收货地址

As a 用户,
I want 删除收货地址,
So that 我可以移除不需要的地址

**Acceptance Criteria:**

**Given** 用户在地址列表
**When** 点击删除按钮
**Then** 弹出确认对话框

**Given** 用户确认删除
**Then** 地址从列表移除

### Story 4.4: 选择收货地址

As a 用户,
I want 在结算时选择收货地址,
So that 我可以指定订单的收货地点

**Acceptance Criteria:**

**Given** 用户在结算页面
**When** 点击选择收货地址
**Then** 显示地址选择列表

**Given** 用户选择一个地址
**Then** 地址填充到订单
**And** 显示收货人信息和联系电话

### Story 4.5: 确认订单信息

As a 用户,
I want 确认订单信息,
So that 我可以核对购买内容

**Acceptance Criteria:**

**Given** 用户在结算页面
**When** 查看订单预览
**Then** 显示商品列表、收货地址、支付方式、总金额

**Given** 用户发现信息有误
**Then** 可以返回修改对应内容

### Story 4.6: 选择支付方式并支付

As a 用户,
I want 选择支付方式（微信支付）,
So that 我可以完成购买付款

**Acceptance Criteria:**

**Given** 用户确认订单信息
**When** 选择微信支付并点击支付
**Then** 跳转微信支付页面

**Given** 微信支付成功
**Then** 订单状态变为"已支付"
**And** 跳转到订单成功页

**Given** 微信支付失败
**Then** 显示支付失败提示
**And** 订单状态保持"待支付"

## Epic 5: 订单履约

### Story 5.1: 查看订单列表

As a 用户,
I want 查看自己的订单列表,
So that 我可以了解历史购买情况

**Acceptance Criteria:**

**Given** 用户在"我的订单"页面
**When** 页面加载
**Then** 显示所有订单（按时间倒序）

**Given** 订单数量较多
**Then** 支持分页加载

**Given** 订单为空
**Then** 显示"暂无订单，去逛逛"

### Story 5.2: 查看订单详情

As a 用户,
I want 查看订单详情（商品、价格、物流）,
So that 我可以了解订单的完整信息

**Acceptance Criteria:**

**Given** 用户在订单列表页
**When** 点击某个订单
**Then** 显示订单详情页
**And** 包含商品列表、支付金额、收货地址、订单状态

### Story 5.3: 追踪订单物流

As a 用户,
I want 追踪订单物流状态,
So that 我可以了解商品的配送进度

**Acceptance Criteria:**

**Given** 订单已发货
**When** 用户查看订单详情
**Then** 显示物流公司和运单号
**And** 显示物流进度

**Given** 物流信息更新
**Then** 用户可实时查看最新状态

### Story 5.4: 订单状态推送

As a 系统,
I want 推送订单状态更新通知,
So that 用户可以及时了解订单进展

**Acceptance Criteria:**

**Given** 订单状态发生变化（如"已发货"）
**Then** 系统发送状态更新通知
**And** 通知包含订单号和新状态

**Given** 订单状态：已支付 → 已发货 → 配送中 → 已送达
**Then** 每个状态变化都推送通知

## Epic 6: 个人中心

### Story 6.1: 查看个人中心

As a 用户,
I want 查看个人中心页面,
So that 我可以了解账户概览

**Acceptance Criteria:**

**Given** 用户点击"我的"
**When** 进入个人中心页面
**Then** 显示用户头像、昵称、快捷入口（订单、地址、收藏等）

### Story 6.2: 修改头像

As a 用户,
I want 修改头像,
So that 我可以个性化我的账户

**Acceptance Criteria:**

**Given** 用户在个人中心
**When** 点击头像
**Then** 打开头像选择器

**Given** 用户上传新头像
**When** 点击保存
**Then** 头像更新成功

### Story 6.3: 修改基础信息

As a 用户,
I want 修改基础个人信息,
So that 我可以更新我的账户信息

**Acceptance Criteria:**

**Given** 用户在个人中心
**When** 点击"编辑资料"
**Then** 显示资料编辑表单

**Given** 用户修改昵称并保存
**Then** 信息更新成功
**And** 页面显示新昵称

## Epic 7: 实时通知（Post-MVP）

### Story 7.1: 订单状态 WebSocket 推送

As a 系统,
I want 通过 WebSocket 向用户推送订单状态,
So that 用户可以实时收到订单更新

**Acceptance Criteria:**

**Given** 订单状态发生变化
**When** 系统通过 WebSocket 推送
**Then** 用户端实时显示新状态
**And** 不需要刷新页面

### Story 7.2: 系统消息推送

As a 系统,
I want 向用户推送系统消息,
So that 用户可以收到重要的平台通知

**Acceptance Criteria:**

**Given** 系统有重要通知（如活动、公告）
**When** 发送系统消息
**Then** 用户可在消息中心查看
**And** 支持 WebSocket 实时推送

---

## Epic Summary

| Epic | Stories | FRs Covered |
|------|---------|-------------|
| Epic 1: 用户账户管理 | 1.1-1.4 | FR1-4 |
| Epic 2: 商品浏览与发现 | 2.1-2.4 | FR5-8 |
| Epic 3: 购物车管理 | 3.1-3.5 | FR9-13 |
| Epic 4: 交易与支付 | 4.1-4.6 | FR14-19 |
| Epic 5: 订单履约 | 5.1-5.4 | FR20-23 |
| Epic 6: 个人中心 | 6.1-6.3 | FR24-26 |
| Epic 7: 实时通知 | 7.1-7.2 | FR27-28 (Post-MVP) |

**Total Stories:** 24 (MVP: 22, Post-MVP: 2)