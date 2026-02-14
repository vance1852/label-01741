-- 智慧养老系统数据库初始化脚本
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

CREATE DATABASE IF NOT EXISTS elderly_care DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE elderly_care;

-- 系统用户表
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    role INT DEFAULT 0 COMMENT '角色:0普通用户,1管理员',
    status INT DEFAULT 1 COMMENT '状态:0禁用,1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 老人信息表
DROP TABLE IF EXISTS elder;
CREATE TABLE elder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
    gender INT DEFAULT 1 COMMENT '性别:1男,2女',
    birth_date DATE COMMENT '出生日期',
    id_card VARCHAR(18) COMMENT '身份证号',
    phone VARCHAR(20) COMMENT '联系电话',
    address VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '家庭住址',
    emergency_contact VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    health_status INT DEFAULT 1 COMMENT '健康状态:1良好,2一般,3较差',
    status INT DEFAULT 1 COMMENT '状态:0离院,1在院',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='老人信息表';

-- 护工信息表
DROP TABLE IF EXISTS care_worker;
CREATE TABLE care_worker (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
    gender INT DEFAULT 1 COMMENT '性别:1男,2女',
    phone VARCHAR(20) COMMENT '联系电话',
    skills VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '技能特长',
    status INT DEFAULT 1 COMMENT '状态:0离职,1在职',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护工信息表';

-- 服务项目表
DROP TABLE IF EXISTS service_item;
CREATE TABLE service_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '服务名称',
    description VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '服务描述',
    price DECIMAL(10,2) DEFAULT 0 COMMENT '服务价格',
    duration INT DEFAULT 60 COMMENT '服务时长(分钟)',
    status INT DEFAULT 1 COMMENT '状态:0下架,1上架',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务项目表';

-- 服务订单表
DROP TABLE IF EXISTS service_order;
CREATE TABLE service_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_no VARCHAR(32) NOT NULL UNIQUE COMMENT '订单编号',
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    service_id BIGINT NOT NULL COMMENT '服务项目ID',
    worker_id BIGINT COMMENT '护工ID',
    appointment_time DATETIME COMMENT '预约时间',
    status INT DEFAULT 0 COMMENT '状态:0待处理,1进行中,2已完成,3已取消',
    remark VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务订单表';

-- 健康记录表
DROP TABLE IF EXISTS health_record;
CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    elder_id BIGINT NOT NULL COMMENT '老人ID',
    blood_pressure_high DECIMAL(5,1) COMMENT '收缩压',
    blood_pressure_low DECIMAL(5,1) COMMENT '舒张压',
    heart_rate DECIMAL(5,1) COMMENT '心率',
    blood_sugar DECIMAL(5,2) COMMENT '血糖',
    temperature DECIMAL(4,1) COMMENT '体温',
    remark VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
    record_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted INT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康记录表';

-- 系统日志表
DROP TABLE IF EXISTS sys_log;
CREATE TABLE sys_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT COMMENT '用户ID',
    username VARCHAR(50) COMMENT '用户名',
    operation VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '操作描述',
    method VARCHAR(200) COMMENT '请求方法',
    params TEXT COMMENT '请求参数',
    ip VARCHAR(50) COMMENT 'IP地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志表';

-- 初始化管理员账号 (密码: admin123, BCrypt哈希 cost=10)
INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '$2a$10$h2IhdL4OZx7ato1DxLYXqeSssBWC.fjevi8X/MHgxAKSt/UnhtAMG', '系统管理员', '13800138000', 1, 1);

