-- H2 测试数据库初始化脚本
DROP TABLE IF EXISTS sys_log;
DROP TABLE IF EXISTS health_record;
DROP TABLE IF EXISTS service_order;
DROP TABLE IF EXISTS service_item;
DROP TABLE IF EXISTS care_worker;
DROP TABLE IF EXISTS elder;
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    phone VARCHAR(20),
    role INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE elder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender INT DEFAULT 1,
    birth_date DATE,
    id_card VARCHAR(18),
    phone VARCHAR(20),
    address VARCHAR(200),
    emergency_contact VARCHAR(50),
    emergency_phone VARCHAR(20),
    health_status INT DEFAULT 1,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE care_worker (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender INT DEFAULT 1,
    phone VARCHAR(20),
    skills VARCHAR(500),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE service_item (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    price DECIMAL(10,2) DEFAULT 0,
    duration INT DEFAULT 60,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

CREATE TABLE service_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_no VARCHAR(32) NOT NULL UNIQUE,
    elder_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    worker_id BIGINT,
    appointment_time TIMESTAMP,
    status INT DEFAULT 0,
    remark VARCHAR(500),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    CONSTRAINT fk_order_elder FOREIGN KEY (elder_id) REFERENCES elder(id),
    CONSTRAINT fk_order_service FOREIGN KEY (service_id) REFERENCES service_item(id),
    CONSTRAINT fk_order_worker FOREIGN KEY (worker_id) REFERENCES care_worker(id)
);
CREATE INDEX idx_order_elder_id ON service_order(elder_id);
CREATE INDEX idx_order_service_id ON service_order(service_id);
CREATE INDEX idx_order_worker_id ON service_order(worker_id);
CREATE INDEX idx_order_status ON service_order(status);
CREATE INDEX idx_order_appointment_time ON service_order(appointment_time);

CREATE TABLE health_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    elder_id BIGINT NOT NULL,
    blood_pressure_high DECIMAL(5,1),
    blood_pressure_low DECIMAL(5,1),
    heart_rate DECIMAL(5,1),
    blood_sugar DECIMAL(5,2),
    temperature DECIMAL(4,1),
    remark VARCHAR(500),
    record_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    CONSTRAINT fk_health_elder FOREIGN KEY (elder_id) REFERENCES elder(id)
);
CREATE INDEX idx_health_elder_id ON health_record(elder_id);
CREATE INDEX idx_health_record_time ON health_record(record_time);

CREATE TABLE sys_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    username VARCHAR(50),
    operation VARCHAR(100),
    method VARCHAR(200),
    params CLOB,
    ip VARCHAR(50),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_log_user_id ON sys_log(user_id);
CREATE INDEX idx_log_create_time ON sys_log(create_time);

-- 测试数据 (密码: admin123)
INSERT INTO sys_user (username, password, real_name, phone, role, status) VALUES
('admin', '$2a$10$h2IhdL4OZx7ato1DxLYXqeSssBWC.fjevi8X/MHgxAKSt/UnhtAMG', '系统管理员', '13800138000', 1, 1);

INSERT INTO elder (name, gender, birth_date, id_card, phone, address, emergency_contact, emergency_phone, health_status) VALUES
('张福贵', 1, '1945-03-15', '110101194503150011', '13900139001', '北京市朝阳区', '张明华', '13900139002', 1),
('李秀英', 2, '1948-07-22', '110101194807220022', '13900139003', '北京市海淀区', '李建国', '13900139004', 2);

INSERT INTO care_worker (name, gender, phone, skills) VALUES
('王美玲', 2, '13800138001', '日常护理、康复训练'),
('刘建军', 1, '13800138002', '医疗护理、急救处理');

INSERT INTO service_item (name, description, price, duration) VALUES
('日常生活照料', '基础护理服务', 100.00, 60),
('专业康复训练', '康复训练服务', 150.00, 45);

INSERT INTO service_order (order_no, elder_id, service_id, worker_id, appointment_time, status, remark) VALUES
('ORD20240201001', 1, 1, 1, '2024-02-01 09:00:00', 2, '服务已完成');

INSERT INTO health_record (elder_id, blood_pressure_high, blood_pressure_low, heart_rate, blood_sugar, temperature, remark) VALUES
(1, 125.0, 82.0, 72.0, 5.6, 36.5, '各项指标正常');
