-- 기존 데이터 삭제
DELETE FROM orders;

-- 더미 주문 데이터 추가 (20개)
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('노트북', '김철수', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('스마트폰', '이영희', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('태블릿', '박지민', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('헤드폰', '최민수', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('스마트워치', '정소연', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('블루투스 스피커', '강동원', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('무선 이어폰', '송중기', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('게이밍 마우스', '전지현', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('게이밍 키보드', '이민호', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('모니터', '김수진', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('프린터', '박보검', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('외장 하드디스크', '이종석', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('웹캠', '김고은', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('마이크', '조인성', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('스캐너', '한지민', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('라우터', '공유', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('전기 자전거', '이준기', 'SHIPPING', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('스마트 홈 허브', '신민아', 'COMPLETED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('로봇 청소기', '유아인', 'RECEIVED', CURRENT_TIMESTAMP);
INSERT INTO orders (item, customer_name, status, created_at) VALUES ('드론', '김우빈', 'SHIPPING', CURRENT_TIMESTAMP); 