-- 初始化老人测试数据
INSERT INTO elder (name, gender, birth_date, id_card, phone, address, emergency_contact, emergency_phone, health_status) VALUES
('张福贵', 1, '1945-03-15', '110101194503150011', '13900139001', '北京市朝阳区建国路88号院3号楼', '张明华', '13900139002', 1),
('李秀英', 2, '1948-07-22', '110101194807220022', '13900139003', '北京市海淀区中关村大街15号', '李建国', '13900139004', 2),
('王德顺', 1, '1942-11-08', '110101194211080033', '13900139005', '北京市西城区西单北大街120号', '王小军', '13900139006', 1),
('赵玉兰', 2, '1950-05-20', '110101195005200044', '13900139007', '北京市东城区王府井大街200号', '赵丽娜', '13900139008', 3),
('刘长江', 1, '1946-09-12', '110101194609120055', '13900139009', '北京市丰台区南三环西路16号', '刘洋', '13900139010', 2);

-- 初始化护工测试数据
INSERT INTO care_worker (name, gender, phone, skills) VALUES
('王美玲', 2, '13800138001', '日常护理、康复训练、心理疏导、营养配餐'),
('刘建军', 1, '13800138002', '医疗护理、急救处理、康复指导、健康监测'),
('陈晓红', 2, '13800138003', '老年护理、生活照料、陪伴聊天、文娱活动'),
('张伟强', 1, '13800138004', '康复训练、运动指导、安全防护、紧急救援');

-- 初始化服务项目测试数据
INSERT INTO service_item (name, description, price, duration) VALUES
('日常生活照料', '包含起居照料、饮食照料、个人卫生清洁等基础护理服务，确保老人日常生活舒适', 100.00, 60),
('专业康复训练', '由专业康复师指导的肢体功能恢复训练，帮助老人恢复身体机能', 150.00, 45),
('健康指标检测', '血压、血糖、心率、体温等基础健康指标检测与记录', 50.00, 30),
('心理健康疏导', '专业心理咨询师提供的心理健康服务，关注老人心理状态', 200.00, 60),
('陪伴聊天服务', '专人陪伴老人聊天、读报、下棋等，丰富老人精神生活', 80.00, 60),
('外出陪同服务', '陪同老人外出就医、购物、散步等，确保出行安全', 120.00, 120);

-- 初始化服务订单测试数据
INSERT INTO service_order (order_no, elder_id, service_id, worker_id, appointment_time, status, remark) VALUES
('ORD20240201001', 1, 1, 1, '2024-02-01 09:00:00', 2, '服务已完成，老人状态良好'),
('ORD20240201002', 2, 3, 2, '2024-02-01 10:30:00', 2, '健康检测完成，各项指标正常'),
('ORD20240202001', 3, 2, 4, '2024-02-02 14:00:00', 1, '康复训练进行中'),
('ORD20240202002', 1, 4, 3, '2024-02-02 15:30:00', 0, '待安排心理疏导服务'),
('ORD20240203001', 4, 5, 1, '2024-02-03 09:00:00', 0, '预约陪伴聊天服务');

-- 初始化健康记录测试数据
INSERT INTO health_record (elder_id, blood_pressure_high, blood_pressure_low, heart_rate, blood_sugar, temperature, remark, record_time) VALUES
(1, 125.0, 82.0, 72.0, 5.6, 36.5, '各项指标正常，继续保持良好作息', '2024-02-01 08:30:00'),
(1, 128.0, 85.0, 75.0, 5.8, 36.6, '血压略有升高，建议减少盐分摄入', '2024-02-02 08:30:00'),
(2, 135.0, 88.0, 78.0, 6.2, 36.4, '血压偏高，需要注意休息', '2024-02-01 09:00:00'),
(2, 132.0, 86.0, 76.0, 6.0, 36.5, '血压有所下降，继续观察', '2024-02-02 09:00:00'),
(3, 118.0, 78.0, 68.0, 5.2, 36.3, '各项指标良好', '2024-02-01 09:30:00'),
(4, 142.0, 92.0, 82.0, 7.1, 36.8, '血压血糖偏高，建议就医检查', '2024-02-01 10:00:00'),
(5, 130.0, 84.0, 74.0, 5.9, 36.5, '指标基本正常', '2024-02-01 10:30:00');
