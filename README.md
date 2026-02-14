# 智慧养老管理系统

基于 Spring Boot + Vue 3 + MySQL 的智慧养老综合管理平台。

## How to Run

### Docker 方式（推荐）

```bash
# 启动所有服务
docker-compose up --build -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### 本地开发

1. 启动 MySQL 并执行 `backend/src/main/resources/schema.sql`
2. 启动后端：
```bash
cd backend
mvn spring-boot:run
```
3. 启动前端：
```bash
cd frontend-admin
npm install
npm run dev
```

## Services

| 服务 | 地址 | 说明 |
|------|------|------|
| 管理后台 | http://localhost:8081 | Vue 3 管理端 |
| 后端 API | http://localhost:8080 | Spring Boot API |
| MySQL | localhost:3306 | 数据库 |

## 测试账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |

## 题目内容

可以帮我生成一个智慧养老的系统吗？使用Java编写后端框架用SpringBoot，前端Vue，数据库Mysql。

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
