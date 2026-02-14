# 智慧养老管理系统

基于 Spring Boot + Vue 3 + MySQL 的智慧养老综合管理平台。

## 快速启动

```bash
# 克隆后直接启动（开发环境，使用内置默认配置）
docker-compose up --build -d

# 访问
# 前端: http://localhost:8081
# 后端API: http://localhost:8080/api
# 测试账号: admin / admin123
```

## 环境变量配置

### 开发环境

Docker 方式启动时已内置开发环境默认值，无需额外配置即可运行。

### 生产环境（必须配置）

生产部署前，复制环境变量模板并配置：

```bash
# Linux/Mac
cp .env.example .env

# Windows PowerShell
Copy-Item .env.example .env
```

编辑 `.env` 文件，配置以下必填变量：

| 变量 | 必填 | 说明 | 示例 |
|------|:----:|------|------|
| MYSQL_ROOT_PASSWORD | ✅ | 数据库密码 | your_secure_password |
| JWT_SECRET | ✅ | JWT签名密钥（至少32字符） | your-very-long-secret-key-32chars |
| CORS_ALLOWED_ORIGINS | ✅ | 允许的跨域来源（生产必须限定域名） | https://your-domain.com |
| SPRING_PROFILES_ACTIVE | 建议 | Spring环境 | prod |
| LOG_LEVEL | 建议 | 日志级别 | info / warn |

然后启动：
```bash
docker-compose up --build -d
```

## 本地开发（不使用Docker）

1. 启动 MySQL 并执行 `backend/src/main/resources/schema.sql`

2. 启动后端：
```bash
cd backend

# Windows PowerShell
$env:DB_PASSWORD="123456"
mvn spring-boot:run

# Linux/Mac
DB_PASSWORD=123456 mvn spring-boot:run
```

3. 启动前端：
```bash
cd frontend-admin
npm install
npm run dev
```

## 服务端口

| 环境 | 服务 | 地址 |
|------|------|------|
| Docker | 前端 | http://localhost:8081 |
| Docker | 后端API | http://localhost:8080/api |
| Docker | MySQL | localhost:3306 |
| 本地开发 | 前端 | http://localhost:5173 |
| 本地开发 | 后端API | http://localhost:8080/api |

## 测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |

## 安全说明

- 开发环境默认 CORS 允许所有来源（`*`），仅用于开发调试
- 生产环境必须配置 `CORS_ALLOWED_ORIGINS` 为具体域名
- 生产环境必须配置强 `JWT_SECRET`（至少32字符）
- 建议启用 HTTPS 和 CSP 安全策略

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
