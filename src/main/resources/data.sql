-- 기존 데이터 삭제
DELETE FROM orders;

-- 더미 주문 데이터 추가 (20개)
-- Hanbit Electronics orders
INSERT INTO orders (item, customer_name, status) VALUES ('SAP ERP License Renewal', 'Hanbit Electronics Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('S/4HANA Upgrade', 'Woojin Innotech Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('Microsoft Azure Resource Expansion', 'Dongbang C&C Co., Ltd.', 'RECEIVED');

INSERT INTO orders (item, customer_name, status) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('SAP ERP License Renewal', 'NextGen Systems Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('Microsoft Azure Resource Expansion', 'Hanbit Electronics Co., Ltd.', 'COMPLETED');

INSERT INTO orders (item, customer_name, status) VALUES ('S/4HANA Upgrade', 'Sejong Industries Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('AWS Cloud Usage Expansion', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('GAM Solution Module Addition', 'Dongbang C&C Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('EWM Logistics System Customization', 'Daelim Logistics Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('WRMS Rental Solution User Addition', 'Haneul IT Co., Ltd.', 'SHIPPING');

INSERT INTO orders (item, customer_name, status) VALUES ('Salesforce Integration Request', 'Sungil Logistics Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('SAP ERP License Renewal', 'Woojin Innotech Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('Microsoft Azure Resource Expansion', 'NextGen Systems Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('S/4HANA Upgrade', 'Hanbit Electronics Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('AWS Cloud Usage Expansion', 'Sejong Industries Co., Ltd.', 'RECEIVED');

INSERT INTO orders (item, customer_name, status) VALUES ('GAM Solution Module Addition', 'Kwangmyung Tech Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('EWM Logistics System Customization', 'Dongbang C&C Co., Ltd.', 'COMPLETED');
INSERT INTO orders (item, customer_name, status) VALUES ('WRMS Rental Solution User Addition', 'Daelim Logistics Co., Ltd.', 'RECEIVED');
INSERT INTO orders (item, customer_name, status) VALUES ('Salesforce Integration Request', 'Haneul IT Co., Ltd.', 'SHIPPING');
INSERT INTO orders (item, customer_name, status) VALUES ('SAP ERP License Renewal', 'Sungil Logistics Co., Ltd.', 'COMPLETED');

-- 초기 관리자 사용자 추가 (비밀번호: admin)
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$rDkPvvAFV6GgJjXp5GzQeO5YQZ5YQZ5YQZ5YQZ5YQZ5YQZ5YQZ5YQ', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

