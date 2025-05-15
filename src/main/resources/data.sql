-- 기존 데이터 삭제
DELETE FROM orders;

-- 더미 주문 데이터 추가 (20개)
-- Hanbit Electronics orders
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Hanbit Electronics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Woojin Innotech Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '1' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '2' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '3' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'Dongbang C&C Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '4' HOUR);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '5' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '6' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '7' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'NextGen Systems Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '8' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'Hanbit Electronics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '9' HOUR);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Sejong Industries Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '10' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '11' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Dongbang C&C Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '12' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '13' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '14' HOUR);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '15' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Woojin Innotech Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '16' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'NextGen Systems Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '17' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Hanbit Electronics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '18' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '19' HOUR);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '20' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Dongbang C&C Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '21' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Daelim Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP - INTERVAL '22' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Haneul IT Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP - INTERVAL '23' HOUR);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Sungil Logistics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP - INTERVAL '24' HOUR);

-- admin 계정 생성 (비밀번호: admin, BCrypt 해시)
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$Dow1v6QwQwQwQwQwQwQwQOQwQwQwQwQwQwQwQwQwQwQwQwQwQw', 'ROLE_ADMIN');

