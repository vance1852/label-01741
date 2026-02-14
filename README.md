# 智慧养老管理系统

基于 Spring Boot + Vue 3 + MySQL 的智慧养老综合管理平台。

## 环境变量配置

### 生产环境（必须配置）

复制 `.env.example` 为 `.env` 并配置以下变量：

| 变量 | 说明 | 示例 |
|------|------|------|
| MYSQL_ROOT_PASSWORD | 数据库密码 | your_secure_password |
| JWT_SECRET | JWT签名密钥（至少32字符） | your-very-long-secret-key |
| CORS_ALLOWED_ORIGINS | 允许的跨域来源 | https://your-domain.com |
| SPRING_PROFILES_ACTIVE | Spring环境 | prod |
| LOG_LEVEL | 日志级别 | info / warn |

### 开发环境

开发环境可使用默认值，但建议配置 `DB_PASSWORD` 和 `JWT_SECRET`。

## How to Run

### Docker 方式（推荐）

```bash
# 开发环境（使用默认配置）
docker-compose up --build -d

# 生产环境（使用.env文件）
cp .env.example .env
# 编辑 .env 配置生产参数
docker-compose up --build -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### 本地开发

1. 启动 MySQL 并执行 `backend/src/main/resources/schema.sql`

2. 配置环境变量并启动后端：
```bash
cd backend
# Windows PowerShell
$env:DB_PASSWORD="123456"; $env:JWT_SECRET="dev-secret-key-for-local-development"
mvn spring-boot:run

# Linux/Mac
DB_PASSWORD=123456 JWT_SECRET=dev-secret-key-for-local-development mvn spring-boot:run
```

3. 启动前端：
```bash
cd frontend-admin
npm install
npm run dev
```

## Services

### Docker 部署

| 服务 | 地址 | 说明 |
|------|------|------|
| 管理后台 | http://localhost:8081 | Vue 3 管理端 |
| 后端 API | http://localhost:8080/api | Spring Boot API |
| MySQL | localhost:3306 | 数据库 |

### 本地开发

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端开发服务器 | http://localhost:5173 | Vite Dev Server（自动代理API） |
| 后端 API | http://localhost:8080/api | Spring Boot API |

## 测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |

## 安全说明

- 生产环境必须配置强密码和JWT密钥
- 生产环境应限制CORS来源为具体域名
- 建议启用HTTPS和CSP安全策略
- 前端使用localStorage存储token，需配合XSS防护

---

## 功能模块

- 首页概览：统计在院老人、护工、订单数据
- 老人管理：老人信息的增删改查
- 护工管理：护工信息的增删改查
- 服务管理：服务项目的增删改查
- 订单管理：服务预约订单管理
- 健康管理：老人健康数据记录
- 操作日志：系统操作日志查询

## 技术栈

- 后端：Java 17 + Spring Boot 3 + MyBatis-Plus + MySQL 8
- 前端：Vue 3 + Vite + Element Plus + Pinia + Axios
- 部署：Docker + Docker Compose
