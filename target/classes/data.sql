-- 기존 데이터 삭제
DELETE FROM orders;

-- 더미 주문 데이터 추가 (20개)
-- Hanbit Electronics orders
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Hanbit Electronics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Woojin Innotech Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'Dongbang C&C Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'NextGen Systems Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'Hanbit Electronics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Sejong Industries Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Dongbang C&C Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Woojin Innotech Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Microsoft Azure Resource Expansion', 'NextGen Systems Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('S/4HANA Upgrade', 'Hanbit Electronics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);

INSERT INTO orders (item, customer_name, status, created_at) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('EWM Logistics System Customization', 'Dongbang C&C Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('WRMS Rental Solution User Addition', 'Daelim Logistics Co., Ltd.', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('Salesforce Integration Request', 'Haneul IT Co., Ltd.', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('SAP ERP License Renewal', 'Sungil Logistics Co., Ltd.', 'COMPLETED', CURRENT_TIMESTAMP);

-- 초기 관리자 사용자 추가 (비밀번호: admin)
MERGE INTO users (username, password, role, created_at, updated_at)
VALUES ('admin', '$2a$10$rDkPvvAFV6GgJjXp5GzQeO5YQZ5YQZ5YQZ5YQZ5YQZ5YQZ5YQZ5YQ', 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

